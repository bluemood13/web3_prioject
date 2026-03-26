package cn.happain.module.sys.module.menu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

/**
* @author happain
* @since 2023-04-01
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuVo  {


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
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;
    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer ranks;

    /**
     * 组件路径
     */
    private String component;


    /**
     * 是否外部链接
     */
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


    private List<MenuVo> children;


    /**
     * 角色信息
     */
    private List<Integer> roleIds;

}
