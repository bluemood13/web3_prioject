package cn.happain.common.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author happain
 * @desc 分页返回类
 * @since 2023/2/16
 */
@Data
@Accessors(chain = true)
public class PageVo<T>  {

    /**
    * 查询数量
    */
    private Long size;
    /**
    * 总数
    */
    private Long total;

    /**
    * 当前页
    */
    private Long page;

    /**
    * 数据集
    */
    private List<T> list;

    /**
     * 将model的分页转成vo的分页
     *
     * @param obj obj
     * @return {@link PageVo}<{@link R}>
     */
    public static  <R> PageVo<R> PageModelToVo(Page<?> page,Class<R> obj) {
        PageVo<R> rPageVo = new PageVo<>();
        rPageVo.setPage(page.getCurrent());
        rPageVo.setSize(page.getSize());
        rPageVo.setTotal(page.getTotal());
        rPageVo.setList(BeanUtil.copyToList(page.getRecords(), obj));
        return rPageVo;
    }

}
