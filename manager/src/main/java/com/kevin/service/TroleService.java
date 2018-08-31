package com.kevin.service;

import com.kevin.entity.Trole;

import java.util.List;

public interface TroleService extends IService<Trole>{


    /**
     * 查询用户所有的角色
     * @param userid
     * @return
     */
    List<Trole> selectRolesByUserId(Integer userid);//根据userid查询所有的角色

}