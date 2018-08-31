package com.kevin.mapper;

import com.kevin.entity.Tmenu;
import com.kevin.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TmenuMapper extends MyMapper<Tmenu> {

    List<Tmenu> selectMenusByRoleId(Integer roleid);

    List<Tmenu> selectByParentIdAndRoleId(HashMap<String,Object> paraMap);

}