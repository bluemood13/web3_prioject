/**
 * 获取views下的全部组件
 */
export const initComponents = () => {
  const componentList: any = {};
  let modules = import.meta.glob("@/views/**/*.vue");
  for (const item in modules) {
    const match: any = item.match(/\/src\/views(.*?)\.vue/);
    if (match.length > 1) {
      componentList[match[1]] = modules[item];
    }
  }
  return componentList;
};

// 全局的组件列表
export const componentsList = initComponents();

/**
 * 将列表菜单转为多层级的菜单
 * @param menuList
 */
export const menuListToMenuListChildren = (menuList: MenuVo[]): MenuVo[] => {
  const result: MenuVo[] = []; // 用于存放多层级菜单的结果
  const map = new Map<number, MenuVo>(); // 创建一个 Map 存储 id 和菜单项的映射关系
  // 初始化 map，便于快速查找每个菜单项
  menuList.forEach((item) => {
    map.set(item.id, { ...item, children: [] });
  });
  menuList.forEach((item: MenuVo) => {
    // 获取当前项
    const current = map.get(item.id);
    // 判断是否是顶级菜单
    if (item.parentId === 0) {
      // 顶级菜单，直接添加到结果数组
      result.push(current!);
    } else {
      // 查找父级菜单，将当前项放入父级的 children 中
      const parent = map.get(item.parentId);
      if (parent) {
        parent.children!.push(current!);
      }
    }
  });

  /**
   * 递归对菜单项及其子菜单进行排序
   */
  const sortMenus = (menus: MenuVo[]) => {
    menus.sort((a, b) => a.rank - b.rank); // 根据 rank 对当前菜单排序
    menus.forEach((menu) => {
      if (menu.children && menu.children.length > 0) {
        sortMenus(menu.children); // 对子菜单递归排序
      }
    });
  };
  // 对顶级菜单和子菜单进行排序
  sortMenus(result);
  return result; // 返回多层级菜单
};

/**
 * 将树形菜单整合成前端路由的格式
 * @param menuListChildren
 */
export const menuListChildrenTo = (menuListChildren: MenuVo[]): MenuRoute[] => {
  const result: MenuRoute[] = []; // 用于存放多层级菜单的结果
  for (const item of menuListChildren) {
    // 判断菜单是否隐藏
    if (item.isHide) {
      continue;
    }

    const route: MenuRoute = menuToRouteMenu(
      item,
      item.children?.length ? menuListChildrenTo(item.children) : [],
    );

    result.push(route);
  }
  return result;
};
/**
 * 将后端的菜单处理为前端能显示的菜单形式
 * @param menuList
 */
export const getRouteMenuList = (menuList: MenuVo[]): MenuRoute[] => {
  // 获取路由的树形菜单
  const menu = menuListChildrenTo(menuList);
  console.log("menu :>> ", menu);
  return menu;
};

/**
 * 获取一级菜单列表
 * @param menuList
 * @returns
 */
export const getFirstMenuList = (menuList: MenuVo[]): MenuRoute[] => {
  const result: MenuRoute[] = []; // 用于存放多层级菜单的结果
  for (const item of menuList) {
    // 判断菜单是否隐藏
    if (item.isHide) {
      continue;
    }
    // 只获取一级菜单
    if (item.parentId === 0) {
      const route: MenuRoute = menuToRouteMenu(item);
      result.push(route);
    }
  }
  return result;
};

/**
 * 将menu菜单数据转为带meta的数据
 */
export const menuToRouteMenu = (
  item: MenuVo,
  children: any = [],
): MenuRoute => {
  const route: MenuRoute = {
    path: item.path,
    name: item.name,
    // 如果是外链组件就失效
    component: item.isFrame
      ? undefined
      : item.component
        ? componentsList[item.component]
        : undefined,
    redirect: item.isFrame ? item.path : undefined, // 如果是外部链接，设置 redirect
    meta: {
      title: item.title,
      id: item.id,
      parentId: item.id,
      rank: item.rank,
      icon: item.icon,
      isHide: item.isHide || false,
      isFrame: item.isFrame || false,
      isAffix: item.isAffix || false,
      isCache: item.isCache || false,
    },
    children: children,
  };
  return route;
};
/**
 * 获取菜单中最后一层的数据
 * @param menuList
 * @returns
 */
export const getLastMenuList = (menuList: MenuVo[]): MenuRoute[] => {
  const result: MenuRoute[] = [];
  // 第一次循环：检查每个菜单项是否存在于其他项的 parentId 中
  for (const item of menuList) {
    let flag = true;
    for (const checkItem of menuList) {
      if (checkItem.parentId === item.id) {
        flag = false;
        break;
      }
      // 添加到列表中
      if (flag) {
        // 将 MenuVo 转换成 MenuRoute
        const route: MenuRoute = menuToRouteMenu(item);
        result.push(route);
      }
    }
  }

  return result;
};
