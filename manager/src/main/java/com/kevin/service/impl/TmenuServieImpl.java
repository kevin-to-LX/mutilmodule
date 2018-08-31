package com.kevin.service.impl;

import com.kevin.entity.Tmenu;
import com.kevin.mapper.TmenuMapper;
import com.kevin.service.TmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author kevin
 * @version 1.0, 2018/7/10
 * @description
 */
@Service("tmenuService")
public class TmenuServieImpl extends BaseService<Tmenu> implements TmenuService {
    @Autowired
    private TmenuMapper tmenuMapper;

    @Override
    public List<Tmenu> selectMenusByRoleId(Integer roleid) {
        return tmenuMapper.selectMenusByRoleId(roleid);
    }

    @Override
    public List<Tmenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap) {
        return tmenuMapper.selectByParentIdAndRoleId(paraMap);
    }
}
