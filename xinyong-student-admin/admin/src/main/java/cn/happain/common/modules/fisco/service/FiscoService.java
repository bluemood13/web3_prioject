package cn.happain.common.modules.fisco.service;


import cn.happain.common.config.fisco.contract.BaseFiscoContract;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.common.inter.LinkInter;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service

public class FiscoService implements LinkInter {

    @Lazy
    @Autowired(required = false)
    private Client client;

    /**
     * 获取钱包地址
     *
     * @return {@link String }
     */
    public String getAccount() {
        try {
            return client.getCryptoSuite().getCryptoKeyPair().getAddress();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取用户公钥
     *
     * @return {@link String }
     */
    public String getPublicKey() {
        try {
            return client.getCryptoSuite().getCryptoKeyPair().getHexPublicKey();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户私钥
     *
     * @return {@link String }
     */
    public String getPrivateKey() {
        try {
            return client.getCryptoSuite().getKeyPairFactory().getHexPrivateKey();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取块号
     *
     * @return {@link Integer }
     */
    public Integer getBlockNumber() {
        try {
            BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
            return blockNumber.intValue();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取合约数量
     *
     * @return {@link String }
     */
    public Integer getContractNumber() {
        try {

        }catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过合约执行的返回值解析
     *
     * @param transactionResponse 事务响应
     * @return {@link BlockVo }
     */
    public BlockVo contractTransactionDecode(TransactionResponse transactionResponse) {
        BlockVo blockVo = new BlockVo();
        try {
            org.fisco.bcos.sdk.model.TransactionReceipt transactionReceipt = transactionResponse.getTransactionReceipt();
            String tx = transactionReceipt.getTransactionHash();
            String hash = transactionReceipt.getBlockHash();
            String blockNumber = transactionReceipt.getBlockNumber();
            BigInteger decimalBlockNumber = new BigInteger(blockNumber.replace("0x", ""), 16);
            blockVo.setTx(tx);
            blockVo.setHash(hash);
            blockVo.setNumber(decimalBlockNumber.intValue());
            blockVo.setContractAddress(transactionReceipt.getContractAddress());
            return blockVo;
        }catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 合约交易
     *
     * @param jsonStr json str
     * @return {@link BlockVo }
     */
    public BlockVo contractTransaction(String jsonStr, BaseFiscoContract contract) {
        try {
            // TODO: 这边是执行合约的方法
            TransactionResponse transactionResponse = contract.setJsonStr(jsonStr);
            return contractTransactionDecode(transactionResponse);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过合约的hash获取数据
     *
     * @param hash 散 列
     * @return {@link String }
     */
    @Override
    public String getContractTransactionByTx(String hash) {
        try {
            BcosTransactionReceipt receiptResponse = client.getTransactionReceipt(hash);
            org.fisco.bcos.sdk.model.TransactionReceipt receipt = receiptResponse.getTransactionReceipt().get();
            // 获取交易输入数据
            String input = receipt.getInput();
            CryptoSuite cryptoSuite = client.getCryptoSuite();
            TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
            // 解码回执消息
            String data = decoder.decodeReceiptMessage(input);
            return data;
        }catch (Exception e)  {
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
    @Override
    public String getContractTransactionByBlockHash(String blockHash) {
        try {
            BcosBlock blockResponse = client.getBlockByHash(blockHash, false);
            BcosBlock.Block block = blockResponse.getBlock();
            for (BcosBlock.TransactionResult transactionResult : block.getTransactions()) {
                String txHash = (String) transactionResult.get();
                BcosTransactionReceipt receiptResponse = client.getTransactionReceipt(txHash);
                org.fisco.bcos.sdk.model.TransactionReceipt receipt = receiptResponse.getTransactionReceipt().get();
                String input = receipt.getInput();
                CryptoSuite cryptoSuite = client.getCryptoSuite();
                TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite);
                String data = decoder.decodeReceiptMessage(input);
                return data;
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }


}
