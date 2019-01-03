package com.manage.common.commonUtil;

import com.manage.common.Const;
import com.manage.common.enums.ExceptionEnum;
import com.manage.dao.UsersMapper;
import com.manage.entity.Users;

import java.util.List;

public class InvalidMaxCnt {

    private Users users;
    private UsersMapper usersMapper;

    private Boolean flag = true;
    private ExceptionEnum exceptionEnum;

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }

    public Boolean getFlag() {
        return flag;
    }

    public InvalidMaxCnt(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    public InvalidMaxCnt builder(Users users){
        this.users = users;
        return this;
    }

    //检验用户名是否已被使用
    public InvalidMaxCnt checkUserName(){
        Users validUsers = usersMapper.selectByUserNo(users.getUserno());
        if(validUsers != null){
            this.flag = false;
            this.exceptionEnum = ExceptionEnum.ALREADYACCOUNT;
        }
        return this;
    }

    //检查推荐人是否存在
    public InvalidMaxCnt checkPidIsExist(){
        if(!flag){
            return this;
        }
        Users pidUsers = usersMapper.selectByUserNo(users.getPerUserNo());
        if(pidUsers == null){
            this.flag = false;
            this.exceptionEnum = ExceptionEnum.REFERRERNOTFOUND;
        }
        return this;
    }

    //检查当前推荐人是否已经达到最大数量
    public InvalidMaxCnt checkUserReommentCnt(){
        if(!flag){
            return this;
        }
        List<Users> pidUsers = usersMapper.selectByPid(users.getPid());
        if(pidUsers != null && pidUsers.size() >= Const.STATIS_TWO){
            this.flag = false;
            this.exceptionEnum = ExceptionEnum.RECOMMENTERR;
        }
        return this;
    }
}
