package com.dlf.web.config.shiro;

import com.dlf.web.controller.restTemplate.RestTemplateForRouter;
import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserReqDTO;
import com.dlf.web.dto.UserVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/12/11.
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    private static final String QUERY_USERNAME = "/user/queryUserByUsername";
    private static final String LOGIN_WITH_CODE = "/login/loginWithCode";
    private static final String LOGIN = "/login/login";

    @Autowired
    RestTemplateForRouter restTemplateForRouter;




    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//        UserResDTO resDTO = (UserResDTO) principals.getPrimaryPrincipal();
//        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(resDTO.getId()),SecurityUtils.getSubject().getPrincipals());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //查询角色列表
//        List<RoleDTO> roleList = userService.getRoleListByUser(resDTO.getId());
//        if(!CollectionUtils.isEmpty(roleList)){
//            for(RoleDTO thisDTO : roleList){
//                info.addRole(thisDTO.getName());
//            }
//        }
        //查询资源列表
//        List<FunDTO> funList = userService.getFunListByUser(resDTO.getId());
//        if(!CollectionUtils.isEmpty(funList)){
//            for (FunDTO thisDTO : funList) {
//                if(StringUtils.isBlank(thisDTO.getPath())){
//                    continue;
//                }
//                info.addStringPermission(thisDTO.getPath());
//            }
//        }
        return info;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号
        BaseReqDTO<UserReqDTO> baseReqDTO;
        if(token instanceof MobileVerifyCodeToken){
            MobileVerifyCodeToken mobileToken = (MobileVerifyCodeToken)token;
            baseReqDTO = new BaseReqDTO<UserReqDTO>();
            baseReqDTO.setAction(LOGIN_WITH_CODE);
            UserReqDTO reqDTO = new UserReqDTO();
            reqDTO.setUsername(mobileToken.getUsername());
            reqDTO.setVerifyCode(mobileToken.getVerifyCode());
            baseReqDTO.setData(reqDTO);
            GlobalResultDTO<UserVO> resultDTO = this.restPost(baseReqDTO);
            if(!resultDTO.isSuccess()){
                throw new AuthenticationException(resultDTO.getMsg());
            }
            UserVO userVO = resultDTO.getData();
            return new SimpleAuthenticationInfo(userVO, "ok", getName());
        }else if (token instanceof RegisterAndLoginToken){
            RegisterAndLoginToken registerAndLoginToken = (RegisterAndLoginToken)token;
            UserVO userVO = new UserVO();
            userVO.setId(registerAndLoginToken.getUserId());
            userVO.setType(registerAndLoginToken.getType());
            return new SimpleAuthenticationInfo(userVO, "ok", getName());
        } else {
            UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
            baseReqDTO = new BaseReqDTO<UserReqDTO>();
            baseReqDTO.setAction(LOGIN);
            UserReqDTO reqDTO = new UserReqDTO();
            reqDTO.setUsername(usernamePasswordToken.getUsername());
            reqDTO.setPassword(String.valueOf(usernamePasswordToken.getPassword()));
            baseReqDTO.setData(reqDTO);
            GlobalResultDTO<UserVO> resultDTO = this.restPost(baseReqDTO);
            if(!resultDTO.isSuccess()){
                throw new AuthenticationException(resultDTO.getMsg());
            }
            UserVO userVO = resultDTO.getData();
            return new SimpleAuthenticationInfo(userVO, usernamePasswordToken.getPassword() , getName());
        }
    }

    private GlobalResultDTO<UserVO> restPost(BaseReqDTO reqDTO){
        try {
            ResponseEntity<GlobalResultDTO<UserVO>> responseEntity =
                    restTemplate.exchange(RestTemplateForRouter.ROUTER_SERVICE, HttpMethod.POST, RestTemplateForRouter.getHeader(reqDTO),
                            new ParameterizedTypeReference<GlobalResultDTO<UserVO>>() {});
            return responseEntity.getBody();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }
    }
}