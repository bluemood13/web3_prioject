package cn.happain.common.config.mybatisPlus;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 我 Meta 对象处理程序
 * mybatisplus自动填充功能
 *
 * @author happain
 * @date 2023/01/24
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        try {
            Object o = StpUtil.getLoginId();
            if(o!=null) {
                this.strictInsertFill(metaObject, "createUser", Integer.class, Integer.parseInt(o.toString()));
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        try {
            Object o = StpUtil.getLoginId();
            if (o != null) {
                this.strictInsertFill(metaObject, "updateUser", Integer.class, Integer.parseInt(o.toString()));
            }
        }catch (Exception e) {
        }
    }
}
