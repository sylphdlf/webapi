package com.dlf.web.config.shiro;

import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.enums.UserResultEnums;
import com.dlf.web.utils.Md5Utils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/12/11.
 * 自定义权限匹配和账号密码匹配
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Value("${router.url}")
    private String routerUrl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
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

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 验证用户输入的账号和密码是否正确
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(token instanceof WxLoginToken){
            return wxLogin(token);
        }else if(token instanceof UsernamePasswordToken){
            return usernamePasswordLogin(token);
        }
        return null;
    }

    private AuthenticationInfo wxLogin(AuthenticationToken token){
        WxLoginToken loginToken = (WxLoginToken)token;
        SimpleAuthenticationInfo authenticationInfo = null;
        try {
            authenticationInfo = new SimpleAuthenticationInfo(loginToken.getUserInfo(), Md5Utils.md5Encoding(loginToken.getPrincipal().toString()), getName());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return authenticationInfo;
    }

    private AuthenticationInfo usernamePasswordLogin(AuthenticationToken token){
        //获取用户的输入的账号
        UsernamePasswordToken loginToken = (UsernamePasswordToken)token;
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(loginToken.getUsername());
        GlobalResultDTO<UserInfo> resultDTO = this.postForJsonObject(userInfo, routerUrl + "/user/getUserByUsername");
        if(null == resultDTO || !resultDTO.isSuccess()){
            throw new AuthenticationException(UserResultEnums.LOGIN_FAIL.getMsg());
        }
        userInfo = resultDTO.getData();
        return new SimpleAuthenticationInfo(userInfo, resultDTO.getData().getPassword(), getName());
    }

    private GlobalResultDTO<UserInfo> postForJsonObject(UserInfo reqDTO, String url){
        ResponseEntity<GlobalResultDTO<UserInfo>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, getHeader(reqDTO),
                new ParameterizedTypeReference<GlobalResultDTO<UserInfo>>() {});
        return responseEntity.getBody();
    }

    private static HttpEntity getHeader(Object reqDTO){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        return new HttpEntity<>(reqDTO,headers);
    }
}