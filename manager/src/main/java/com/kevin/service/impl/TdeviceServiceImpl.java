package com.kevin.service.impl;

import com.kevin.entity.Tdevice;
import com.kevin.mapper.TdeviceMapper;
import com.kevin.service.TdeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creat by kevin on 2018/8/19
 */
@Service("tdeviceService")
public class TdeviceServiceImpl extends BaseService<Tdevice> implements TdeviceService {
    @Autowired
    private TdeviceMapper tdeviceMapper;

    @Override
    public List<Tdevice> selectDeviceByUserId(Integer userid) {
        return tdeviceMapper.selectDevicesByUserId(userid);
    }

    @Override
    public Integer getDeviceId(String deviceName) {
        return tdeviceMapper.getDeviceId(deviceName);
    }
}
