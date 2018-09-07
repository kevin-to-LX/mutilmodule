package com.kevin.mapper;

import com.kevin.entity.TSysPermissionInit;
import com.kevin.util.MyMapper;

import java.util.List;

public interface TSysPermissionInitMapper extends MyMapper<TSysPermissionInit> {
    List<TSysPermissionInit> selectAll();
}