package com.hejie.tmall_ssm.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Program: tmall_ssm
 * @Description:
 * @Author: hejie
 * @Create: 2020-07-07 17:27
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView doUnauthorizedException(NativeWebRequest request, UnauthorizedException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("message/noAccess");
        modelAndView.addObject("e", e);
        return modelAndView;
    }
}
