package com.dlf.web.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.dlf.web.dto.FileResDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import com.dlf.web.utils.Md5Utils;
import com.dlf.web.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

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
    private static final String SAVE_FILE_FROM_ORDER = "/file/saveFromOd";
    private static final String ROLLBACK_FILE = "/file/rollbackFromOd";

    @RequestMapping(value = "/upload")
    public GlobalResultDTO upload(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        JSONObject reqObj = new JSONObject();
        reqObj.put("fileName", StringUtils.substringBeforeLast(file.getOriginalFilename(), "."));
        HttpEntity entity = this.setDefaultParams(file, reqObj);
        ResponseEntity<GlobalResultDTO<FileResDTO>> responseEntity = restTemplate.exchange(routerUrl + SAVE_FILE, HttpMethod.POST, entity,
                new ParameterizedTypeReference<GlobalResultDTO<FileResDTO>>() {});
        GlobalResultDTO<FileResDTO> resultDTO = responseEntity.getBody();
        if(null != resultDTO && resultDTO.isSuccess() && StringUtils.isNotBlank(resultDTO.getData() + "")){
            return this.saveFile(file, entity, resultDTO.getData());
        }else if(null != resultDTO && resultDTO.isSuccess()){
            return resultDTO;
        }else {
            return GlobalResultDTO.FAIL();
        }
    }

    @RequestMapping(value = "/uploadFromOd")
    public GlobalResultDTO uploadFromOd(MultipartFile file, Long orderId) throws NoSuchAlgorithmException, IOException {
        JSONObject reqObj = new JSONObject();
        reqObj.put("orderId", orderId);
        HttpEntity entity = this.setDefaultParams(file, reqObj);
        ResponseEntity<GlobalResultDTO<FileResDTO>> responseEntity = restTemplate.exchange(routerUrl + SAVE_FILE_FROM_ORDER, HttpMethod.POST, entity,
                new ParameterizedTypeReference<GlobalResultDTO<FileResDTO>>() {});
        GlobalResultDTO<FileResDTO> resultDTO = responseEntity.getBody();
        if(null == resultDTO || resultDTO.isFail()){
            return GlobalResultDTO.FAIL();
        }
        //保存文件
        return this.saveFile(file, entity, resultDTO.getData());
    }

    private HttpEntity setDefaultParams(MultipartFile file, JSONObject jsonObject) throws IOException, NoSuchAlgorithmException {
        //MD5加密
        String md5 = Md5Utils.md5Encoding(file);
        //检查重复文件, 有则建立关联，无则保存数据且返回文件存储路径
        jsonObject.put("md5", md5);
        jsonObject.put("size", file.getSize());
        jsonObject.put("suffix", StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
        return new HttpEntity<>(jsonObject, WebUtils.getHeaders());
    }

    private GlobalResultDTO saveFile(MultipartFile file, HttpEntity entity, FileResDTO resDTO){
        try {
            File fileSave = new File(resDTO.getPath());
            if(!fileSave.exists()){
                boolean mkdirs = fileSave.mkdirs();
            }
            file.transferTo(new File(resDTO.getPath() + File.separator + resDTO.getName()));
            return GlobalResultDTO.SUCCESS();
        } catch (IOException e) {
            this.rollback(entity);
        }
        return GlobalResultDTO.FAIL();
    }

    private void rollback(HttpEntity entity){
        //数据回滚
        for(int i = 1;i <= 3; i++){
            GlobalResultDTO rollback = restTemplate
                    .exchange(routerUrl + ROLLBACK_FILE, HttpMethod.POST, entity, GlobalResultDTO.class).getBody();
            if(rollback != null && rollback.isSuccess()){
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }
}
