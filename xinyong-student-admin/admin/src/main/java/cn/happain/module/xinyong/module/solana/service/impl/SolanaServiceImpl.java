package cn.happain.module.xinyong.module.solana.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.xinyong.module.solana.domain.dto.SolanaDto;
import cn.happain.module.xinyong.module.solana.domain.vo.SolanaVo;
import cn.happain.module.xinyong.module.solana.mapper.SolanaMapper;
import cn.happain.module.xinyong.module.solana.service.ISolanaService;
import cn.happain.module.xinyong.module.solana.domain.SolanaModel;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.happain.common.utils.HappainGlobalUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import cn.happain.common.domain.vo.BlockVo;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SolanaServiceImpl extends ServiceImpl<SolanaMapper, SolanaModel> implements ISolanaService {
    String encryptKey = "";
    
    
    
    
    

     public List<SolanaVo> fillVo(List<SolanaVo> solanaVo) {
        for (SolanaVo vo : solanaVo) {
            fillVoOne(vo);
        }
        return solanaVo;
    }

     public SolanaVo fillVoOne(SolanaVo vo)  {
        return vo;
     }


     /**
      * 分页查询
      *
      * @param solanaDto dto
      * @return {@link Response}<{@link PageVo}<{@link SolanaVo}>>
      */
     public Response<PageVo<SolanaVo>> listPage(SolanaDto solanaDto) {
         LambdaQueryWrapper<SolanaModel> lambda = search(solanaDto);
         Page<SolanaModel> solanaModelPage =  solanaDto.buildPageVo(SolanaModel.class);
         Page<SolanaModel> page = page(solanaModelPage, lambda);
         PageVo<SolanaVo> solanaVoPageVo = PageVo.PageModelToVo(page,SolanaVo.class);
         solanaVoPageVo.setList(fillVo(solanaVoPageVo.getList()));
         return Response.successDataDefault(solanaVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param solanaDto dto
       * @return {@link Response}<{@link List}<{@link SolanaVo}>>
       */
      public Response<List<SolanaVo>> listAll(SolanaDto solanaDto) {
         LambdaQueryWrapper<SolanaModel> lambda = search(solanaDto);
          List<SolanaModel> list = list(lambda);
          List<SolanaVo> solanaVos = BeanUtil.copyToList(list, SolanaVo.class);
          return Response.successDataDefault(fillVo(solanaVos));
      }


     /**
      * 添加
      *
      * @param solanaDto dto
      * @return {@link Response}
      */
     public Response add(SolanaDto solanaDto) {
         if(hasData(solanaDto)) {
             return Response.failExitData();
         }
         SolanaModel solanaModel = BeanUtil.copyProperties(solanaDto, SolanaModel.class);
         
        
         save(solanaModel);
         
        return Response.successDefault();
         
         
     }

     /**
      * 修改
      *
      * @param solanaDto dto
      * @return {@link Response}
      */
     public Response upd(SolanaDto solanaDto) {
         if(hasData(solanaDto)) {
             return Response.failExitData();
         }
         SolanaModel solanaModel = BeanUtil.copyProperties(solanaDto, SolanaModel.class);
         
        
         updateById(solanaModel);
         

        return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param solanaDto dto
      * @return {@link Response}
      */
     public Response del(SolanaDto solanaDto) {
         removeById(solanaDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param solanaDto dto
      * @return {@link Response}
      */
     public Response<SolanaVo> detail(SolanaDto solanaDto) {
         SolanaModel model = getById(solanaDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         SolanaVo solanaVo = BeanUtil.copyProperties(model, SolanaVo.class);
         return Response.successDataDefault(fillVoOne(solanaVo));
     }

     /**
      * 有数据
      *
      * @param solanaDto dto
      * @return boolean
      */
     public boolean hasData(SolanaDto solanaDto) {
         LambdaQueryWrapper<SolanaModel> lambda = new QueryWrapper<SolanaModel>().lambda();
         lambda.eq(SolanaModel::getCurrentPrice,solanaDto.getCurrentPrice());
         if(solanaDto.getId()!=null) {
             lambda.ne(SolanaModel::getId,solanaDto.getId());
         }
         if (lambda.getExpression().getNormal().isEmpty()) {
             return false;
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param solanaDto dto
     * @return {@link LambdaQueryWrapper}<{@link SolanaModel}>
     */
     public LambdaQueryWrapper<SolanaModel> search(SolanaDto solanaDto) {
         LambdaQueryWrapper<SolanaModel> lambda = new LambdaQueryWrapper<SolanaModel>();
//        if(solanaDto.getId() != null) {
//            lambda.eq(SolanaModel::getId,solanaDto.getId());
//        }
//        if(solanaDto.getCurrentPrice() != null) {
//            lambda.eq(SolanaModel::getCurrentPrice,solanaDto.getCurrentPrice());
//        }
//        if(solanaDto.getHourMaxPrice() != null) {
//            lambda.eq(SolanaModel::getHourMaxPrice,solanaDto.getHourMaxPrice());
//        }
//        if(solanaDto.getHourMinPrice() != null) {
//            lambda.eq(SolanaModel::getHourMinPrice,solanaDto.getHourMinPrice());
//        }
//        if(solanaDto.getHourRate() != null) {
//            lambda.eq(SolanaModel::getHourRate,solanaDto.getHourRate());
//        }
//        if(solanaDto.getDayRate() != null) {
//            lambda.eq(SolanaModel::getDayRate,solanaDto.getDayRate());
//        }
//        if(solanaDto.getMonthRate() != null) {
//            lambda.eq(SolanaModel::getMonthRate,solanaDto.getMonthRate());
//        }
//        if(solanaDto.getShizhi() != null) {
//            lambda.eq(SolanaModel::getShizhi,solanaDto.getShizhi());
//        }
//        if(solanaDto.getChengjiaoliang() != null) {
//            lambda.eq(SolanaModel::getChengjiaoliang,solanaDto.getChengjiaoliang());
//        }
//        if(solanaDto.getTotal() != null) {
//            lambda.eq(SolanaModel::getTotal,solanaDto.getTotal());
//        }
        lambda.orderByDesc(SolanaModel::getCreateTime);
         return lambda;
      }

    public Response<SolanaVo> SolanaVo(SolanaDto solanaDto) {
         // TODO: 随机获取一条数据
        List<SolanaModel> list = list();
        SolanaModel solanaModel = RandomUtil.randomEle(list);
        return Response.successDataDefault(BeanUtil.copyProperties(solanaModel, SolanaVo.class));
    }
}
