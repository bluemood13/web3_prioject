package cn.happain.module.sys.module.info.service.impl;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.info.domain.model.InfoModel;
import cn.happain.module.sys.module.info.domain.model.dto.InfoDto;
import cn.happain.module.sys.module.info.domain.model.vo.InfoVo;
import cn.happain.module.sys.module.info.mapper.InfoMapper;
import cn.happain.module.sys.module.info.service.IInfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class InfoServiceImpl extends ServiceImpl<InfoMapper, InfoModel> implements IInfoService {


     private List<InfoVo> fillVo(List<InfoVo> infoVo) {
        return infoVo;
     }


     /**
      * 分页查询
      *
      * @param infoDto dto
      * @return {@link Response}<{@link PageVo}<{@link InfoVo}>>
      */
     public Response<PageVo<InfoVo>> listPage(InfoDto infoDto) {
         LambdaQueryWrapper<InfoModel> lambda = search(infoDto);
         Page<InfoModel> infoModelPage =  infoDto.buildPageVo(InfoModel.class);
         Page<InfoModel> page = page(infoModelPage, lambda);
         PageVo<InfoVo> infoVoPageVo = PageVo.PageModelToVo(page,InfoVo.class);
         return Response.successDataDefault(infoVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param infoDto dto
       * @return {@link Response}<{@link List}<{@link InfoVo}>>
       */
      public Response<List<InfoVo>> listAll(InfoDto infoDto) {
         LambdaQueryWrapper<InfoModel> lambda = search(infoDto);
          List<InfoModel> list = list(lambda);
          List<InfoVo> infoVos = BeanUtil.copyToList(list, InfoVo.class);
          return Response.successDataDefault(infoVos);
      }


     /**
      * 添加
      *
      * @param infoDto dto
      * @return {@link Response}
      */
     public Response add(InfoDto infoDto) {
         if(hasData(infoDto)) {
             return Response.failExitData();
         }
         InfoModel infoModel = BeanUtil.copyProperties(infoDto, InfoModel.class);
         save(infoModel);
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param infoDto dto
      * @return {@link Response}
      */
     public Response upd(InfoDto infoDto) {
         if(hasData(infoDto)) {
             return Response.failExitData();
         }
         InfoModel infoModel = BeanUtil.copyProperties(infoDto, InfoModel.class);
         updateById(infoModel);
         return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param infoDto dto
      * @return {@link Response}
      */
     public Response del(InfoDto infoDto) {
         long count = count();
         if(count<=1) {
             return Response.failDefault("只有一个公告不能删除");
         }
         removeById(infoDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param infoDto dto
      * @return {@link Response}
      */
     public Response<InfoVo> detail(InfoDto infoDto) {
         InfoModel model = getById(infoDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         InfoVo infoVo = BeanUtil.copyProperties(model, InfoVo.class);
         return Response.successDataDefault(infoVo);
     }

     /**
      * 有数据
      *
      * @param infoDto dto
      * @return boolean
      */
     public boolean hasData(InfoDto infoDto) {
         LambdaQueryWrapper<InfoModel> lambda = new QueryWrapper<InfoModel>().lambda();
         lambda.eq(InfoModel::getInfo,infoDto.getInfo());
         if(infoDto.getId()!=null) {
             lambda.ne(InfoModel::getId,infoDto.getId());
         }
         if(lambda.getSqlSegment().isEmpty()) {
             return false;
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param infoDto dto
     * @return {@link LambdaQueryWrapper}<{@link InfoModel}>
     */
     public LambdaQueryWrapper<InfoModel> search(InfoDto infoDto) {
         LambdaQueryWrapper<InfoModel> lambda = new LambdaQueryWrapper<InfoModel>();
         if(StrUtil.isNotEmpty(infoDto.getInfo())) {
             lambda.like(InfoModel::getInfo,infoDto.getInfo());
         }
         lambda.orderByDesc(InfoModel::getCreateTime);
         return lambda;
      }

    public Response<InfoVo> getNew(InfoDto infoDto) {
        LambdaQueryWrapper<InfoModel> infoModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        infoModelLambdaQueryWrapper.orderByDesc(InfoModel::getCreateTime);
        InfoModel one = getOne(infoModelLambdaQueryWrapper.last("limit 1"));
        return Response.successDataDefault(BeanUtil.copyProperties(one, InfoVo.class));
    }
}
