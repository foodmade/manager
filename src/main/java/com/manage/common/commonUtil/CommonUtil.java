package com.manage.common.commonUtil;

import com.manage.common.redisUtil.RedisClient;
import com.manage.dao.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class CommonUtil {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private UsersMapper usersMapper;

    public Boolean strIsNULL(String str){
        return str == null || str.equals("") || str.equals("null");
    }

    public Object invalidObject(Object obj){
        return obj == null?"":obj;
    }

    /**
     * 针对List结构排序
     * @param compeList  源
     * @param filedName  排序字段
     * @param order      排序方式
     */
    public void doSortList(List<HashMap<String,Object>> compeList,final String order,final String filedName) {
        compeList.sort((o1, o2) -> {
            Long l1 =Long.parseLong( o1.get(filedName).toString().replaceAll("-","").replaceAll(" ",""));
            Long l2 =Long.parseLong( o2.get(filedName).toString().replaceAll("-","").replaceAll(" ",""));
            if (order.equals("asc")) {
                return l1.compareTo(l2);

            } else {
                return l2.compareTo(l1);
            }
        });
    }

    public List getPagingList(List<HashMap<String,Object>> listRows, Integer page, Integer pageSize){
        Integer conpageSize=pageSize==null?listRows.size():pageSize;
        if(listRows==null||listRows.size()==0){
            return new ArrayList();
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=listRows.size();
            page=1;
        }
        if(listRows.size() < page*pageSize){
            pageSize=listRows.size();
        }else{
            pageSize=page*pageSize;
        }
        return listRows.subList((page-1)*conpageSize,pageSize);
    }

    /**
     * 通过用户查询该用户的菜单列表
     * @param userId
     * @return
     */
    public List getMenuTreeByUser(Long userId){
        return  usersMapper.getMenuTreeByUser(userId);
    }

    /**
     * 简单实体对象转map函数
     * @param list  源集合
     */
    public static List<HashMap<String, Object>> transferFormat(List<?> list) throws Exception {
        List<HashMap<String,Object>> result = new ArrayList<>();
        if(list == null || list.isEmpty()){
            return result;
        }
        for (Object obj:list) {
            Field[] field = obj.getClass().getDeclaredFields();
            setFieldName(field,obj,result);
        }
        return result;
    }

    private static void setFieldName(Field[] field, Object obj, List<HashMap<String,Object>> result) throws Exception {
        HashMap<String,Object> itemMap = new HashMap<>();
        for (Field aField : field) {
            String name = aField.getName();
            //属性名
            name = name.substring(0, 1).toUpperCase() + name.substring(1);

            Method m = null;
            try {
                m = obj.getClass().getMethod("get" + name);
            } catch (Exception e) {
                continue;
            }
            if(m==null){
                continue;
            }
            Object value = m.invoke(obj);

            if(value == null || value.equals("")){
                itemMap.put(name,"");
            }else{
                itemMap.put(name,value);
            }
        }
        result.add(itemMap);
    }

}
