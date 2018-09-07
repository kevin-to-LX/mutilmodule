package com.kevin.web;

import com.github.pagehelper.PageHelper;
import com.kevin.entity.Tdevice;
import com.kevin.entity.Tuser;
import com.kevin.model.JqgridBean;
import com.kevin.model.PageRusult;
import com.kevin.service.TdeviceService;
import com.kevin.service.TuserService;
import com.kevin.service.TuserdeviceService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinyugai on 2018/8/20.
 */
@Controller
@RequestMapping("admin/userdevice")
@Api(value ="admin/userdevice",tags = "设备管理模块")
public class DeviceController {
    @Resource
    private TdeviceService deviceService;

    @Resource
    private TuserdeviceService userDeviceService;

    @Resource
    private TuserService userService;

    @RequestMapping("/todevicemanage")
    public String todevicemanage(){ return "power/userdevice";}

    /**
     * 分页查询设备信息
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    /*@RequiresPermissions(value = {"设备管理"})*/

    public Map<String, Object> list(JqgridBean jqgridbean , HttpSession session) throws Exception {
        /*String userName,@RequestParam(value="page",required=false)Integer page*/
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
        Tuser user =  (Tuser)session.getAttribute("currentUser");
        List<Tdevice> deviceList = deviceService.selectDeviceByUserId(user.getId());
        PageRusult<Tdevice> pageRusult = new PageRusult<Tdevice>(deviceList);


       /* Integer totalrecords = deviceService.selectCountByExample(tdeviceExample);//总记录数
        Page pagebean = new Page(jqgridbean.getLength() * ((jqgridbean.getPage() > 0 ? jqgridbean.getPage() : 1) - 1), jqgridbean.getLength(), totalrecords);
        tdeviceExample.setPage(pagebean);
        tdeviceExample.setOrderByClause(jqgridbean.getSidx() + " " + jqgridbean.getSord());
        List<Tdevice> deviceList = deviceService.selectByExample(tdeviceExample);*/

        //userService.addUserSet(deviceList);
        resultmap.put("currpage", String.valueOf(pageRusult.getPageNum()));
        resultmap.put("totalpages", String.valueOf(pageRusult.getPages()));
        resultmap.put("totalrecords", String.valueOf(pageRusult.getTotal()));
        resultmap.put("datamap", deviceList);

        return resultmap;
    }


}
