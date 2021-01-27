package com.yonyou.mde.web.model;

import javax.persistence.*;

@Table(name = "view_detail")
public class ViewDetail {
    @Id
    private String id;

    private String viewid;

    private String layouttype;

    private String dimid;

    private String scope;

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
     * @return viewid
     */
    public String getViewid() {
        return viewid;
    }

    /**
     * @param viewid
     */
    public void setViewid(String viewid) {
        this.viewid = viewid;
    }

    /**
     * @return layouttype
     */
    public String getLayouttype() {
        return layouttype;
    }

    /**
     * @param layouttype
     */
    public void setLayouttype(String layouttype) {
        this.layouttype = layouttype;
    }

    /**
     * @return dimid
     */
    public String getDimid() {
        return dimid;
    }

    /**
     * @param dimid
     */
    public void setDimid(String dimid) {
        this.dimid = dimid;
    }

    /**
     * @return scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
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