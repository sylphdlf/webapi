package com.dlf.web.controller;

import com.dlf.web.dto.BaseReqDTO;
import com.dlf.web.dto.GlobalResultDTO;
import com.dlf.web.controller.restTemplate.RestTemplateForRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    RestTemplateForRouter restTemplateForRouter;

    @RequestMapping(value = "/getKeyValue",method = RequestMethod.POST)
    public Object getKeyValue(@RequestBody BaseReqDTO<Object> reqDTO){
        return restTemplateForRouter.postForJsonObject(reqDTO);
    }
}
