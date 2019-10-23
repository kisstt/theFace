package com.face.controller;

import com.face.page.Page;
import com.face.po.StarPo;
import com.face.service.impl.StarServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/star")
public class StarController {

    @Resource
    private StarServiceImpl starService;

    @RequestMapping("/starList")
    public Page starList(@RequestBody Page page) {
        return starService.qryStarByPage(page, new StarPo());
    }

}
