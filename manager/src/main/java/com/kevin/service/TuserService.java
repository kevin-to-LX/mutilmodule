package com.kevin.service;

import com.kevin.entity.Tdevice;
import com.kevin.entity.Tuser;

import java.util.List;

/**
 * @author kevin
 *
 */
public interface TuserService  extends IService<Tuser>{
    /**
     *
     * @param did
     * @return
     */
    List<Tuser> selectUsersByDeviceId(Integer did);

    /**
     *
     * @param deviceList
     */
    void addUserSet(List<Tdevice> deviceList);

}