package com.dlf.web.controller;

import com.dlf.web.anno.UrlPermissionIgnoreAnno;
import com.dlf.web.controller.restTemplate.RestTemplateForRouter;
import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserReqDTO;
import com.dlf.web.utils.ImgCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/7.
 */
@RestController
public class CommController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RestTemplateForRouter restTemplateForRouter;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 通用接口
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/other",method = RequestMethod.POST)
    public GlobalResultDTO entrance(@RequestBody BaseReqDTO<Object> reqDTO){
        return restTemplateForRouter.postForJsonObject(reqDTO);
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/comm/upload",method = RequestMethod.POST)
    public GlobalResultDTO upload(@RequestParam("file")MultipartFile file){
        return GlobalResultDTO.SUCCESS();
    }
    /**
     * 消息发送
     * @param reqDTO
     * @return
     */
    @UrlPermissionIgnoreAnno
    @RequestMapping(value = "/comm/msgSend", method = RequestMethod.POST)
    public GlobalResultDTO msgSend(@RequestBody BaseReqDTO<Object> reqDTO){

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 0);
        Date thisDate = new Date();
        if(thisDate.after(cal.getTime())){

        }
//
//  return messagePushService.messagePush(msgReqDTO, RedisPrefixEnums.REGISTER_MESSAGE_PRE.getCode());
        return restTemplateForRouter.postForJsonObject(reqDTO);
    }

    /**
     * 获取图片验证码
     * @param username
     */
    @RequestMapping(value = "/getImgCode")
    public void getImgCode(@RequestParam("username")String username, HttpServletResponse response) throws IOException {
        BaseReqDTO<UserReqDTO> reqDTO = new BaseReqDTO<UserReqDTO>();
        reqDTO.setAction("/comm/getImgCode");
        reqDTO.setDeviceType("Web");
        reqDTO.setRequestTime(simpleDateFormat.format(new Date()));
        reqDTO.setVersion("1");
        reqDTO.setType(1);
        UserReqDTO userReqDTO = new UserReqDTO();
        userReqDTO.setUsername(username);
        reqDTO.setData(userReqDTO);
        GlobalResultDTO<String> resultDTO = restTemplateForRouter.postForJsonObject(reqDTO);
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        ImgCodeUtils imgCodeUtils = new ImgCodeUtils();
        imgCodeUtils.getRandCodeImg(response, resultDTO.getData());
    }

    /**
     * 查询用户状态
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/user/queryUserByUsername", method = RequestMethod.POST)
    public GlobalResultDTO queryUserByUsername(@RequestBody BaseReqDTO<Object> reqDTO){
        return restTemplateForRouter.postForJsonObject(reqDTO);
    }

    /**
     * 修改登录密码
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/user/pwdReset", method = RequestMethod.POST)
    public GlobalResultDTO pwdReset(@RequestBody BaseReqDTO<Object> reqDTO){
        return restTemplateForRouter.postForJsonObject(reqDTO);
    }

}
