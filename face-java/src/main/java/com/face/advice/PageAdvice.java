package com.face.advice;

import com.face.page.Page;
import com.face.utils.Utils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order(3)
public class PageAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (obj instanceof Page) {
            Page inPage = Page.threadLocal.get();
            Page page = (Page) obj;
            if (page.getTotal() < 0 && Utils.notEmpty(inPage)) {
                page.setTotal(inPage.getTotal());
            }
        }
        return obj;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }


}
