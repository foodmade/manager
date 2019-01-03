package com.manage.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {

    private Long menuid;

    private Long pid;

    private String menuTree;

    private  String menuName;

    private String url;

    private String icon;

    private List<MenuTree> children = new ArrayList<>();

    public List<MenuTree> getChildren() {
        return children;
    }

    public Long getMenuid() {
        return menuid;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuTree() {
        return menuTree;
    }

    public void setChildren(List<MenuTree> children) {
        this.children = children;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuTree(String menuTree) {
        this.menuTree = menuTree;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
