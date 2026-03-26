package cn.happain.common.inter;


import cn.happain.common.config.fisco.contract.BaseFiscoContract;
import cn.happain.common.domain.vo.BlockVo;

public interface LinkInter {


    /**
     * 获取当前区块数量（最新区块号）
     * @return 最新区块号，获取失败时返回 null
     */
    Integer getBlockNumber();

    /**
     * 获取默认下标对应的钱包地址（依赖配置的 index）
     * @return 默认钱包地址，下标非法或获取失败时返回 null
     */
    String getAccount();


    /**
     * 发起合约交易（调用合约的 setMessage 方法）
     *
     * @param jsonStr  合约存储的业务数据
     * @param contract 合同
     * @return 合约交易结果封装（包含合约地址、交易Hash等），失败时返回 null
     */
    BlockVo contractTransaction(String jsonStr, BaseFiscoContract contract);

    /**
     * 根据交易Hash获取合约交易的业务数据（调用合约的 getMessage 方法）
     *
     * @param hash 合约交易Hash（唯一标识）
     * @return 合约中存储的业务数据，获取失败或无数据时返回 null
     */
    String getContractTransactionByTx(String hash);

    /**
     * 根据区块Hash获取该区块对应合约交易的业务数据（调用合约的 getMessage 方法）
     *
     * @param blockHash 区块Hash（唯一标识）
     * @return 合约中存储的业务数据，获取失败或无数据时返回 null
     */
    String getContractTransactionByBlockHash(String blockHash);
}
