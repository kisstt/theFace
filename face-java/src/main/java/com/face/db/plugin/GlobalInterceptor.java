package com.face.db.plugin;

import com.face.WebConstants;
import com.face.page.Page;
import com.face.utils.Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 全局拦截器
 */
public class GlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object sessionKeyObj = request.getHeader(WebConstants.USER_INFO_KEY);
        String sessionKey = Utils.isEmpty(sessionKeyObj) ? WebConstants.USER_INFO_KEY : sessionKeyObj.toString();
        HttpSession session = request.getSession(false);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //移除分页消息
        Page.threadLocal.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
