package cn.happain.module.xinyong.module.jiaoyi.service.impl;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.xinyong.module.jiaoyi.domain.dto.JiaoyiDto;
import cn.happain.module.xinyong.module.jiaoyi.domain.vo.JiaoyiVo;
import cn.happain.module.xinyong.module.jiaoyi.mapper.JiaoyiMapper;
import cn.happain.module.xinyong.module.jiaoyi.service.IJiaoyiService;
import cn.happain.module.xinyong.module.jiaoyi.domain.JiaoyiModel;
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
import cn.happain.common.modules.web3j.service.Web3jService;
import cn.happain.module.xinyong.module.jiaoyiLink.service.impl.JiaoyiLinkServiceImpl;
import cn.happain.module.xinyong.blockBase.JiaoyiBlockBase;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.JiaoyiLinkModel;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.dto.JiaoyiLinkDto;
import cn.happain.common.modules.fisco.contract.JiaoyiContract;
import cn.happain.common.domain.vo.BlockVo;
import cn.happain.module.sys.module.user.service.impl.UserServiceImpl;
import cn.happain.module.sys.module.user.domain.UserModel;
import cn.happain.module.sys.module.user.domain.vo.UserVo;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class JiaoyiServiceImpl extends ServiceImpl<JiaoyiMapper, JiaoyiModel> implements IJiaoyiService {
    String encryptKey = "";
    
    
    @Resource
    UserServiceImpl userService;
    
    
    @Resource
    Web3jService web3jService;
    @Resource
    JiaoyiLinkServiceImpl jiaoyiLinkService;
    @Resource
    JiaoyiContract jiaoyiContract;
    
    

     public List<JiaoyiVo> fillVo(List<JiaoyiVo> jiaoyiVo) {
        for (JiaoyiVo vo : jiaoyiVo) {
            fillVoOne(vo);
        }
        return jiaoyiVo;
    }

     public JiaoyiVo fillVoOne(JiaoyiVo vo)  {
        if(vo.getUserId()!=null) {
            UserModel userModel = userService.getById(vo.getUserId());
            if(userModel!=null) {
                vo.setUserIdVo(BeanUtil.copyProperties(userModel, UserVo.class)); 
            }
        }
        if(StrUtil.isNotEmpty(vo.getHash())) {
            String jiaoyiBlockBaseStr = web3jService.getContractTransactionByBlockHash(vo.getHash(),encryptKey);
            JiaoyiBlockBase jiaoyiBlockBase = HappainGlobalUtil.jsonStrToBean(jiaoyiBlockBaseStr, JiaoyiBlockBase.class);
            vo.setJiaoyiBlockBase(jiaoyiBlockBase);
        }
        
        
        LambdaQueryWrapper<JiaoyiLinkModel> jiaoyiLinkModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
         jiaoyiLinkModelLambdaQueryWrapper.eq(JiaoyiLinkModel::getJiaoyiId,vo.getId());
         jiaoyiLinkModelLambdaQueryWrapper.orderByDesc(JiaoyiLinkModel::getCreateTime);
         List<JiaoyiLinkModel> jiaoyiLinkModelList = jiaoyiLinkService.list(jiaoyiLinkModelLambdaQueryWrapper);
         if(CollUtil.isNotEmpty(jiaoyiLinkModelList)) {
             ArrayList<JiaoyiBlockBase> jiaoyiBlockBaseList = new ArrayList<>();
             for (JiaoyiLinkModel jiaoyiLinkModel : jiaoyiLinkModelList) {
                 String jiaoyiBlockBaseStr = web3jService.getContractTransactionByBlockHash(jiaoyiLinkModel.getHash(),encryptKey);
                 JiaoyiBlockBase jiaoyiBlockBase = HappainGlobalUtil.jsonStrToBean(jiaoyiBlockBaseStr, JiaoyiBlockBase.class);
                 jiaoyiBlockBase.setHash(jiaoyiLinkModel.getHash());
                 jiaoyiBlockBase.setTx(jiaoyiLinkModel.getTx());
                 jiaoyiBlockBase.setCreateTime(jiaoyiLinkModel.getCreateTime());
                 jiaoyiBlockBaseList.add(jiaoyiBlockBase);
             }
             vo.setJiaoyiBlockBaseList(jiaoyiBlockBaseList);
         }
        return vo;
     }


     /**
      * 分页查询
      *
      * @param jiaoyiDto dto
      * @return {@link Response}<{@link PageVo}<{@link JiaoyiVo}>>
      */
     public Response<PageVo<JiaoyiVo>> listPage(JiaoyiDto jiaoyiDto) {
         LambdaQueryWrapper<JiaoyiModel> lambda = search(jiaoyiDto);
         Page<JiaoyiModel> jiaoyiModelPage =  jiaoyiDto.buildPageVo(JiaoyiModel.class);
         Page<JiaoyiModel> page = page(jiaoyiModelPage, lambda);
         PageVo<JiaoyiVo> jiaoyiVoPageVo = PageVo.PageModelToVo(page,JiaoyiVo.class);
         jiaoyiVoPageVo.setList(fillVo(jiaoyiVoPageVo.getList()));
         return Response.successDataDefault(jiaoyiVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param jiaoyiDto dto
       * @return {@link Response}<{@link List}<{@link JiaoyiVo}>>
       */
      public Response<List<JiaoyiVo>> listAll(JiaoyiDto jiaoyiDto) {
         LambdaQueryWrapper<JiaoyiModel> lambda = search(jiaoyiDto);
          List<JiaoyiModel> list = list(lambda);
          List<JiaoyiVo> jiaoyiVos = BeanUtil.copyToList(list, JiaoyiVo.class);
          return Response.successDataDefault(fillVo(jiaoyiVos));
      }


     /**
      * 添加
      *
      * @param jiaoyiDto dto
      * @return {@link Response}
      */
     public Response<BlockVo> add(JiaoyiDto jiaoyiDto) {

         JiaoyiModel jiaoyiModel = BeanUtil.copyProperties(jiaoyiDto, JiaoyiModel.class);
         
        JiaoyiBlockBase jiaoyiBlockBase = BeanUtil.copyProperties(jiaoyiDto, JiaoyiBlockBase.class);
        
        BlockVo blockVo = web3jService.contractTransaction(HappainGlobalUtil.beanToJsonStr(jiaoyiBlockBase),jiaoyiContract, encryptKey);
           
    
        if(blockVo!=null) {
            jiaoyiModel.setTx(blockVo.getTx());
            jiaoyiModel.setHash(blockVo.getHash());
         }
        
         save(jiaoyiModel);
         
         JiaoyiLinkDto jiaoyiLinkDto = new JiaoyiLinkDto();
         jiaoyiLinkDto.setJiaoyiId(jiaoyiModel.getId());
         jiaoyiLinkDto.setTx(jiaoyiModel.getTx());
         jiaoyiLinkDto.setHash(jiaoyiModel.getHash());
         jiaoyiLinkService.add(jiaoyiLinkDto);
        return Response.successDataDefault(blockVo);
         
         
     }

     /**
      * 修改
      *
      * @param jiaoyiDto dto
      * @return {@link Response}
      */
     public Response<BlockVo> upd(JiaoyiDto jiaoyiDto) {
         JiaoyiModel jiaoyiModel = BeanUtil.copyProperties(jiaoyiDto, JiaoyiModel.class);
         
        JiaoyiBlockBase jiaoyiBlockBase = BeanUtil.copyProperties(jiaoyiDto, JiaoyiBlockBase.class);
        
        BlockVo blockVo = web3jService.contractTransaction(HappainGlobalUtil.beanToJsonStr(jiaoyiBlockBase),jiaoyiContract, encryptKey);
           
    
        if(blockVo!=null) {
            jiaoyiModel.setTx(blockVo.getTx());
            jiaoyiModel.setHash(blockVo.getHash());
         }
        
         updateById(jiaoyiModel);
         
        JiaoyiLinkDto jiaoyiLinkDto = new JiaoyiLinkDto();
         jiaoyiLinkDto.setJiaoyiId(jiaoyiModel.getId());
         jiaoyiLinkDto.setTx(jiaoyiModel.getTx());
         jiaoyiLinkDto.setHash(jiaoyiModel.getHash());
         jiaoyiLinkService.add(jiaoyiLinkDto);

        return Response.successDataDefault(blockVo);
     }

     /**
      * 删除
      *
      * @param jiaoyiDto dto
      * @return {@link Response}
      */
     public Response del(JiaoyiDto jiaoyiDto) {
         removeById(jiaoyiDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param jiaoyiDto dto
      * @return {@link Response}
      */
     public Response<JiaoyiVo> detail(JiaoyiDto jiaoyiDto) {
         JiaoyiModel model = getById(jiaoyiDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         JiaoyiVo jiaoyiVo = BeanUtil.copyProperties(model, JiaoyiVo.class);
         return Response.successDataDefault(fillVoOne(jiaoyiVo));
     }

     /**
      * 有数据
      *
      * @param jiaoyiDto dto
      * @return boolean
      */
     public boolean hasData(JiaoyiDto jiaoyiDto) {
         LambdaQueryWrapper<JiaoyiModel> lambda = new QueryWrapper<JiaoyiModel>().lambda();
        lambda.eq(JiaoyiModel::getUserId,jiaoyiDto.getUserId());
         if(jiaoyiDto.getId()!=null) {
             lambda.ne(JiaoyiModel::getId,jiaoyiDto.getId());
         }
         if (lambda.getExpression().getNormal().isEmpty()) {
             return false;
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param jiaoyiDto dto
     * @return {@link LambdaQueryWrapper}<{@link JiaoyiModel}>
     */
     public LambdaQueryWrapper<JiaoyiModel> search(JiaoyiDto jiaoyiDto) {
         LambdaQueryWrapper<JiaoyiModel> lambda = new LambdaQueryWrapper<JiaoyiModel>();
        if(jiaoyiDto.getId() != null) {
            lambda.eq(JiaoyiModel::getId,jiaoyiDto.getId());
        }
        if(StrUtil.isNotEmpty(jiaoyiDto.getHash())){
            lambda.like(JiaoyiModel::getHash,jiaoyiDto.getHash());
        }
        if(StrUtil.isNotEmpty(jiaoyiDto.getTx())){
            lambda.like(JiaoyiModel::getTx,jiaoyiDto.getTx());
        }
        if(jiaoyiDto.getUserId() != null) {
            lambda.eq(JiaoyiModel::getUserId,jiaoyiDto.getUserId());
        }
        if(jiaoyiDto.getNum() != null) {
            lambda.eq(JiaoyiModel::getNum,jiaoyiDto.getNum());
        }
        if(StrUtil.isNotEmpty(jiaoyiDto.getTypes())){
            lambda.like(JiaoyiModel::getTypes,jiaoyiDto.getTypes());
        }
        if(jiaoyiDto.getPrice() != null) {
            lambda.eq(JiaoyiModel::getPrice,jiaoyiDto.getPrice());
        }
        lambda.orderByDesc(JiaoyiModel::getCreateTime);
         return lambda;
      }
}
