package com.yonyou.mde.web.model;

import javax.persistence.*;

public class View {
    @Id
    private String id;

    private String cubeid;

    private String name;

    private Integer position;

    private Integer version;

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
        this.id = id;
    }

    /**
     * @return cubeid
     */
    public String getCubeid() {
        return cubeid;
    }

    /**
     * @param cubeid
     */
    public void setCubeid(String cubeid) {
        this.cubeid = cubeid;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}