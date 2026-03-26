package ${packageName}.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import ${packageName}.domain.model.dto.${modelName}Dto;
import ${packageName}.domain.model.vo.${modelName}Vo;
import ${packageName}.mapper.${modelName}Mapper;
import ${packageName}.service.I${modelName}Service;
import ${packageName}.domain.model.${modelName}Model;
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

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ${modelName}ServiceImpl extends ServiceImpl<${modelName}Mapper, ${modelName}Model> implements I${modelName}Service {

     public List<${modelName}Vo> fillVo(List<${modelName}Vo> ${lowerModelName}Vo) {
        for (${modelName}Vo vo : ${lowerModelName}Vo) {
            fillVoOne(vo);
        }
        return ${lowerModelName}Vo;
    }

     public ${modelName}Vo fillVoOne(${modelName}Vo vo)  {
        return vo;
     }


     /**
      * 分页查询
      *
      * @param ${lowerModelName}Dto dto
      * @return {@link Response}<{@link PageVo}<{@link ${modelName}Vo}>>
      */
     public Response<PageVo<${modelName}Vo>> listPage(${modelName}Dto ${lowerModelName}Dto) {
         LambdaQueryWrapper<${modelName}Model> lambda = search(${lowerModelName}Dto);
         Page<${modelName}Model> ${lowerModelName}ModelPage =  ${lowerModelName}Dto.buildPageVo(${modelName}Model.class);
         Page<${modelName}Model> page = page(${lowerModelName}ModelPage, lambda);
         PageVo<${modelName}Vo> ${lowerModelName}VoPageVo = PageVo.PageModelToVo(page,${modelName}Vo.class);
         ${lowerModelName}VoPageVo.setList(fillVo(${lowerModelName}VoPageVo.getList()));
         return Response.successDataDefault(${lowerModelName}VoPageVo);
     }
      /**
       * 查询所有
       *
       * @param ${lowerModelName}Dto dto
       * @return {@link Response}<{@link List}<{@link ${modelName}Vo}>>
       */
      public Response<List<${modelName}Vo>> listAll(${modelName}Dto ${lowerModelName}Dto) {
         LambdaQueryWrapper<${modelName}Model> lambda = search(${lowerModelName}Dto);
          List<${modelName}Model> list = list(lambda);
          List<${modelName}Vo> ${lowerModelName}Vos = BeanUtil.copyToList(list, ${modelName}Vo.class);
          return Response.successDataDefault(fillVo(${lowerModelName}Vos));
      }


     /**
      * 添加
      *
      * @param ${lowerModelName}Dto dto
      * @return {@link Response}
      */
     public Response add(${modelName}Dto ${lowerModelName}Dto) {
         if(hasData(${lowerModelName}Dto)) {
             return Response.failExitData();
         }
         ${modelName}Model ${lowerModelName}Model = BeanUtil.copyProperties(${lowerModelName}Dto, ${modelName}Model.class);
         save(${lowerModelName}Model);
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param ${lowerModelName}Dto dto
      * @return {@link Response}
      */
     public Response upd(${modelName}Dto ${lowerModelName}Dto) {
         if(hasData(${lowerModelName}Dto)) {
             return Response.failExitData();
         }
         ${modelName}Model ${lowerModelName}Model = BeanUtil.copyProperties(${lowerModelName}Dto, ${modelName}Model.class);
         updateById(${lowerModelName}Model);
         return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param ${lowerModelName}Dto dto
      * @return {@link Response}
      */
     public Response del(${modelName}Dto ${lowerModelName}Dto) {
         removeById(${lowerModelName}Dto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param ${lowerModelName}Dto dto
      * @return {@link Response}
      */
     public Response<${modelName}Vo> detail(${modelName}Dto ${lowerModelName}Dto) {
         ${modelName}Model model = getById(${lowerModelName}Dto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         ${modelName}Vo ${lowerModelName}Vo = BeanUtil.copyProperties(model, ${modelName}Vo.class);
         return Response.successDataDefault(${lowerModelName}Vo);
     }

     /**
      * 有数据
      *
      * @param ${lowerModelName}Dto dto
      * @return boolean
      */
     public boolean hasData(${modelName}Dto ${lowerModelName}Dto) {
         LambdaQueryWrapper<${modelName}Model> lambda = new QueryWrapper<${modelName}Model>().lambda();
         if(${lowerModelName}Dto.getId()!=null) {
             lambda.ne(${modelName}Model::getId,${lowerModelName}Dto.getId());
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param ${lowerModelName}Dto dto
     * @return {@link LambdaQueryWrapper}<{@link ${modelName}Model}>
     */
     public LambdaQueryWrapper<${modelName}Model> search(${modelName}Dto ${lowerModelName}Dto) {
         LambdaQueryWrapper<${modelName}Model> lambda = new LambdaQueryWrapper<${modelName}Model>();
        <#list tableFields as item>
            <#if item.columnName != 'update_time' && item.columnName != 'update_user' && item.columnName != 'create_user' && item.columnName != 'create_time' && item.columnName!= 'id'>
                <#if item.dataType == 'varchar' || item.dataType =='text'>
                if(StrUtil.isNotEmpty(${lowerModelName}Dto.get${modelName}())) {
                    lambda.like(${modelName}Model::get${modelName},${lowerModelName}Dto.get${modelName}());
                }
                </#if>
            </#if>
        </#list>
         return lambda;
      }
}
