package com.kevin.service;

import com.kevin.entity.Tdevice;

import java.util.List;

public interface TdeviceService extends IService<Tdevice>{
    /**
     *
     * @param userid
     * @return
     */
    List<Tdevice> selectDeviceByUserId(Integer userid);

    /**
     *
     * @param deviceName
     * @return
     */
    Integer getDeviceId(String deviceName);


}
