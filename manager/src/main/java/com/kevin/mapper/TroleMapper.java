package com.kevin.mapper;

import com.kevin.entity.Trole;
import com.kevin.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TroleMapper extends MyMapper<Trole> {

    List<Trole> selectRolesByUserId(Integer userid);

}