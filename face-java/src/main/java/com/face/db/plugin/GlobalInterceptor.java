package com.face.db.plugin;

import com.alibaba.fastjson.JSON;
import com.face.WebConstants;
import com.face.page.Page;
import com.face.utils.Utils;
import com.face.vo.ReturnData;
import com.face.vo.StatusCode;
import com.face.vo.UserSessionInfo;
import com.face.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局拦截器
 */
public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object sessionKeyObj = request.getHeader(WebConstants.USER_INFO_KEY);
        String sessionKey = Utils.isEmpty(sessionKeyObj) ? WebConstants.USER_INFO_BEAN : sessionKeyObj.toString();
        if (sessionKey.contains("=")) {
            sessionKey = sessionKey.substring(sessionKey.indexOf("="));
            UserVo user = (UserVo) redisTemplate.opsForHash().get(WebConstants.USER_REDIS_KEY, sessionKey);
            UserSessionInfo.getUserSessionInfo().setUserVo(user);
            return true;
        }
        response401(response);
        return false;
    }


    private void response401(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try {
            response.getWriter().print(JSON.toJSONString(new ReturnData(StatusCode.NEED_LOGIN, "用户未登录！", "")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //移除分页消息
        Page.threadLocal.remove();
        UserSessionInfo.getUserSessionInfo().clean();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Page.threadLocal.remove();
    }


}
