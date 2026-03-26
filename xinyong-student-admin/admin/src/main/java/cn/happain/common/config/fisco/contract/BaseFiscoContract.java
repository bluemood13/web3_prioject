package cn.happain.common.config.fisco.contract;

import cn.happain.common.config.fisco.utils.IOUtil;
import cn.happain.common.consts.GlobalConst;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/**
 * FISCO BCOS 合约抽象基类
 * 封装合约通用属性和初始化逻辑，子类仅需实现自身特有方法
 */
@Component
@Data
@NoArgsConstructor
@ConditionalOnBean(Client.class) // 仅当 Client 存在时，子类才会生效
public abstract class BaseFiscoContract {

    // ======================== 1. 通用属性（子类共享）========================
    @Resource
    public Client client; // 子类可直接使用，protected 修饰

    @Value("${qkl.value}")
    public String qklKey; // 区块链环境标识（如 local/remote）

    // 合约核心信息（子类需指定具体值，或通过配置加载）
    public String contractKey; // 合约唯一标识（如 "data"，用于拼接 ABI/BIN 路径）
    public String ABI; // 合约 ABI
    public String BINARY; // 合约 BIN
    public String ADDRESS; // 合约地址合集
    public String contractAddress = ""; // 执行的合约地址

    // 交易处理器（通用工具，子类直接用）
    public AssembleTransactionProcessor txProcessor;

    // ======================== 核心：通过构造方法强制传入 contractKey ========================
    /**
     * 抽象基类构造方法：必须传入 contractKey
     * 子类必须调用此构造方法，确保 contractKey 被初始化
     */
    public BaseFiscoContract(String contractKey) {
        // 校验 contractKey 非空
        if (StrUtil.isBlank(contractKey)) {
            throw new IllegalArgumentException("构造方法传入的 contractKey 不能为空！");
        }
        this.contractKey = contractKey; // 赋值给 final 字段
    }


    // ======================== 2. 通用初始化逻辑（子类无需重写）========================
    /**
     * 初始化流程：创建交易处理器 → 加载 ABI/BIN
     * 子类若需扩展初始化逻辑，可重写 initExtra() 方法
     */
    @PostConstruct
    public final void init() throws Exception {
        // 3. 子类扩展初始化（可选，默认空实现）
        initExtra();
        // 1. 创建交易处理器（通用逻辑，所有合约共用）
        createTxProcessor();
        // 2. 加载合约 ABI 和 BIN（需子类指定 contractKey，通过抽象方法约束）
        loadContractAbiAndBin();
    }

    /**
     * 创建交易处理器（通用逻辑，封装后子类无需重复写）
     */
    public void createTxProcessor() throws Exception {
        this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(
                client,
                client.getCryptoSuite().getCryptoKeyPair()
        );
    }

    /**
     * 加载合约 ABI 和 BIN（根据 qklKey 和 contractKey 拼接路径）
     * 依赖子类实现的 getContractKey() 方法，确保子类必须指定合约标识
     */
    public void loadContractAbiAndBin() throws Exception {
        String contractKey = getContractKey();
        if (StrUtil.isBlank(contractKey)) {
            throw new IllegalArgumentException("合约标识 contractKey 不能为空，请在子类中实现 getContractKey() 方法");
        }
        // 拼接 ABI/BIN 路径（通用路径规则，子类无需修改）
        String abiPath = StrUtil.format("fisco/contract/{}/{}.abi", contractKey, contractKey);
        String binPath = StrUtil.format("fisco/contract/{}/{}.bin", contractKey, contractKey);
        String addressPath = StrUtil.format("fisco/contract/{}/{}.json", contractKey, contractKey);
        // 读取 ABI/BIN（依赖 IOUtil，通用逻辑）
        if(FileUtil.exist(abiPath)) {
            this.ABI = IOUtil.readString(abiPath);
        }
        if(FileUtil.exist(binPath)) {
            this.BINARY = IOUtil.readString(binPath);
        }
        if(FileUtil.exist(addressPath)) {
            this.ADDRESS = IOUtil.readString(addressPath);
            JSONObject addressJson = JSONUtil.parseObj(this.ADDRESS);
            // 空实现，留给子类扩展
            if (GlobalConst.Qkl.FISCO_REMOTE.equals(qklKey)) {
                this.contractAddress = addressJson.getStr("remote-address").strip();
            } else if (GlobalConst.Qkl.FISCO_LOCAL.equals(qklKey)) {
                this.contractAddress = addressJson.getStr("local-address").strip();
            } else {
                this.contractAddress = "";
            }
        }

    }



    // ======================== 4. 可选扩展方法（子类按需重写）========================
    /**
     * 子类扩展初始化逻辑（如额外加载配置、校验合约地址）
     * 默认空实现，子类无需强制重写
     */
    public void initExtra()  {
        // 空实现，留给子类扩展
    }


    public TransactionResponse setJsonStr(String jsonStr) throws Exception {
        return this.txProcessor.sendTransactionAndGetResponse(this.contractAddress, this.ABI, "setJsonStr", ListUtil.of(jsonStr));
    }

    public CallResponse getJsonStr() throws Exception {
        return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.contractAddress, this.ABI, "getJsonStr", Arrays.asList());
    }



}