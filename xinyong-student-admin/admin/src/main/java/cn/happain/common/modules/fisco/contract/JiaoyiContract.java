package cn.happain.common.modules.fisco.contract;

import cn.happain.common.config.fisco.contract.BaseFiscoContract;
import cn.hutool.core.collection.ListUtil;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class JiaoyiContract extends BaseFiscoContract {

  public JiaoyiContract() {
    super("jiaoyi");
  }


  public TransactionResponse setJiaoyi(Double num, String types, Double price) throws Exception {
    ensureTxProcessor();
    return this.txProcessor.sendTransactionAndGetResponse(
            this.contractAddress,
            this.ABI,
            "setJiaoyi",
            ListUtil.of(num, types, price)
    );
  }

  public CallResponse getJiaoyi() throws Exception {
    ensureTxProcessor();
    return this.txProcessor.sendCall(
            this.client.getCryptoSuite().getCryptoKeyPair().getAddress(),
            this.contractAddress,
            this.ABI,
            "getJiaoyi",
            Arrays.asList()
    );
  }

  public CallResponse deleteJiaoyi() throws Exception {
    ensureTxProcessor();
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.contractAddress, this.ABI, "deleteJiaoyi", Arrays.asList());
  }

}
