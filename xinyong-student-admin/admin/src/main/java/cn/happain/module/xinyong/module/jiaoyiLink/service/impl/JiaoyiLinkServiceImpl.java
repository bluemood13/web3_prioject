package cn.happain.module.xinyong.module.jiaoyiLink.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.dto.JiaoyiLinkDto;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.vo.JiaoyiLinkVo;
import cn.happain.module.xinyong.module.jiaoyiLink.mapper.JiaoyiLinkMapper;
import cn.happain.module.xinyong.module.jiaoyiLink.service.IJiaoyiLinkService;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.JiaoyiLinkModel;
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
import cn.happain.module.xinyong.module.jiaoyi.service.impl.JiaoyiServiceImpl;
import cn.happain.module.xinyong.module.jiaoyi.domain.JiaoyiModel;
import cn.happain.module.xinyong.module.jiaoyi.domain.vo.JiaoyiVo;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class JiaoyiLinkServiceImpl extends ServiceImpl<JiaoyiLinkMapper, JiaoyiLinkModel> implements IJiaoyiLinkService {
    String encryptKey = "";
    
    
    @Resource
    JiaoyiServiceImpl jiaoyiService;
    
    
    

     public List<JiaoyiLinkVo> fillVo(List<JiaoyiLinkVo> jiaoyiLinkVo) {
        for (JiaoyiLinkVo vo : jiaoyiLinkVo) {
            fillVoOne(vo);
        }
        return jiaoyiLinkVo;
    }

     public JiaoyiLinkVo fillVoOne(JiaoyiLinkVo vo)  {
        if(vo.getJiaoyiId()!=null) {
            JiaoyiModel jiaoyiModel = jiaoyiService.getById(vo.getJiaoyiId());
            if(jiaoyiModel!=null) {
                vo.setJiaoyiIdVo(BeanUtil.copyProperties(jiaoyiModel, JiaoyiVo.class)); 
            }
        }
        return vo;
     }


     /**
      * 分页查询
      *
      * @param jiaoyiLinkDto dto
      * @return {@link Response}<{@link PageVo}<{@link JiaoyiLinkVo}>>
      */
     public Response<PageVo<JiaoyiLinkVo>> listPage(JiaoyiLinkDto jiaoyiLinkDto) {
         LambdaQueryWrapper<JiaoyiLinkModel> lambda = search(jiaoyiLinkDto);
         Page<JiaoyiLinkModel> jiaoyiLinkModelPage =  jiaoyiLinkDto.buildPageVo(JiaoyiLinkModel.class);
         Page<JiaoyiLinkModel> page = page(jiaoyiLinkModelPage, lambda);
         PageVo<JiaoyiLinkVo> jiaoyiLinkVoPageVo = PageVo.PageModelToVo(page,JiaoyiLinkVo.class);
         jiaoyiLinkVoPageVo.setList(fillVo(jiaoyiLinkVoPageVo.getList()));
         return Response.successDataDefault(jiaoyiLinkVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param jiaoyiLinkDto dto
       * @return {@link Response}<{@link List}<{@link JiaoyiLinkVo}>>
       */
      public Response<List<JiaoyiLinkVo>> listAll(JiaoyiLinkDto jiaoyiLinkDto) {
         LambdaQueryWrapper<JiaoyiLinkModel> lambda = search(jiaoyiLinkDto);
          List<JiaoyiLinkModel> list = list(lambda);
          List<JiaoyiLinkVo> jiaoyiLinkVos = BeanUtil.copyToList(list, JiaoyiLinkVo.class);
          return Response.successDataDefault(fillVo(jiaoyiLinkVos));
      }


     /**
      * 添加
      *
      * @param jiaoyiLinkDto dto
      * @return {@link Response}
      */
     public Response add(JiaoyiLinkDto jiaoyiLinkDto) {
         JiaoyiLinkModel jiaoyiLinkModel = BeanUtil.copyProperties(jiaoyiLinkDto, JiaoyiLinkModel.class);
         
        
         save(jiaoyiLinkModel);
         
        return Response.successDefault();
         
         
     }

     /**
      * 修改
      *
      * @param jiaoyiLinkDto dto
      * @return {@link Response}
      */
     public Response upd(JiaoyiLinkDto jiaoyiLinkDto) {
         JiaoyiLinkModel jiaoyiLinkModel = BeanUtil.copyProperties(jiaoyiLinkDto, JiaoyiLinkModel.class);
         
        
         updateById(jiaoyiLinkModel);
         

        return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param jiaoyiLinkDto dto
      * @return {@link Response}
      */
     public Response del(JiaoyiLinkDto jiaoyiLinkDto) {
         removeById(jiaoyiLinkDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param jiaoyiLinkDto dto
      * @return {@link Response}
      */
     public Response<JiaoyiLinkVo> detail(JiaoyiLinkDto jiaoyiLinkDto) {
         JiaoyiLinkModel model = getById(jiaoyiLinkDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         JiaoyiLinkVo jiaoyiLinkVo = BeanUtil.copyProperties(model, JiaoyiLinkVo.class);
         return Response.successDataDefault(fillVoOne(jiaoyiLinkVo));
     }

     /**
      * 有数据
      *
      * @param jiaoyiLinkDto dto
      * @return boolean
      */
     public boolean hasData(JiaoyiLinkDto jiaoyiLinkDto) {
         LambdaQueryWrapper<JiaoyiLinkModel> lambda = new QueryWrapper<JiaoyiLinkModel>().lambda();
        lambda.eq(JiaoyiLinkModel::getJiaoyiId,jiaoyiLinkDto.getJiaoyiId());
         if(jiaoyiLinkDto.getId()!=null) {
             lambda.ne(JiaoyiLinkModel::getId,jiaoyiLinkDto.getId());
         }
         if (lambda.getExpression().getNormal().isEmpty()) {
             return false;
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param jiaoyiLinkDto dto
     * @return {@link LambdaQueryWrapper}<{@link JiaoyiLinkModel}>
     */
     public LambdaQueryWrapper<JiaoyiLinkModel> search(JiaoyiLinkDto jiaoyiLinkDto) {
         LambdaQueryWrapper<JiaoyiLinkModel> lambda = new LambdaQueryWrapper<JiaoyiLinkModel>();
        if(jiaoyiLinkDto.getId() != null) {
            lambda.eq(JiaoyiLinkModel::getId,jiaoyiLinkDto.getId());
        }
        if(StrUtil.isNotEmpty(jiaoyiLinkDto.getHash())){
            lambda.like(JiaoyiLinkModel::getHash,jiaoyiLinkDto.getHash());
        }
        if(StrUtil.isNotEmpty(jiaoyiLinkDto.getTx())){
            lambda.like(JiaoyiLinkModel::getTx,jiaoyiLinkDto.getTx());
        }
        if(jiaoyiLinkDto.getJiaoyiId() != null) {
            lambda.eq(JiaoyiLinkModel::getJiaoyiId,jiaoyiLinkDto.getJiaoyiId());
        }
        lambda.orderByDesc(JiaoyiLinkModel::getCreateTime);
         return lambda;
      }
}
