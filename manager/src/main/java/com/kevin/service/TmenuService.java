package com.kevin.service;

import com.kevin.entity.Tmenu;

import java.util.HashMap;
import java.util.List;

public interface TmenuService extends IService<Tmenu>{

    /**
     * 根据用户角色来获取对应的菜单信息
     * @param roleid
     * @return
     */
    List<Tmenu> selectMenusByRoleId(Integer roleid);

    /**
     *
     * @param paraMap
     * @return
     */
    List<Tmenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap);


}