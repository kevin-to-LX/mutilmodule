package com.kevin.service.impl;

import com.kevin.entity.Tdevice;
import com.kevin.entity.Tuser;
import com.kevin.mapper.TuserMapper;
import com.kevin.service.TuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kevin
 * @version 1.0, 2018/7/10
 * @description
 */
@Service("tuserService")
public class TuserServiceImpl   extends BaseService<Tuser> implements TuserService {

    @Autowired
    private TuserMapper tuserMapper;

    @Autowired(required = false)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public synchronized void  addUserSet(List<Tdevice> deviceList) {

       deviceList.forEach(d->{
           List<Tuser> userList = selectUsersByDeviceId(d.getId());
           StringBuffer sb = new StringBuffer();
           userList.forEach(u->{
               sb.append("," + u.getTrueName());
           });
           d.setUsers(sb.toString().replaceFirst(",", ""));
       });
    }

    @Override
    public   List<Tuser>  selectUsersByDeviceId(Integer did) {
        return tuserMapper.selectUsersByDeviceId(did);
    }
}
