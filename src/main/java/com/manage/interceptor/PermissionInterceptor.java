package com.manage.interceptor;

import com.alibaba.fastjson.JSON;
import com.manage.annotation.BaseChek;
import com.manage.common.Const;
import com.manage.common.enums.ExceptionEnum;
import com.manage.common.enums.PermissionEnum;
import com.manage.common.exception.PermissionException;
import com.manage.common.exception.TokenException;
import com.manage.common.support.ApiRequestSupport;
import com.manage.entity.UserResponse;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 权限检查拦截器
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = Logger.getLogger(PermissionInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("进入权限拦截器.......");

        if (handler instanceof HandlerMethod) {
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                BaseChek annotation = method.getAnnotation(BaseChek.class);

                if(annotation == null){
                    return true;
                }

                if(!annotation.needCheckToken()){
                    return true;
                }

                String permission = annotation.permissionFilter();

                UserResponse user;
                try {
                    user = JSON.parseObject(JSON.toJSONString(request.getAttribute(Const.ACCESSTOKENKEY)),UserResponse.class);
                } catch (Exception e) {
                    throw new TokenException(ExceptionEnum.TOKENRERROR);
                }

                if(user == null || user.getRole() == null ){
                        throw new TokenException(ExceptionEnum.TOKENRERROR);
                }

                //获取当前接口的权限
                Integer role_level = PermissionEnum.find(permission,PermissionEnum.EMPLOYEE).getPre_level();
                //获取用户的权限描述
                String user_desc = PermissionEnum.find(Integer.parseInt(user.getRole()+""),PermissionEnum.EMPLOYEE).getDescription();
                if(user.getRole() > role_level){
                    logger.warn("url:"+request.getRequestURI()+"  userName:"+user.getUserNo()+" 权限不足");
                    logger.warn("need authority：【"+annotation.permissionFilter()+"】 Now authority：【"+user_desc+"】");
                    //说明方法上定义的权限大于当前用户的权限 终端此次操作
                    throw new PermissionException(ExceptionEnum.PERMISSIONERR);
                }

            } catch (TokenException e) {
                logger.error(this.getClass().getSimpleName()+" : "+e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response,e.getCode(),e.getMessage());
                return false;
            }catch (PermissionException e){
                logger.error(this.getClass().getSimpleName()+" : "+e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response,e.getCode(),e.getMessage());
                return false;
            } catch (Exception e){
                logger.error(e.getMessage());
                ApiRequestSupport.invokeExceptionWrapper(response, ExceptionEnum.SERVER_ERR.getCode(),
                        ExceptionEnum.SERVER_ERR.getMessage());
                return false;
            }
        }


        return true;
    }
}
