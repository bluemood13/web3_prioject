package cn.happain.common.utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 递归处理树形数据
 *
 * @author happain
 * @desc
 * @date 2024/09/26
 * @since 2023/5/22
 */

public class HappainTreeUtil {


    public static <T> List<T> deptListToTree(List<T> list, int level,
                                             Function<T, Integer> idFunc,
                                             Function<T, Integer> rankFunc,
                                             Function<T, Integer> parentIdFunc,
                                             Function<T, Integer> levelFunc,
                                             Function<T, List<T>> childrenFunc,
                                             BiFunction<T, List<T>, T> setChildrenFunc
    ) {
        List<T> result = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        // TODO: 2023/5/23 根据父节点进行排序 降序
        list.sort(Comparator.comparing(idFunc));
        // TODO: 2023/5/23 再根据rank进行排序
        list.sort(Comparator.comparing(rankFunc));
        Map<Integer, List<T>> map = new HashMap<>();
        for (T t : list) {
            Integer par = parentIdFunc.apply(t);
            if (map.containsKey(par)) {
                map.get(par).add(t);
            } else {
                List<T> children = new ArrayList<>();
                children.add(t);
                map.put(par, children);
            }
        }
        for (T t : list) {
            /*如果是符合查找的初始父节点的话*/
            if (levelFunc.apply(t) == level) {
                result.add(t);
                /*给父节点赋值*/
                buildTree(t, map, setChildrenFunc, idFunc);
            }
        }
        return result;
    }


    /**
     * 列表树
     * 根据父
     *
     * @param list            列表
     * @param rankFunc        排序函数
     * @param parentIdFunc    父id函数
     * @param setChildrenFunc 设置儿童函数
     * @param parentId        父id
     * @param idFunc
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> listToTree(List<T> list, int parentId,
                                         Function<T, Integer> idFunc,
                                         Function<T, Integer> rankFunc,
                                         Function<T, Integer> parentIdFunc,
                                         BiFunction<T, List<T>, T> setChildrenFunc
    ) {
        List<T> result = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        // TODO: 2023/5/23 根据父节点进行排序 降序
//        list.sort(Comparator.comparing(idFunc));
        // TODO: 2023/5/23 再根据rank进行排序
        list.sort(Comparator.comparing(rankFunc).reversed());
        Map<Integer, List<T>> map = new HashMap<>();
        for (T t : list) {
            Integer par = parentIdFunc.apply(t);
            if (map.containsKey(par)) {
                map.get(par).add(t);
            } else {
                List<T> children = new ArrayList<>();
                children.add(t);
                map.put(par, children);
            }
        }
        for (T t : list) {
            /*如果是符合查找的初始父节点的话*/
            if (parentIdFunc.apply(t) == parentId) {
                result.add(t);
                /*给父节点赋值*/
                buildTree(t, map, setChildrenFunc, idFunc);
            }
        }
        return result;
    }

    private static <T> void buildTree(T t, Map<Integer, List<T>> map,
                                      BiFunction<T, List<T>,T> setChildrenFunc,
                                      Function<T, Integer> idFunc) {
        List<T> children = new ArrayList<>();
        /*获取父节点id*/
        int id = idFunc.apply(t);
        /*遍历查找parentid符合的父节点id的item*/
        List<T> ts = map.get(id);
        if(ts!=null) {
            children.addAll(ts);
        }
        setChildrenFunc.apply(t, children);
        for (T child : children) {
            buildTree(child, map, setChildrenFunc, idFunc);
        }
    }

}
