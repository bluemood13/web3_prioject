package cn.happain.module.sys.module.menu.mapper;

import cn.happain.module.sys.module.menu.domain.MenuModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author happain
* @since 2023-03-04
*/
@Mapper
@Component
//@CacheConfig(cacheNames = "menu", keyGenerator = "cacheKeyGenerator")
public interface MenuMapper extends BaseMapper<MenuModel> {

}