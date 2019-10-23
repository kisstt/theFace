package com.face.service.impl;

import com.face.dao.IPushSentenceDao;
import com.face.po.PushSentencePo;
import com.face.service.IPushSentenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PushSentenceServiceImpl implements IPushSentenceService {

    @Resource
    private IPushSentenceDao pushSentenceDao;

    @Override
    public PushSentencePo pushSentence(PushSentencePo pushSentencePo) {
        int count = pushSentenceDao.countByTagId(pushSentencePo);
        int random = (int) (Math.random() * count);
        return pushSentenceDao.getRandomSentenceOne(random,pushSentencePo);
    }
}
