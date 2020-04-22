package com.dlf.web.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.enums.UserResultEnums;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

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
        UsernamePasswordToken loginToken = (UsernamePasswordToken)token;
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(loginToken.getUsername());
        GlobalResultDTO<UserInfo> resultDTO = restTemplate.postForObject("http://ROUTER/service/user/getUserByUsername", userInfo, GlobalResultDTO.class);
        if(null == resultDTO || !resultDTO.isSuccess()){
            throw new AuthenticationException(UserResultEnums.LOGIN_FAIL.getMsg());
        }
        return new SimpleAuthenticationInfo(userInfo, resultDTO.getData().getPassword(), getName());
    }
}