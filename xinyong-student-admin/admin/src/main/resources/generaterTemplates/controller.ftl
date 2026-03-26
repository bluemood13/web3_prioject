package ${packageName}.controller;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.domain.dto.valid.BaseGroup;
import ${packageName}.domain.model.dto.${modelName}Dto;
import ${packageName}.domain.model.vo.${modelName}Vo;
import ${packageName}.service.impl.${modelName}ServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/${tablePrefix}/${lowerModelName}")
public class ${modelName}Controller {
    @Resource
    ${modelName}ServiceImpl ${lowerModelName}Service;


    @PostMapping("/add")
    public Response add(@Validated(BaseGroup.add.class) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
        return ${lowerModelName}Service.add(${lowerModelName}Dto);
    }


    @PostMapping("/upd")
    public Response upd(@Validated(BaseGroup.upd.class) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
       return ${lowerModelName}Service.upd(${lowerModelName}Dto);
    }


    @PostMapping("/del")
    public Response del(@Validated(BaseGroup.del.class) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
      return ${lowerModelName}Service.del(${lowerModelName}Dto);
    }


    @PostMapping("/detail")
    public Response<${modelName}Vo> detail(@Validated(BaseGroup.detail.class) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
        return ${lowerModelName}Service.detail(${lowerModelName}Dto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<${modelName}Vo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
        return ${lowerModelName}Service.listPage(${lowerModelName}Dto);
    }


    @PostMapping("/list/all")
    public Response<List<${modelName}Vo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody ${modelName}Dto ${lowerModelName}Dto) {
       return ${lowerModelName}Service.listAll(${lowerModelName}Dto);
    }



}