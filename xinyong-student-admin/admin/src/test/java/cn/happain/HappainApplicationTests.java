package cn.happain;

import cn.happain.common.config.fisco.utils.IOUtil;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.common.modules.fisco.contract.DataContract;
import cn.happain.common.modules.fisco.service.FiscoService;
import cn.happain.common.modules.ganahce.service.GanacheService;
import cn.happain.common.modules.generater.GeneraterService;
import cn.happain.common.modules.web3j.service.Web3jService;
import cn.happain.common.utils.HappainRequestUtil;
import cn.happain.common.utils.SolanaDataProcessor;
import cn.happain.common.utils.SolanaSpiderUtil;
import cn.happain.module.solana.domain.SolanaCoreData;
import cn.happain.module.xinyong.module.solana.domain.SolanaModel;
import cn.happain.module.xinyong.module.solana.service.impl.SolanaServiceImpl;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.tuple.Pair;
import org.fisco.bcos.sdk.abi.ABICodec;
import org.fisco.bcos.sdk.abi.wrapper.ABIObject;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.model.EventLog;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class HappainApplicationTests {

    @Resource
    GeneraterService generaterService;
    @Resource
    Web3jService web3jService;
    @Autowired(required = false)
    GanacheService ganacheService;
    @Autowired(required = false)
    FiscoService fiscoService;
    @Resource
    Client client;
    @Resource
    DataContract dataContract;


    @Test
    void test() throws TemplateException, IOException, SQLException {
        generaterService.createCode("happain-admin-ts", "sys_menu", "cn.happain.module.system.module", "sys_");
    }

    /**
     * 合约部署
     *
     * @throws Exception 例外
     */
    @Test
    void deployContact() throws Exception {
        ganacheService.deployContact();
    }


    @Test
    void ganacheTest() throws Exception {
        BlockVo blockVo = ganacheService.transaction("测试", new BigDecimal("1"), "0x6A0B557DEAF908538a2758F2B5a8e17788c3aC69", "0xae01dF4dB41A58307e00543D047EFda437234dC8");
        System.out.println(blockVo);
    }

    @Test
    void fiscoTest() throws Exception {
        TransactionResponse demo = dataContract.setData("demo");
        TransactionReceipt transactionReceipt1 = demo.getTransactionReceipt();
        String blockHash = transactionReceipt1.getBlockHash();
        String transactionHash = transactionReceipt1.getTransactionHash();
        BigInteger decimalBlockNumber = new BigInteger(transactionReceipt1.getBlockNumber().replace("0x", ""), 16);
        System.out.println(decimalBlockNumber.intValue());
        System.out.println(blockHash);
        System.out.println(transactionHash);
    }

    /**
     * fisco通过方法的参数进行获取数据
     *
     * @throws Exception
     */
    @Test
    void fiscoMethod() throws Exception {
        BcosTransactionReceipt receiptResponse = client.getTransactionReceipt("0x7cfaafed3af46880f714faffd74d93590946f55a1299860a3af7da60e6c84b64");
        Optional<TransactionReceipt> receiptOpt = receiptResponse.getTransactionReceipt();
        TransactionReceipt transactionReceipt = receiptOpt.get();

        CryptoSuite cryptoSuite = client.getCryptoSuite();
        String abi = IOUtil.readString("fisco-remote/contract/user/user.abi");
        ABICodec abiCodec = new ABICodec(cryptoSuite);
        Pair<List<Object>, List<ABIObject>> setmUser = abiCodec.decodeMethodInput(abi, transactionReceipt.getInput(), "setUser", null);
        List<Object> params = setmUser.getLeft();
        String username = (String) params.get(0);
        BigInteger age = (BigInteger) params.get(1);
        System.out.println("\n解析到UserSet事件数据:");
        System.out.println("用户名: " + username);
        System.out.println("年龄: " + age);
    }

    /**
     * FISCO通过事件来获取数据
     *
     * @throws Exception 例外
     */
    @Test
    void fiscoEvent() throws Exception {
        BcosTransactionReceipt receiptResponse = client.getTransactionReceipt("0x7cfaafed3af46880f714faffd74d93590946f55a1299860a3af7da60e6c84b64");
        Optional<TransactionReceipt> receiptOpt = receiptResponse.getTransactionReceipt();
        TransactionReceipt transactionReceipt = receiptOpt.get();
        List<TransactionReceipt.Logs> logs = transactionReceipt.getLogs();
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        String abi = IOUtil.readString("fisco-remote/contract/user/user.abi");
        ABICodec abiCodec = new ABICodec(cryptoSuite);
        EventLog eventLog = new EventLog();
        for (TransactionReceipt.Logs log : logs) {
            eventLog.setAddress(log.getAddress());
            eventLog.setTopics(log.getTopics());
            eventLog.setData(log.getData());
            try {
                List<Object> userSet = abiCodec.decodeEvent(abi, "UserSet", eventLog);
                String username = (String) userSet.get(0);
                BigInteger age = (BigInteger) userSet.get(1);
                System.out.println("\n解析到UserSet事件数据:");
                System.out.println("用户名: " + username);
                System.out.println("年龄: " + age);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    void tests() throws Exception {
        String aes = "des3";
        BlockVo blockVo = web3jService.contractTransaction("你好", aes);
        String contractTransactionByTx = web3jService.getContractTransactionByTx(blockVo.getTx(), aes);
        String contractTransactionByBlockHash = web3jService.getContractTransactionByBlockHash(blockVo.getHash(), aes);
    }

    @Resource
    SolanaDataProcessor solanaDataProcessor;

    /**
     * 获取远程最新的solana的价格数据
     *
     * @throws IOException          ioexception
     * @throws InterruptedException 中断异常
     */
    @Test
    void saveSolonaData() throws IOException, InterruptedException {
        HttpResponse httpResponse = HappainRequestUtil.get("https://api.coingecko.com/api/v3/coins/solana", "127.0.0.1", 7890);
        String rawJson = httpResponse.body();

        try {
            SolanaCoreData solanaCoreData = solanaDataProcessor.extractCoreData(rawJson);
            solanaDataProcessor.saveCoreData(solanaCoreData);
        } catch (Exception e) {
            System.err.println("数据解析失败：" + e.getMessage());
        }
    }
}
