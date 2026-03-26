package cn.happain.common.domain.dto;

import cn.happain.common.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author happain
 * @date 2023/2/12
 * @desc
 */
@Data
@Accessors(chain = true)
public class PageDto {
    private Integer pageSize;


    private Integer pageNum;

    /**
     * 创建分页对象
     *
     * @param obj obj
     * @return {@link PageVo}<{@link T}>
     */
    public <T> Page<T> buildPageVo(Class<T> obj) {
        Page<T> tPageVo = new Page<>();
        tPageVo.setSize(getPageNum());
        tPageVo.setCurrent(getPageSize());
        return tPageVo;
    }
}
