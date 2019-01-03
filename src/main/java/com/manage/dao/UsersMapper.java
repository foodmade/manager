package com.manage.dao;

import com.manage.entity.Users;
import com.manage.vo.QueryUserListRequest;
import com.manage.vo.UserScope;
import com.manage.vo.in.UserInRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users selectByUserNo(String userno);

    List<Users> selectByPid(Long pid);

    List<Users> qryUserList(UserScope userScope);

    List<Users> selectByTopId(Long userId);

    void insertBatch(List<Users> userList);

    List<Users> selectSearchQuery(QueryUserListRequest request);

    Integer selectSearchQueryCnt(QueryUserListRequest request);

    List<Users> selectByPrimaryFilter(Users users);

    List getMenuTreeByUser(Long userId);

    /**
     * 查询当前节点的详细信息
     */
    Users selectByTopPid(String topPid);

    /**
     * 获取当前节点所有的子节点 排除指定用户方式
     */
    List<Users> selectAllChildNodeUser(UserInRequest userInRequest);

    /**
     * 根据用户id集合批量查询详细信息
     */
    List<Users> selectUserListByUserIds(List<Long> preUserIdList);

    /**
     * 获取所有用户
     */
    List<Users> selectAllUser();

}