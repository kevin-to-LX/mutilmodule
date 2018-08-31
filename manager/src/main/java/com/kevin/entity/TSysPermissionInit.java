package com.kevin.entity;

import javax.persistence.*;

@Table(name = "sys_permission_init")
public class TSysPermissionInit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 閾炬帴鍦板潃
     */
    private String url;

    /**
     * 闇€瑕佸叿澶囩殑鏉冮檺
     */
    @Column(name = "permission_init")
    private String permissionInit;

    /**
     * 鎺掑簭
     */
    private Integer sort;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取閾炬帴鍦板潃
     *
     * @return url - 閾炬帴鍦板潃
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置閾炬帴鍦板潃
     *
     * @param url 閾炬帴鍦板潃
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取闇€瑕佸叿澶囩殑鏉冮檺
     *
     * @return permission_init - 闇€瑕佸叿澶囩殑鏉冮檺
     */
    public String getPermissionInit() {
        return permissionInit;
    }

    /**
     * 设置闇€瑕佸叿澶囩殑鏉冮檺
     *
     * @param permissionInit 闇€瑕佸叿澶囩殑鏉冮檺
     */
    public void setPermissionInit(String permissionInit) {
        this.permissionInit = permissionInit == null ? null : permissionInit.trim();
    }

    /**
     * 获取鎺掑簭
     *
     * @return sort - 鎺掑簭
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置鎺掑簭
     *
     * @param sort 鎺掑簭
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}