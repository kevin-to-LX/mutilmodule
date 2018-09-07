package com.kevin.mq.redis.constants;

/**
 * Created by jinyugai on 2018/8/28.
 */
public class RedisConstants {
    public static final String DEVICE = "device_";//设备缓存
    public static final String DEVICE_CODE = "device_code_";//设备编号和主键缓存
    public static final String DEVICE_BY_GROUP = "group_device";//分组下面设备缓存
    public static final String DEVICE_COUNT_BY_GROUP = "group_of_device_count";//分组下面设备在线数量，总数缓存
    public static final String CAMERA_CODE = "camera_code_";//监控点缓存
    public static final String CAMERA = "camera_";//监控点主键和编号缓存
    public static final String CAMERA_BY_GROUP = "group_camera";//分组下面监控点缓存
    public static final String CAMERA_COUNT_BY_GROUP = "group_of_camera_count";//分组下面监控点在线数量，总数缓存

    public static final String ALARM_DASHBOARD = "alarm_dashboard";//dashboard报警缓存
    public static final String CAMERA_DASHBOARD = "camera_dashboard";//dashboard监控点缓存
    public static final String DEVICE_DASHBOARD = "device_dashboard";//dashboard设备缓存

    public static final String USER_DASHBOARD = "user_dashboard";//dashboard用户缓存
    public static final String USER_ONLINE = "user_online";//用户在线缓存
    public static final String USER_LOGIN_SESSION = "user_login_session";//登入用户SessionId


    public static final String ST_SESSION = "st_session";
    public static final String SESSION_ST = "session_st";

    public static final String EZVIZ_ALARM_CONSUMERID = "ezviz_alarm_consumerid";

}
