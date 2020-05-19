package com.dlf.web.controller.file;

import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.dto.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;

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


    @RequestMapping(value = "/upload")
    public GlobalResultDTO upload(MultipartFile file, String orderId) throws IOException {

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("orderId", orderId);
        multiValueMap.add("file", file.getInputStream());

        Subject subject = SecurityUtils.getSubject();
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("userId", ((UserInfo)subject.getPrincipal()).getId() + "");
        headers.set("username", ((UserInfo)subject.getPrincipal()).getUsername());
        HttpEntity entity = new HttpEntity<>(multiValueMap,headers);
        return restTemplate.postForObject(routerUrl + "/file/upload", entity, GlobalResultDTO.class);
//        ResponseEntity<GlobalResultDTO> responseEntity = restTemplate.exchange(routerUrl + "/file/upload", HttpMethod.POST, entity, GlobalResultDTO.class);
//        return GlobalResultDTO.SUCCESS();
    }
}
