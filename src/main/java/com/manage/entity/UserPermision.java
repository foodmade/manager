package com.manage.entity;

import java.io.Serializable;

/**
 * USER_PERMISION
 * @author 
 */
public class UserPermision implements Serializable {
    private Long perid;

    private String pername;

    /**
     * 1：超级管理员；2：管理员；3：普通会员
     */
    private Integer perlev;

    private static final long serialVersionUID = 1L;

    public Long getPerid() {
        return perid;
    }

    public void setPerid(Long perid) {
        this.perid = perid;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername;
    }

    public Integer getPerlev() {
        return perlev;
    }

    public void setPerlev(Integer perlev) {
        this.perlev = perlev;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserPermision other = (UserPermision) that;
        return (this.getPerid() == null ? other.getPerid() == null : this.getPerid().equals(other.getPerid()))
            && (this.getPername() == null ? other.getPername() == null : this.getPername().equals(other.getPername()))
            && (this.getPerlev() == null ? other.getPerlev() == null : this.getPerlev().equals(other.getPerlev()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPerid() == null) ? 0 : getPerid().hashCode());
        result = prime * result + ((getPername() == null) ? 0 : getPername().hashCode());
        result = prime * result + ((getPerlev() == null) ? 0 : getPerlev().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", perid=").append(perid);
        sb.append(", pername=").append(pername);
        sb.append(", perlev=").append(perlev);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}