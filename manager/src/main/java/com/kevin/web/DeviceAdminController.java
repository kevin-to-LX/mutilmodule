package com.kevin.web;

import com.github.pagehelper.PageHelper;
import com.kevin.entity.Tdevice;
import com.kevin.entity.Tuser;
import com.kevin.entity.Tuserdevice;
import com.kevin.service.*;
import com.kevin.entity.*;
import com.kevin.model.JqgridBean;
import com.kevin.model.PageRusult;
import com.kevin.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Creat by kevin on 2018/8/19
 */
@Controller
@RequestMapping("/admin/device")
@Api(value = "/admin/device",tags = "设备用户控制API")
public class DeviceAdminController {

    @Resource
    private TdeviceService deviceService;

    @Resource
    private TuserdeviceService userDeviceService;

    @Resource
    private TuserService userService;

    @Resource
    private TroleService roleService;

    @Resource
    private TuserroleService userRoleService;

    @Resource
    private TmenuService tmenuService;

    @Resource
    private TrolemenuService trolemenuService;

    @RequestMapping("/todevicemanage")
    @RequiresPermissions(value = {"设备管理"})
    public String todevicemanage() {
        return "power/device";
    }

    /**
     * 分页查询设备信息
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"设备管理"})
    @ApiOperation(value="分页查询设备")
    public Map<String, Object> list(JqgridBean jqgridbean
            /*String userName,@RequestParam(value="page",required=false)Integer page*/
    ) throws Exception {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();

        Example tdeviceExample = new Example(Tdevice.class);
        //tdeviceExample.or().andIdNotEqualTo(1L);
        Example.Criteria criteria = tdeviceExample.or();
        criteria.andNotEqualTo("deviceName", "admin");

        if (StringUtils.isNotEmpty(jqgridbean.getSearchField())) {
            if ("devicename".equalsIgnoreCase(jqgridbean.getSearchField())) {
                if ("eq".contentEquals(jqgridbean.getSearchOper())) {
                    criteria.andEqualTo("deviceName", jqgridbean.getSearchString());
                }
            }
        }

        if (StringUtils.isNotEmpty(jqgridbean.getSidx()) && StringUtils.isNotEmpty(jqgridbean.getSord())) {
            tdeviceExample.setOrderByClause(jqgridbean.getSidx() + " " + jqgridbean.getSord());
        }

        PageHelper.startPage(jqgridbean.getPage(), jqgridbean.getLength());
        List<Tdevice> deviceList = deviceService.selectByExample(tdeviceExample);
        PageRusult<Tdevice> pageRusult = new PageRusult<Tdevice>(deviceList);


       /* Integer totalrecords = deviceService.selectCountByExample(tdeviceExample);//总记录数
        Page pagebean = new Page(jqgridbean.getLength() * ((jqgridbean.getPage() > 0 ? jqgridbean.getPage() : 1) - 1), jqgridbean.getLength(), totalrecords);
        tdeviceExample.setPage(pagebean);
        tdeviceExample.setOrderByClause(jqgridbean.getSidx() + " " + jqgridbean.getSord());
        List<Tdevice> deviceList = deviceService.selectByExample(tdeviceExample);*/

        userService.addUserSet(deviceList);
        resultmap.put("currpage", String.valueOf(pageRusult.getPageNum()));
        resultmap.put("totalpages", String.valueOf(pageRusult.getPages()));
        resultmap.put("totalrecords", String.valueOf(pageRusult.getTotal()));
        resultmap.put("datamap", deviceList);

        return resultmap;
    }


    @ResponseBody
    @RequestMapping(value = "/addupdatedevice")
    @RequiresPermissions(value = {"设备管理"})
    @ApiOperation(value="设备管理")
    public Map<String, Object> addupdatedevice(Tdevice tdevice, HttpSession session) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tdevice.getId() == null) {//新建
                //首先判断设备名是否可用
                Example tdeviceExample = new Example(Tdevice.class);
                tdeviceExample.or().andEqualTo("deviceName", tdevice.getDeviceName());
                List<Tdevice> devicelist = deviceService.selectByExample(tdeviceExample);
                if (devicelist != null && devicelist.size() > 0) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前设备名已存在");
                    return resultmap;
                }
                Tuser tuser = (Tuser)session.getAttribute("currentUser");
                tdevice.setCtime(new Date());
                tdevice.setUtime(new Date());
                deviceService.saveNotNull(tdevice);
                Tuserdevice tuserdevice = new Tuserdevice();
                tuserdevice.setUserId(tuser.getId());
                tuserdevice.setDeviceId(deviceService.getDeviceId(tdevice.getDeviceName()));;
                userDeviceService.saveNotNull(tuserdevice);

            } else {//编辑
                Tdevice oldObject = deviceService.selectByKey(tdevice.getId());
                if (oldObject == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前设备名不存在");
                    return resultmap;
                } else {
                    tdevice.setCtime(oldObject.getCtime() == null ? new Date():oldObject.getCtime());
                    tdevice.setUtime(new Date());
                    deviceService.updateNotNull(tdevice);
                }
            }
            resultmap.put("state", "success");
            resultmap.put("mesg", "操作成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "操作失败，系统异常");
            return resultmap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deletedevice")
    @RequiresPermissions(value = {"设备管理"})
    public Map<String, Object> deletedevice(Tdevice tdevice) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tdevice.getId() != null && !tdevice.getId().equals(0)) {
                Tdevice device = deviceService.selectByKey(tdevice.getId());
                if (device == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "删除失败,无法找到该记录");
                    return resultmap;
                } else {

                    //还需删除用户设备中间表
                    Example tuserdeviceexample = new Example(Tuserdevice.class);
                    tuserdeviceexample.or().andEqualTo("deviceId", tdevice.getId());
                    userDeviceService.deleteByExample(tuserdeviceexample);
                    deviceService.delete(tdevice.getId());

                }
            } else {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "删除失败");
            }


            resultmap.put("state", "success");
            resultmap.put("mesg", "删除成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "删除失败，系统异常");
            return resultmap;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectDeviceById")
    @RequiresPermissions(value = {"设备管理"})
    public Map<String, Object> selectUserById(Tdevice tdevice) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tdevice.getId() != null && !tdevice.getId().equals(0)) {
                tdevice = deviceService.selectByKey(tdevice.getId());
                if (tdevice == null) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "无法找到该记录");
                    return resultmap;
                }
            } else {
                resultmap.put("state", "fail");
                resultmap.put("mesg", "无法找到该记录的id");
                return resultmap;
            }

            List<Tuser> userList = userService.selectUsersByDeviceId(tdevice.getId());
            StringBuffer sb = new StringBuffer();
            for (Tuser u : userList) {
                sb.append("," + u.getUserName());
            }
            tdevice.setUsers(sb.toString().replaceFirst(",", ""));

            //所有用户
            Example tuserExample = new Example(Tuser.class);
            //troleExample.or().andNameNotEqualTo("管理员");
            List<Tuser> alluserlist = userService.selectByExample(tuserExample);

            resultmap.put("userList", userList);//用户拥有的所有角色


            Iterator<Tuser> it = alluserlist.iterator();
            while (it.hasNext()) {
                Tuser temp = it.next();
                for (Tuser e2 : userList) {
                    if (temp.getId().compareTo(e2.getId()) == 0) {
                        it.remove();
                    }
                }
            }

            List<Tuser> notinuserlist = alluserlist;

            resultmap.put("notinuserlist", notinuserlist);//设备不拥有的用户

            resultmap.put("tdevice", tdevice);
            resultmap.put("state", "success");
            resultmap.put("mesg", "获取成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "获取失败，系统异常");
            return resultmap;
        }

    }
    //设置用户角色
    @ResponseBody
    @RequestMapping(value = "/saveUserSet")
    @RequiresPermissions(value = {"设备管理"})
    public Map<String, Object> saveRoleSet (Integer[] user, Integer id){
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            // 根据设备id删除所有设备用户关联实体
            Example tuserdeviceeexample = new Example(Tuserdevice.class);
            tuserdeviceeexample.or().andEqualTo("deviceId", id);
            userDeviceService.deleteByExample(tuserdeviceeexample);

            if (user != null && user.length > 0) {
                for (Integer userid : user) {
                    Tuserdevice tuserdevice = new Tuserdevice();
                    tuserdevice.setUserId(userid);
                    tuserdevice.setDeviceId(id);;
                    userDeviceService.saveNotNull(tuserdevice);
                }
            }

            resultmap.put("state", "success");
            resultmap.put("mesg", "设置成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "设置失败，系统异常");
            return resultmap;
        }
    }
}
