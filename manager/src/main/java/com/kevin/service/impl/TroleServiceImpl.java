package com.kevin.service.impl;

import com.kevin.entity.Trole;
import com.kevin.mapper.TroleMapper;
import com.kevin.service.TroleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevin
 * @version 1.0, 2018/7/10
 * @description
 */

@Service("troleService")
public class TroleServiceImpl   extends BaseService<Trole> implements TroleService {
    @Autowired
    private TroleMapper troleMapper;

    @Override
    public List<Trole> selectRolesByUserId(Integer userid) {
        return troleMapper.selectRolesByUserId(userid);
    }
}
