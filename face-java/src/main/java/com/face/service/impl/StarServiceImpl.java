package com.face.service.impl;

import com.face.dao.IStarDao;
import com.face.page.Page;
import com.face.po.StarPo;
import com.face.service.IStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class StarServiceImpl implements IStarService {
    @Resource
    private IStarDao starDao;

    @Override
    public void insert(StarPo starPo) {
        starDao.insert(starPo);
    }

    @Override
    public List<StarPo> list(StarPo starPo) {
        return null;
    }

    @Override
    public void update(StarPo starPo) {

    }

    public Page qryStarByPage(Page page, StarPo starPo) {
        log.error("");
        page.setRows(starDao.qryStarByPage(starPo));
        return page;
    }
}
