package cn.happain.common.modules.ganahce.service;

import cn.happain.common.config.fisco.contract.BaseFiscoContract;
import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.common.ganache.heyue.DataContract;
import cn.happain.common.inter.LinkInter;
import com.webank.blockchain.hsm.crypto.utils.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@ConditionalOnBean(Web3j.class)
public class GanacheService implements LinkInter {

    @Resource
    Web3j web3j;
    @Value("${ganache.primaryKey}")
    String primaryKey;
    @Value("${ganache.index}")
    Integer index;
    @Value("${ganache.contractAddress}")
    String contractAddress;


    /**
     * 获取钱包列表
     *
     * @return {@link List }<{@link String }>
     */
    public List<String> getAccountList() {
        try {
            return web3j.ethAccounts().send().getAccounts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取区块数量
     *
     * @return {@link BigInteger }
     */
    public Integer getBlockNumber() {
        try {
            EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
            return ethBlockNumber.getBlockNumber().intValue();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取默认钱包地址
     *
     * @return {@link String }
     */
    public String getAccount() {
        return getAccount(index);
    }

    /**
     * 根据下标获取钱包地址
     *
     * @param defaultIndex 默认索引
     * @return {@link String }
     */
    public String getAccount(Integer defaultIndex) {
        try {
            if (defaultIndex == null || defaultIndex < 0 || defaultIndex > 9) {
                return null;
            }
            List<String> accounts = web3j.ethAccounts().send().getAccounts();
            return accounts.get(defaultIndex);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得默认钱包余额
     *
     * @return {@link BigDecimal }
     */
    public BigDecimal getBalance() {
        return getBalance(index);
    }

    /**
     * 根据下标获取钱包余额
     *
     * @param defaultIndex 默认索引
     * @return {@link BigDecimal }
     */
    public BigDecimal getBalance(Integer defaultIndex) {
        try {
            String address = getAccount(defaultIndex);
            DefaultBlockParameterName defaultBlockParameter = DefaultBlockParameterName.LATEST;
            EthGetBalance balance = web3j.ethGetBalance(address, defaultBlockParameter).send();
            BigInteger wei = balance.getBalance();
            return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得钱包地址获取钱包余额
     *
     * @param address 地址
     * @return {@link BigDecimal }
     */
    public BigDecimal getBalance(String address) {
        try {
            DefaultBlockParameterName defaultBlockParameter = DefaultBlockParameterName.LATEST;
            EthGetBalance balance = web3j.ethGetBalance(address, defaultBlockParameter).send();
            BigInteger wei = balance.getBalance();
            return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 默认合约部署
     */
    public void deployContact() {
        deployContactByPrimaryKey(primaryKey);
    }


    /**
     * 根据私钥进行用户认证部署合约
     *
     * @param defaultPrimaryKey 默认主键
     */
    public void deployContactByPrimaryKey(String defaultPrimaryKey) {
        try {
            if (defaultPrimaryKey == null) {
                defaultPrimaryKey = primaryKey;
            }
            RemoteCall<DataContract> deploy = DataContract.deploy(web3j,
                    Credentials.create(defaultPrimaryKey),
                    new StaticGasProvider(new BigInteger(String.valueOf(GlobalConst.GAS_PRICE)), new BigInteger(String.valueOf(GlobalConst.GAS_LIMIT)))
            );
            DataContract send = deploy.send();
            System.err.println("合约地址：" + send.getContractAddress());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 发起普通交易,自己转自己
     *
     * @param jsonStr     json str
     * @param money       钱
     * @param fromAddress 发件人地址
     * @param toAddress   解决
     * @return {@link HashMap }<{@link String },{@link String }>
     */
    public BlockVo transaction(String jsonStr, BigDecimal money, String fromAddress, String toAddress) {
        BlockVo blockVo = new BlockVo();
        try {

            // TODO: 2023/11/9   每单位gas的价格为1以太坊币
            BigInteger gasPrice = BigInteger.valueOf(GlobalConst.GAS_PRICE);
            // TODO: 2023/11/9   最大的gas数量
            BigInteger gasLimit = BigInteger.valueOf(GlobalConst.GAS_LIMIT);
            // TODO: 2023/11/8 设置转账0以太坊币
            BigInteger value = Convert.toWei(money.toString(), Convert.Unit.ETHER).toBigInteger();
            // TODO: 2023/11/9 自定义的交易数据
            String encodedData = Numeric.toHexString(jsonStr.getBytes());
            // TODO: 2023/11/9  构造交易
            Transaction transaction = Transaction.createFunctionCallTransaction(
                    fromAddress, // 交易发送者地址
                    null, // 交易 nonce，默认为 null，由 web3j 自动获取
                    gasPrice, // gas 价格
                    gasLimit, // gas 上限
                    toAddress, // 接收者地址
                    value, // 转账金额
                    encodedData // 交易数据
            );
            // TODO: 2023/11/9  发送交易
            String hash = web3j.ethSendTransaction(transaction).send().getTransactionHash();

            EthTransaction send = web3j.ethGetTransactionByHash(hash).send();
            Optional<org.web3j.protocol.core.methods.response.Transaction> transaction1 = send.getTransaction();
            org.web3j.protocol.core.methods.response.Transaction transaction2 = transaction1.get();
            String blockHash1 = transaction2.getBlockHash();
            // 区块hash
            blockVo.setHash(blockHash1);
            // 交易hash
            blockVo.setTx(hash);
            // 当前区块的位置
            blockVo.setNumber(transaction2.getBlockNumber().intValue());
            return blockVo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 发起自身转自身的交易
     *
     * @param jsonStr json str
     * @return {@link HashMap }<{@link String },{@link String }>
     */
    public BlockVo transactionSelf(String jsonStr) {
        String account = getAccount();
        return transaction(jsonStr, new BigDecimal(0), account, account);
    }


    /**
     * 根据交易hash获取数据
     *
     * @param hash 散 列
     * @return {@link String }
     */
    public String getTransactionByTx(String hash) {
        try {
            EthTransaction transaction = web3j.ethGetTransactionByHash(hash).send();
            String input = transaction.getResult().getInput();
            byte[] inputBytes = Hex.decode(input.substring(2));
            String data = new String(inputBytes, StandardCharsets.UTF_8);
            return data;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过区块哈希获取数据
     *
     * @param blockHash 区块哈希
     * @return {@link String }
     */
    public String getTransactionByBlockHash(String blockHash) {
        try {
            EthBlock ethBlock = web3j.ethGetBlockByHash(blockHash, false).send();
            List<EthBlock.TransactionResult> transactions = ethBlock.getBlock().getTransactions();
            for (EthBlock.TransactionResult transactionResult : transactions) {
                Object transactionObj = transactionResult.get();
                EthTransaction transaction = web3j.ethGetTransactionByHash(transactionObj.toString()).send();
                String input = transaction.getResult().getInput();
                byte[] inputBytes = Hex.decode(input.substring(2));
                String result = new String(inputBytes, StandardCharsets.UTF_8);
                return result;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 基础合约交易
     *
     * @param jsonStr json str
     * @return {@link HashMap }<{@link String }, {@link String }>
     */
    @Override
    public BlockVo contractTransaction(String jsonStr, BaseFiscoContract baseFiscoContract) {
        BlockVo blockVo = new BlockVo();
        try {
            DataContract contract = DataContract.load(
                    contractAddress,
                    web3j,
                    Credentials.create(primaryKey),
                    new StaticGasProvider(new BigInteger(String.valueOf(GlobalConst.GAS_PRICE)), new BigInteger(String.valueOf(GlobalConst.GAS_LIMIT)))
            );
            TransactionReceipt send = contract.setMessage(jsonStr).send();
            blockVo.setTx(send.getTransactionHash());
            blockVo.setHash(send.getBlockHash());
            blockVo.setContractAddress(contractAddress);
            blockVo.setNumber(send.getBlockNumber().intValue());
            return blockVo;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 【合约】根据交易hash获取数据
     *
     * @param hash 散 列
     * @return {@link String }
     */
    @Override
    public String getContractTransactionByTx(String hash) {
        try {
            TransactionReceipt receipt = web3j.ethGetTransactionReceipt(hash)
                    .send()
                    .getTransactionReceipt().orElse(null);
            BigInteger blockNumber = receipt.getBlockNumber();
            // 构造对 getMessage() 方法的调用
            Function function = new Function(
                    "getMessage",
                    Collections.emptyList(),
                    Arrays.asList(new TypeReference<Utf8String>() {
                    })
            );
            String encodedFunction = FunctionEncoder.encode(function);
            // 通过 eth_call，在该交易所在区块执行调用
            EthCall response = web3j.ethCall(
                    Transaction.createEthCallTransaction(null, contractAddress, encodedFunction),
                    DefaultBlockParameter.valueOf(blockNumber)
            ).send();

            List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
            String data = result.get(0).getValue().toString();

            return data;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 【合约】根据区块hash获取数据
     *
     * @param blockHash 区块哈希
     * @return {@link String }
     */
    @Override

    public String getContractTransactionByBlockHash(String blockHash) {
        try {
            EthBlock blockResponse = web3j.ethGetBlockByHash(blockHash, false).send();
            EthBlock.Block block = blockResponse.getBlock();
            BigInteger blockNumber = block.getNumber();
            // 构造合约函数调用（例如getMessage）
            Function function = new Function(
                    "getMessage",
                    Collections.emptyList(),
                    Arrays.asList(new TypeReference<Utf8String>() {
                    })
            );
            String encodedFunction = FunctionEncoder.encode(function);

            // 使用历史调用，指定该区块号
            EthCall response = web3j.ethCall(
                    Transaction.createEthCallTransaction(null, contractAddress, encodedFunction),
                    DefaultBlockParameter.valueOf(blockNumber)
            ).send();

            List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
            if (result != null && !result.isEmpty()) {
                String message = result.get(0).getValue().toString();
                return message;
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



}
