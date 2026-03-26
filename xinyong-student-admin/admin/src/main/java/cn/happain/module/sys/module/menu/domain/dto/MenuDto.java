package cn.happain.module.sys.module.menu.domain.dto;

import cn.happain.common.domain.dto.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
* @author happain
* @since 2023-04-01
*/
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Accessors(chain = true)
public class MenuDto extends PageDto  {

    /**
    * 
    */
    private Integer id;


    /**
    * 路由名称
    */
    private String name;


    /**
    * 路由地址
    */
    private String path;


    /**
    * 菜单名称
    */
    private String title;

    /**
    * 路由图标
    */
    private String icon;


    /**
    * 菜单排序
    */
    private Integer ranks;


    /**
    * 父节点
    */
    private Integer parentId;


    /**
     *组件路径
     */
    private String component;

    private Boolean isFrame;

    /**
     * 是否隐藏菜单
     */
    private Boolean isHide;

    /**
     * 是否需要添加到tab栏
     */
    private Boolean isAffix;

    /**
     * 是否缓存页面
     */
    private Boolean isCache;


    /**
     * 角色Id列表
     */
    private List<Integer> roleIds;


    private Integer roleId;



}
