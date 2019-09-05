package com.face.controller;

import com.face.service.IFaceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
@RequestMapping("/face")
public class FaceController {

    @Resource
    private IFaceService faceService;

    @RequestMapping("/detect")
    @ResponseBody
    public void detect(){

    }


    @RequestMapping("/login")
    @ResponseBody
    public void login(@RequestBody MultipartFile faceImg){

    }

    @RequestMapping("/register")
    @ResponseBody
    public void register(){

    }
}
