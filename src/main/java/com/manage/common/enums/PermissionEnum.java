package com.manage.common.enums;

import com.manage.common.enums.enumHelper.EnumFindHelper;
import com.manage.common.enums.enumHelper.EnumKeyGetter;

/**
 * 权限枚举类
 */
public enum PermissionEnum {

    SUPER_ADMIN(1,"超级管理员"),
    ADMIN(2,"管理员"),
    EMPLOYEE(3,"员工");

    private Integer pre_level;

    private String description;

    PermissionEnum(Integer level, String description){
        this.pre_level = level;
        this.description = description;
    }

    static final EnumFindHelper<PermissionEnum, String> desptHelper = new EnumFindHelper<PermissionEnum, String>(
            PermissionEnum.class, new DesptGetter());


    static final EnumFindHelper<PermissionEnum,Integer> levelHelper = new EnumFindHelper<PermissionEnum,Integer>(
            PermissionEnum.class,new LevelGetter());

    static class DesptGetter implements EnumKeyGetter<PermissionEnum, String> {
        @Override
        public String getKey(PermissionEnum enumValue) {
            return enumValue.description;
        }
    }

    /**
     * 枚举处理器返回结果处理方式
     */
    static class LevelGetter implements EnumKeyGetter<PermissionEnum, Integer> {
        @Override
        public Integer getKey(PermissionEnum enumValue) {
            return enumValue.pre_level;
        }
    }

    /**
     * 通过value反查询key
     */
    public static PermissionEnum find(String despt, PermissionEnum defaultValue){
        return desptHelper.find(despt, defaultValue);
    }

    /**
     * 通过key查询value
     */
    public static PermissionEnum find(Integer level,PermissionEnum defaultValue){
        return levelHelper.find(level, defaultValue);
    }

    public Integer getPre_level() {
        return pre_level;
    }

    public void setPre_level(Integer pre_level) {
        this.pre_level = pre_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
