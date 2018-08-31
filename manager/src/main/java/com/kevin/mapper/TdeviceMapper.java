package com.kevin.mapper;

import com.kevin.entity.Tdevice;
import com.kevin.util.MyMapper;

import java.util.List;

public interface TdeviceMapper extends MyMapper<Tdevice> {
    List<Tdevice> selectDevicesByUserId(Integer userid);

    Integer getDeviceId(String deviceName);
}