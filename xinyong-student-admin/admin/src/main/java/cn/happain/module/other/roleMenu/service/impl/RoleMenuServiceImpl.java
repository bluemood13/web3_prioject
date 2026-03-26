package cn.happain.module.other.roleMenu.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.other.roleMenu.domain.model.dto.RoleMenuDto;
import cn.happain.module.other.roleMenu.domain.model.vo.RoleMenuVo;
import cn.happain.module.other.roleMenu.mapper.RoleMenuMapper;
import cn.happain.module.other.roleMenu.service.IRoleMenuService;
import cn.happain.module.other.roleMenu.domain.model.RoleMenuModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
* @author happain
* @since 2023-02-15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuModel> implements IRoleMenuService {


     private List<RoleMenuVo> fillVo(List<RoleMenuVo> roleMenuVo) {
        return roleMenuVo;
     }


     /**
      * 分页查询
      *
      * @param roleMenuDto dto
      * @return {@link Response}<{@link PageVo}<{@link RoleMenuVo}>>
      */
     public Response<PageVo<RoleMenuVo>> listPage(RoleMenuDto roleMenuDto) {
         LambdaQueryWrapper<RoleMenuModel> lambda = search(roleMenuDto);
         Page<RoleMenuModel> roleMenuModelPage =  roleMenuDto.buildPageVo(RoleMenuModel.class);
         Page<RoleMenuModel> page = page(roleMenuModelPage, lambda);
         PageVo<RoleMenuVo> roleMenuVoPageVo = PageVo.PageModelToVo(page,RoleMenuVo.class);
         return Response.successDataDefault(roleMenuVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param roleMenuDto dto
       * @return {@link Response}<{@link List}<{@link RoleMenuVo}>>
       */
      public Response<List<RoleMenuVo>> listAll(RoleMenuDto roleMenuDto) {
         LambdaQueryWrapper<RoleMenuModel> lambda = search(roleMenuDto);
          List<RoleMenuModel> list = list(lambda);
          List<RoleMenuVo> roleMenuVos = BeanUtil.copyToList(list, RoleMenuVo.class);
          return Response.successDataDefault(roleMenuVos);
      }


     /**
      * 添加
      *
      * @param roleMenuDto dto
      * @return {@link Response}
      */
     public Response add(RoleMenuDto roleMenuDto) {
         if(hasData(roleMenuDto)) {
             return Response.failExitData();
         }
         RoleMenuModel roleMenuModel = BeanUtil.copyProperties(roleMenuDto, RoleMenuModel.class);
         save(roleMenuModel);
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param roleMenuDto dto
      * @return {@link Response}
      */
     public Response upd(RoleMenuDto roleMenuDto) {
         if(hasData(roleMenuDto)) {
             return Response.failExitData();
         }
         RoleMenuModel roleMenuModel = BeanUtil.copyProperties(roleMenuDto, RoleMenuModel.class);
         updateById(roleMenuModel);
         return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param roleMenuDto dto
      * @return {@link Response}
      */
     public Response del(RoleMenuDto roleMenuDto) {
         removeById(roleMenuDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param roleMenuDto dto
      * @return {@link Response}
      */
     public Response<RoleMenuVo> detail(RoleMenuDto roleMenuDto) {
         RoleMenuModel model = getById(roleMenuDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         RoleMenuVo roleMenuVo = BeanUtil.copyProperties(model, RoleMenuVo.class);
         return Response.successDataDefault(roleMenuVo);
     }

     /**
      * 有数据
      *
      * @param roleMenuDto dto
      * @return boolean
      */
     public boolean hasData(RoleMenuDto roleMenuDto) {
         LambdaQueryWrapper<RoleMenuModel> lambda = new QueryWrapper<RoleMenuModel>().lambda();
         if(roleMenuDto.getId()!=null) {
             lambda.ne(RoleMenuModel::getId,roleMenuDto.getId());
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param roleMenuDto dto
     * @return {@link LambdaQueryWrapper}<{@link RoleMenuModel}>
     */
     public LambdaQueryWrapper<RoleMenuModel> search(RoleMenuDto roleMenuDto) {
         LambdaQueryWrapper<RoleMenuModel> lambda = new LambdaQueryWrapper<RoleMenuModel>();

         return lambda;
      }
}
