package cn.happain.module.xinyong.module.solana.mapper;
import cn.happain.module.xinyong.module.solana.domain.SolanaModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface SolanaMapper extends BaseMapper<SolanaModel> {

}