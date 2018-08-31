package com.kevin.mapper;

import com.kevin.entity.Tuser;
import com.kevin.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TuserMapper extends MyMapper<Tuser> {
    List<Tuser> selectUsersByDeviceId(Integer did);
}