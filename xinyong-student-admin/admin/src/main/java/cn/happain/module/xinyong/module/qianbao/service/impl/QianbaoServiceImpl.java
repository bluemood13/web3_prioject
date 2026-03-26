package cn.happain.module.xinyong.module.qianbao.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.xinyong.module.jiaoyi.domain.dto.JiaoyiDto;
import cn.happain.module.xinyong.module.jiaoyi.service.impl.JiaoyiServiceImpl;
import cn.happain.module.xinyong.module.qianbao.domain.dto.QianbaoDto;
import cn.happain.module.xinyong.module.qianbao.domain.vo.QianbaoVo;
import cn.happain.module.xinyong.module.qianbao.mapper.QianbaoMapper;
import cn.happain.module.xinyong.module.qianbao.service.IQianbaoService;
import cn.happain.module.xinyong.module.qianbao.domain.QianbaoModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.happain.common.utils.HappainGlobalUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.module.sys.module.user.service.impl.UserServiceImpl;
import cn.happain.module.sys.module.user.domain.UserModel;
import cn.happain.module.sys.module.user.domain.vo.UserVo;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class QianbaoServiceImpl extends ServiceImpl<QianbaoMapper, QianbaoModel> implements IQianbaoService {

    
    @Resource
    UserServiceImpl userService;
    @Resource
    JiaoyiServiceImpl jiaoyiService;
    
    
    

     public List<QianbaoVo> fillVo(List<QianbaoVo> qianbaoVo) {
        for (QianbaoVo vo : qianbaoVo) {
            fillVoOne(vo);
        }
        return qianbaoVo;
    }

     public QianbaoVo fillVoOne(QianbaoVo vo)  {
        if(vo.getUserId()!=null) {
            UserModel userModel = userService.getById(vo.getUserId());
            if(userModel!=null) {
                vo.setUserIdVo(BeanUtil.copyProperties(userModel, UserVo.class)); 
            }
        }
        return vo;
     }


     /**
      * 分页查询
      *
      * @param qianbaoDto dto
      * @return {@link Response}<{@link PageVo}<{@link QianbaoVo}>>
      */
     public Response<PageVo<QianbaoVo>> listPage(QianbaoDto qianbaoDto) {
         LambdaQueryWrapper<QianbaoModel> lambda = search(qianbaoDto);
         Page<QianbaoModel> qianbaoModelPage =  qianbaoDto.buildPageVo(QianbaoModel.class);
         Page<QianbaoModel> page = page(qianbaoModelPage, lambda);
         PageVo<QianbaoVo> qianbaoVoPageVo = PageVo.PageModelToVo(page,QianbaoVo.class);
         qianbaoVoPageVo.setList(fillVo(qianbaoVoPageVo.getList()));
         return Response.successDataDefault(qianbaoVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param qianbaoDto dto
       * @return {@link Response}<{@link List}<{@link QianbaoVo}>>
       */
      public Response<List<QianbaoVo>> listAll(QianbaoDto qianbaoDto) {
         LambdaQueryWrapper<QianbaoModel> lambda = search(qianbaoDto);
          List<QianbaoModel> list = list(lambda);
          List<QianbaoVo> qianbaoVos = BeanUtil.copyToList(list, QianbaoVo.class);
          return Response.successDataDefault(fillVo(qianbaoVos));
      }


     /**
      * 添加
      *
      * @param qianbaoDto dto
      * @return {@link Response}
      */
     public Response add(QianbaoDto qianbaoDto) {
         LambdaQueryWrapper<QianbaoModel> qianbaoModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
         qianbaoModelLambdaQueryWrapper.eq(QianbaoModel::getUserId,qianbaoDto.getUserId());
         List<QianbaoModel> list = list(qianbaoModelLambdaQueryWrapper);
         if(CollUtil.isNotEmpty(list)) {
             QianbaoModel qianbaoModel = list.get(0);
                qianbaoModel.setCoin(qianbaoModel.getCoin()+qianbaoDto.getCoin());
             updateById(qianbaoModel);
             // TODO: 扣除用户的金额
             UserModel userModel = userService.getById(qianbaoDto.getUserId());
             userModel.setMoney(userModel.getMoney()-qianbaoDto.getPrice());
            userService.updateById(userModel);
             return Response.successDefault();
         }else {
             QianbaoModel qianbaoModel = BeanUtil.copyProperties(qianbaoDto, QianbaoModel.class);
             save(qianbaoModel);
         }
        return Response.successDefault();
         
         
     }

     /**
      * 修改
      *
      * @param qianbaoDto dto
      * @return {@link Response}
      */
     public Response upd(QianbaoDto qianbaoDto) {
         QianbaoModel qianbaoModel = BeanUtil.copyProperties(qianbaoDto, QianbaoModel.class);
         
        
         updateById(qianbaoModel);
         

        return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param qianbaoDto dto
      * @return {@link Response}
      */
     public Response del(QianbaoDto qianbaoDto) {
         removeById(qianbaoDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param qianbaoDto dto
      * @return {@link Response}
      */
     public Response<QianbaoVo> detail(QianbaoDto qianbaoDto) {
         QianbaoModel model = getById(qianbaoDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         QianbaoVo qianbaoVo = BeanUtil.copyProperties(model, QianbaoVo.class);
         return Response.successDataDefault(fillVoOne(qianbaoVo));
     }

     /**
      * 有数据
      *
      * @param qianbaoDto dto
      * @return boolean
      */
     public boolean hasData(QianbaoDto qianbaoDto) {
         LambdaQueryWrapper<QianbaoModel> lambda = new QueryWrapper<QianbaoModel>().lambda();
        lambda.eq(QianbaoModel::getUserId,qianbaoDto.getUserId());
         if(qianbaoDto.getId()!=null) {
             lambda.ne(QianbaoModel::getId,qianbaoDto.getId());
         }
         if (lambda.getExpression().getNormal().isEmpty()) {
             return false;
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param qianbaoDto dto
     * @return {@link LambdaQueryWrapper}<{@link QianbaoModel}>
     */
     public LambdaQueryWrapper<QianbaoModel> search(QianbaoDto qianbaoDto) {
         LambdaQueryWrapper<QianbaoModel> lambda = new LambdaQueryWrapper<QianbaoModel>();
        if(qianbaoDto.getId() != null) {
            lambda.eq(QianbaoModel::getId,qianbaoDto.getId());
        }
        if(qianbaoDto.getUserId() != null) {
            lambda.eq(QianbaoModel::getUserId,qianbaoDto.getUserId());
        }
        if(qianbaoDto.getCoin() != null) {
            lambda.eq(QianbaoModel::getCoin,qianbaoDto.getCoin());
        }
        lambda.orderByDesc(QianbaoModel::getCreateTime);
         return lambda;
      }

    public Response sell(QianbaoDto qianbaoDto) {
        LambdaQueryWrapper<QianbaoModel> qianbaoModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        qianbaoModelLambdaQueryWrapper.eq(QianbaoModel::getUserId, qianbaoDto.getUserId());
        List<QianbaoModel> list = list(qianbaoModelLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            QianbaoModel qianbaoModel = list.get(0);
            qianbaoModel.setCoin(qianbaoModel.getCoin() - qianbaoDto.getCoin());
            updateById(qianbaoModel);
            // TODO: 增加用户的钱
            UserModel userModel = userService.getById(qianbaoDto.getUserId());
            userModel.setMoney(userModel.getMoney() + qianbaoDto.getPrice());
            userService.updateById(userModel);
            JiaoyiDto jiaoyiDto1 = new JiaoyiDto();
            jiaoyiDto1.setNum(qianbaoDto.getCoin());
            jiaoyiDto1.setTypes("sell");
            jiaoyiDto1.setPrice(qianbaoDto.getPrice());
            jiaoyiDto1.setUserId(qianbaoDto.getUserId());
            jiaoyiService.add(jiaoyiDto1);
            return Response.successDefault();
        } else {
            return Response.failDefault("没有钱包数据");
        }


    }

    public Response buy(QianbaoDto qianbaoDto) {
        LambdaQueryWrapper<QianbaoModel> qianbaoModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        qianbaoModelLambdaQueryWrapper.eq(QianbaoModel::getUserId,qianbaoDto.getUserId());
        List<QianbaoModel> list = list(qianbaoModelLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(list)) {
            QianbaoModel qianbaoModel = list.get(0);
            qianbaoModel.setCoin(qianbaoModel.getCoin()+qianbaoDto.getCoin());
            updateById(qianbaoModel);
            // TODO: 扣除用户的金额
            UserModel userModel = userService.getById(qianbaoDto.getUserId());
            userModel.setMoney(userModel.getMoney()-qianbaoDto.getPrice());
            userService.updateById(userModel);
        }else {
            QianbaoModel qianbaoModel = BeanUtil.copyProperties(qianbaoDto, QianbaoModel.class);
            save(qianbaoModel);
        }

        // TODO: 将交易的数据存入区块链当中
        JiaoyiDto jiaoyiDto1 = new JiaoyiDto();
        jiaoyiDto1.setNum(qianbaoDto.getCoin());
        jiaoyiDto1.setTypes("buy");
        jiaoyiDto1.setPrice(qianbaoDto.getPrice());
        jiaoyiDto1.setUserId(qianbaoDto.getUserId());
        jiaoyiService.add(jiaoyiDto1);

        return Response.successDefault();

    }
}
