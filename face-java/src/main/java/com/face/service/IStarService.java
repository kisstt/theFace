package com.face.service;

import com.face.po.StarPo;

import java.util.List;

public interface IStarService {

    void insert(StarPo starPo);

    List<StarPo> list(StarPo starPo);

    void update(StarPo starPo);
}
