package com.dlf.web.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.utils.Md5Utils;
import com.dlf.web.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/5/7.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    RestTemplate restTemplate;

    @Value("${router.url}")
    private String routerUrl;

    private static final String SAVE_FILE = "/file/save";
    private static final String ROLLBACK_FILE = "/file/save";


    @RequestMapping(value = "/upload")
    public GlobalResultDTO upload(MultipartFile file, Long orderId) throws NoSuchAlgorithmException, IOException {
        JSONObject jsonObject = new JSONObject();
        //MD5加密
        String md5 = Md5Utils.md5Encoding(file);
        //检查重复文件, 有则建立关联，无则保存数据且返回文件存储路径
        jsonObject.put("orderId", orderId);
        jsonObject.put("md5", md5);
        jsonObject.put("size", file.getSize());
        jsonObject.put("suffix", StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
        HttpEntity entity = new HttpEntity<>(jsonObject, WebUtils.getHeaders());
        GlobalResultDTO resultDTO = restTemplate
                .exchange(routerUrl + SAVE_FILE, HttpMethod.POST, entity, GlobalResultDTO.class).getBody();
        String filePath = "";
        if(null != resultDTO && resultDTO.isSuccess()){
            return resultDTO;//已经有重复文件，无需再次保存
        }else if(null != resultDTO){
            filePath = resultDTO.getMsg();//获取文件存储路径
        }else{
            return GlobalResultDTO.FAIL();
        }
        //保存文件
        try {
            file.transferTo(new File(filePath));
        }catch (IOException e){
        //数据回滚
            for(int i = 1;i <= 3; i++){
                GlobalResultDTO rollback = restTemplate
                        .exchange(routerUrl + ROLLBACK_FILE, HttpMethod.POST, entity, GlobalResultDTO.class).getBody();
                if(rollback != null && rollback.isSuccess()){
                    break;
                }
            }
        }
        return GlobalResultDTO.SUCCESS();
    }
}
