package cn.happain.module.sys.module.block.controller;

import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.Response;
import cn.happain.common.modules.web3j.service.Web3jService;
import cn.happain.module.sys.module.block.model.BlockInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/block")
public class BlockController {
    @Resource
    Web3jService web3jService;


    @PostMapping("/info")
    public Response<BlockInfo> info() throws Exception {
        String accout = web3jService.getAccount();
        Integer blockNumber = web3jService.getBlockNumber();
        BlockInfo blockInfo = new BlockInfo();
        blockInfo.setAccount(accout);
        blockInfo.setBlockNum(blockNumber);
        blockInfo.setGasLimit(GlobalConst.GAS_LIMIT);
        blockInfo.setGasPrice(GlobalConst.GAS_PRICE);
        return Response.successDataDefault(blockInfo);
    }


    /**
     * 获取账户地址列表
     *
     * @return {@link Response }<{@link List }<{@link String }>>
     * @throws Exception 例外
     */
    @PostMapping("/getAddressList")
    public Response<List<String>> getAddressList() throws Exception {
        List<String> accountList = web3jService.getAccountList();
        return Response.successDataDefault(accountList);

    }
}