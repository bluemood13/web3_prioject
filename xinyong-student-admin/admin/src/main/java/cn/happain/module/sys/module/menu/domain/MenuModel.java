package cn.happain.module.sys.module.menu.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* @author happain
* @since 2023-04-01
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "sys_menu")
public class MenuModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "path")
    private String path;

    @TableField(value = "title")
    private String title;

    @TableField(value = "icon")
    private String icon;

    @TableField(value = "ranks")
    private Integer ranks;

    @TableField(value = "parent_id")
    private Integer parentId;


    @TableField(value = "component")
    private String component;


    @TableField(value = "is_hide")
    private Boolean isHide;


    @TableField(value = "is_frame")
    private Boolean isFrame;

    @TableField(value = "is_affix")
    private Boolean isAffix;

    @TableField(value = "is_cache")
    private Boolean isCache;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Integer createUser;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;

}
