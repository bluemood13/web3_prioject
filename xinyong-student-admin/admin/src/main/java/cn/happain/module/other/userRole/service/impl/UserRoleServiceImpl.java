package cn.happain.module.other.userRole.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.other.userRole.domain.model.dto.UserRoleDto;
import cn.happain.module.other.userRole.domain.model.vo.UserRoleVo;
import cn.happain.module.other.userRole.mapper.UserRoleMapper;
import cn.happain.module.other.userRole.service.IUserRoleService;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleModel> implements IUserRoleService {


     private List<UserRoleVo> fillVo(List<UserRoleVo> userRoleVo) {
        return userRoleVo;
     }


     /**
      * 分页查询
      *
      * @param userRoleDto dto
      * @return {@link Response}<{@link PageVo}<{@link UserRoleVo}>>
      */
     public Response<PageVo<UserRoleVo>> listPage(UserRoleDto userRoleDto) {
         LambdaQueryWrapper<UserRoleModel> lambda = search(userRoleDto);
         Page<UserRoleModel> userRoleModelPage =  userRoleDto.buildPageVo(UserRoleModel.class);
         Page<UserRoleModel> page = page(userRoleModelPage, lambda);
         PageVo<UserRoleVo> userRoleVoPageVo = PageVo.PageModelToVo(page,UserRoleVo.class);
         return Response.successDataDefault(userRoleVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param userRoleDto dto
       * @return {@link Response}<{@link List}<{@link UserRoleVo}>>
       */
      public Response<List<UserRoleVo>> listAll(UserRoleDto userRoleDto) {
         LambdaQueryWrapper<UserRoleModel> lambda = search(userRoleDto);
          List<UserRoleModel> list = list(lambda);
          List<UserRoleVo> userRoleVos = BeanUtil.copyToList(list, UserRoleVo.class);
          return Response.successDataDefault(userRoleVos);
      }


     /**
      * 添加
      *
      * @param userRoleDto dto
      * @return {@link Response}
      */
     public Response add(UserRoleDto userRoleDto) {
         if(hasData(userRoleDto)) {
             return Response.failExitData();
         }
         UserRoleModel userRoleModel = BeanUtil.copyProperties(userRoleDto, UserRoleModel.class);
         save(userRoleModel);
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param userRoleDto dto
      * @return {@link Response}
      */
     public Response upd(UserRoleDto userRoleDto) {
         if(hasData(userRoleDto)) {
             return Response.failExitData();
         }
         UserRoleModel userRoleModel = BeanUtil.copyProperties(userRoleDto, UserRoleModel.class);
         updateById(userRoleModel);
         return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param userRoleDto dto
      * @return {@link Response}
      */
     public Response del(UserRoleDto userRoleDto) {
         removeById(userRoleDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param userRoleDto dto
      * @return {@link Response}
      */
     public Response<UserRoleVo> detail(UserRoleDto userRoleDto) {
         UserRoleModel model = getById(userRoleDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         UserRoleVo userRoleVo = BeanUtil.copyProperties(model, UserRoleVo.class);
         return Response.successDataDefault(userRoleVo);
     }

     /**
      * 有数据
      *
      * @param userRoleDto dto
      * @return boolean
      */
     public boolean hasData(UserRoleDto userRoleDto) {
         LambdaQueryWrapper<UserRoleModel> lambda = new QueryWrapper<UserRoleModel>().lambda();
         if(userRoleDto.getId()!=null) {
             lambda.ne(UserRoleModel::getId,userRoleDto.getId());
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param userRoleDto dto
     * @return {@link LambdaQueryWrapper}<{@link UserRoleModel}>
     */
     public LambdaQueryWrapper<UserRoleModel> search(UserRoleDto userRoleDto) {
         LambdaQueryWrapper<UserRoleModel> lambda = new LambdaQueryWrapper<UserRoleModel>();

         return lambda;
      }
}
