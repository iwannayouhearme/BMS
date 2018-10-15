package com.fhh.base;

import java.io.Serializable;

/**
 * 功能描述：（实体类基类）
 * @author: biubiubiu小浩
 * @date: 2018-10-09 11:18
 */
public class BaseEntity implements Serializable{
    /**
     * uuid
     */
    private String id;
    /**
     * 自增id
     */
    private String rowid;
    /**
     * 创建时间
     */
    private String create_time;
    /**
     * 更新时间
     */
    private String update_time;
    /**
     * 删除标记
     */
    private String isdel;
    /**
     * 删除人id
     */
    private String del_per;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getDel_per() {
        return del_per;
    }

    public void setDel_per(String del_per) {
        this.del_per = del_per;
    }
}
