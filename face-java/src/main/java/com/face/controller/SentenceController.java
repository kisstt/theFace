package com.face.controller;

import com.face.po.PushSentencePo;
import com.face.service.impl.PushSentenceServiceImpl;
import com.face.vo.UserSessionInfo;
import com.face.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/sentence")
public class SentenceController {
    @Resource
    private PushSentenceServiceImpl pushSentenceService;

    /**
     * 根据tagId获得随机的推送句子
     *
     * @param pushSentencePo
     * @return
     */
    @PostMapping("/pushSentence")
    @ResponseBody
    public PushSentencePo pushSentence(@RequestBody PushSentencePo pushSentencePo) {
        return pushSentenceService.pushSentence(pushSentencePo);
    }
}
