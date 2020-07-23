package io.github.mirrormingzz.annotation_spel.aop;

import io.github.mirrormingzz.annotation_spel.annotation.MyAnnotation;
import io.github.mirrormingzz.annotation_spel.handler.HandlerEntity;
import io.github.mirrormingzz.annotation_spel.handler.HandlerResult;
import io.github.mirrormingzz.annotation_spel.handler.SecurityMethodHandlerFactory;
import io.github.mirrormingzz.annotation_spel.handler.SecurityResultEnum;
import io.github.mirrormingzz.annotation_spel.handler.parser.SecurityExpressionParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.lang.reflect.Method;

/**
 * @author Mireal
 */
@Service
@Aspect
public class MyAnnotationAop {
    @Autowired
    SecurityExpressionParser securityExpressionParser;


    @Around("@annotation(io.github.mirrormingzz.annotation_spel.annotation.MyAnnotation)")
    public Object around(ProceedingJoinPoint p) throws Throwable {

        MethodSignature sign = (MethodSignature) p.getSignature();
        Method method = sign.getMethod();

        MyAnnotation preAuthorize = method.getAnnotation(MyAnnotation.class);

        String value = preAuthorize.value();
        HandlerEntity parse = securityExpressionParser.parse(p, value);


        HandlerResult result = SecurityMethodHandlerFactory.getHandler(parse).handler(parse.getParams());
        if (result.getResultEnum() == SecurityResultEnum.REJECT) {
            throw new AuthenticationException("无权限访问");
        }

        switch (result.getResultEnum()) {
            case REJECT:
                throw new AuthenticationException("无权限访问");
            case PERMIT:
                return p.proceed();
            case PERMIT_AND_PERMISSIONS:
                Object proceed = p.proceed();
                Object data = result.getPermissions();
                //todo 取得权限返回值设置到通用返回中
                return proceed;
            default:
                return p.proceed();
        }
    }
}