package cn.happain.common.modules.web3j.service;

import cn.happain.common.config.fisco.contract.BaseFiscoContract;
import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.common.modules.encrypt.utils.EncryptUtil;
import cn.happain.common.modules.fisco.contract.DataContract;
import cn.happain.common.modules.fisco.service.FiscoService;
import cn.happain.common.modules.ganahce.service.GanacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Service
public class Web3jService {
    @Value("${qkl.value}")
    String qklValue;
    @Autowired(required = false)
    GanacheService ganacheService;
    @Autowired(required = false)
    FiscoService fiscoService;
    @Resource
    DataContract dataContract;


    /**
     * 加密标识
     */
    String encryptKey = "";



    /**
     * 获取钱包列表列表
     *
     * @return {@link List }<{@link String }>
     */
    public List<String> getAccountList() {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getAccountList();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到钱包地址
     *
     * @return {@link String}
     * @throws IOException ioexception
     */
    public String getAccount() {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getAccount();
            }else if(qklValue.equals(GlobalConst.Qkl.FISCO_LOCAL) || qklValue.equals(GlobalConst.Qkl.FISCO_REMOTE)) {
                return fiscoService.getAccount();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到钱包地址通过下标
     *
     * @param index 指数
     * @return {@link String}
     */
    public String getAccountByIndex(Integer index) {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getAccount(index);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 得到钱包余额
     *
     * @throws IOException ioexception
     */
    public BigDecimal getBalance() {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getBalance();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 得到钱包余额通过下标
     *
     * @throws IOException ioexception
     */
    public BigDecimal getBalanceByIndex(Integer index) {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getBalance(index);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 得到钱包余额通过钱包地址
     *
     * @throws IOException ioexception
     */
    public BigDecimal getBalanceByAddress(String address) {
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getBalance(address);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 获得目前已有的区块数量
     *
     * @return {@link BigInteger}
     * @throws IOException ioexception
     */
    public Integer getBlockNumber(){
        try {
            if(qklValue.equals(GlobalConst.Qkl.GANACHE ) || qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.getBlockNumber();
            }else if(qklValue.equals(GlobalConst.Qkl.FISCO_LOCAL) || qklValue.equals(GlobalConst.Qkl.FISCO_REMOTE)) {
                return fiscoService.getBlockNumber();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装合约交易
     *
     * @param data       数据
     * @param encryptKey 加密密钥
     * @return {@link String }
     */
    public BlockVo contractTransaction(String data, BaseFiscoContract contract,String encryptKey) {
        try {
            // TODO: 加密逻辑执行在外面
            data = EncryptUtil.encryptData(data,encryptKey);
            // TODO: 执行区块链操作
            if(qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                return ganacheService.contractTransaction(data,contract);
            }else if(qklValue.equals(GlobalConst.Qkl.GANACHE)) {
                return ganacheService.transactionSelf(data);
            }
            else if(qklValue.equals(GlobalConst.Qkl.FISCO_LOCAL) || qklValue.equals(GlobalConst.Qkl.FISCO_REMOTE)) {
                return fiscoService.contractTransaction(data,contract);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 合约交易,使用默认合约
     *
     * @param data       数据
     * @param encryptKey 加密密钥
     * @return {@link BlockVo }
     */
    public BlockVo contractTransaction(String data, String encryptKey) {
        return contractTransaction(data,dataContract, encryptKey);
    }

    /**
     * 通过智能合约的交易Hash获取自定义数据
     *
     * @param hash       散 列
     * @param encryptKey 加密密钥
     * @return {@link String }
     */
    public String getContractTransactionByTx(String hash,String encryptKey) {
        try {
            String data = null;
            if(qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                data = ganacheService.getContractTransactionByTx(hash);
            }else if(qklValue.equals(GlobalConst.Qkl.GANACHE)) {
                data = ganacheService.getTransactionByTx(hash);
            }else if(qklValue.equals(GlobalConst.Qkl.FISCO_LOCAL) || qklValue.equals(GlobalConst.Qkl.FISCO_REMOTE)) {
                data = fiscoService.getContractTransactionByTx(hash);
            }
            return EncryptUtil.decryptData(data,encryptKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过区块获取自定义的数据
     *
     * @param blockHash  区块哈希
     * @param encryptKey 加密密钥
     * @return {@link String }
     */
    public String getContractTransactionByBlockHash(String blockHash,String encryptKey) {
        try {
            String data = null;
            if(qklValue.equals(GlobalConst.Qkl.GANACHE_CONTRACT)) {
                data = ganacheService.getContractTransactionByBlockHash(blockHash);
            }else if(qklValue.equals(GlobalConst.Qkl.GANACHE)) {
                data = ganacheService.getTransactionByBlockHash(blockHash);
            }
            else if(qklValue.equals(GlobalConst.Qkl.FISCO_LOCAL) || qklValue.equals(GlobalConst.Qkl.FISCO_REMOTE)) {
                data = fiscoService.getContractTransactionByBlockHash(blockHash);
            }
            return EncryptUtil.decryptData(data,encryptKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
