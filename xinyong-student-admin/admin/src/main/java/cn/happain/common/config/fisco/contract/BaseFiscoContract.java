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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Lazy;
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
public abstract class BaseFiscoContract {

    // ======================== 1. 通用属性（子类共享）========================
    @Lazy
    @Autowired(required = false)
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
     * 启动时只加载本地 ABI/BIN 文件，不触发任何网络连接。
     * 区块链连接推迟到第一次实际调用时（见 ensureTxProcessor）。
     */
    @PostConstruct
    public final void init() {
        try {
            initExtra();
            loadContractAbiAndBin();
        } catch (Exception e) {
            System.err.println("合约 [" + contractKey + "] 本地配置加载失败: " + e.getMessage());
        }
    }

    /**
     * 懒连接：首次调用合约方法时才真正连接节点、创建交易处理器。
     * 节点不可达时抛出 RuntimeException，由上层业务捕获并返回友好提示。
     */
    protected synchronized void ensureTxProcessor() throws Exception {
        if (this.txProcessor != null) return;
        try {
            createTxProcessor();
        } catch (Exception e) {
            this.txProcessor = null;
            throw new RuntimeException("区块链节点不可用，请检查节点是否已启动: " + e.getMessage(), e);
        }
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





    // ======================== 4. 合约执行（懒连接保护）========================
    public TransactionResponse setJsonStr(String jsonStr) throws Exception {
        ensureTxProcessor();
        return this.txProcessor.sendTransactionAndGetResponse(this.contractAddress, this.ABI, "setJsonStr", ListUtil.of(jsonStr));
    }

    public CallResponse getJsonStr() throws Exception {
        ensureTxProcessor();
        return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.contractAddress, this.ABI, "getJsonStr", Arrays.asList());
    }
}

