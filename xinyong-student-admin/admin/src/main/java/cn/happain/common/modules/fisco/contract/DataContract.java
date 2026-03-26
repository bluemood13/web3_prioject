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
@ConditionalOnBean(Client.class)
public class DataContract extends BaseFiscoContract {

  public DataContract() {
    // 这边传入标识
    super("data");
  }


  public TransactionResponse setData(String message) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(
            this.contractAddress,
            this.ABI,
            "setData",
            ListUtil.of(message)
    );
  }
  public CallResponse getData() throws Exception {
    return this.txProcessor.sendCall(
            this.client.getCryptoSuite().getCryptoKeyPair().getAddress(),
            this.contractAddress,
            this.ABI,
            "getData",
            Arrays.asList() // 无参数
    );
  }

  public CallResponse deleteData() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.contractAddress, this.ABI, "deleteData", Arrays.asList());
  }

}
