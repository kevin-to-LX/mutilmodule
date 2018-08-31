package com.kevin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_device")
public class Tdevice {
    /**
     * 设备id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备名
     */
    @Column(name = "device_name")
    private String deviceName;

    /**
     * 设备类型
     */
    @Column(name = "device_type")
    private String deviceType;

    /**
     * 设备通道号
     */
    @Column(name = "device_channel")
    private String deviceChannel;

    /**
     * 设备编号
     */
    @Column(name = "device_no")
    private String deviceNo;

    /**
     * 上线时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date ctime;

    /**
     * 最近一次操作时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date utime;

    /**
     * 获取设备id
     *
     * @return id - 设备id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置设备id
     *
     * @param id 设备id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取设备名
     *
     * @return device_name - 设备名
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名
     *
     * @param deviceName 设备名
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    /**
     * 获取设备类型
     *
     * @return device_type - 设备类型
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型
     *
     * @param deviceType 设备类型
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    /**
     * 获取设备通道号
     *
     * @return device_channel - 设备通道号
     */
    public String getDeviceChannel() {
        return deviceChannel;
    }

    /**
     * 设置设备通道号
     *
     * @param deviceChannel 设备通道号
     */
    public void setDeviceChannel(String deviceChannel) {
        this.deviceChannel = deviceChannel == null ? null : deviceChannel.trim();
    }

    /**
     * 获取设备编号
     *
     * @return device_no - 设备编号
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置设备编号
     *
     * @param deviceNo 设备编号
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    /**
     * 获取上线时间
     *
     * @return ctime - 上线时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置上线时间
     *
     * @param ctime 上线时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取最近一次操作时间
     *
     * @return utime - 最近一次操作时间
     */
    public Date getUtime() {
        return utime;
    }

    /**
     * 设置最近一次操作时间
     *
     * @param utime 最近一次操作时间
     */
    public void setUtime(Date utime) {
        this.utime = utime;
    }
    @Transient
    String users;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}