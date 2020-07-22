package io.github.mirrormingzz.annotation_spel.aop;

import io.github.mirrormingzz.annotation_spel.annotation.MyAnnotation;
import io.github.mirrormingzz.annotation_spel.method.SecurityExpressionHandler;
import io.github.mirrormingzz.annotation_spel.method.SecurityExpressionOperations;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.lang.reflect.Method;

/**
 * @author Mireal
 */
@Service
@Aspect
public class MyAnnotationAop {
    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser parser = new SpelExpressionParser();
    @Autowired
    private SecurityExpressionOperations securityExpressionOperations;
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(io.github.mirrormingzz.annotation_spel.annotation.MyAnnotation)")
    public Object around(ProceedingJoinPoint p) throws Throwable {

        MethodSignature sign = (MethodSignature) p.getSignature();
        Method method = sign.getMethod();

        MyAnnotation preAuthorize = method.getAnnotation(MyAnnotation.class);
        if (preAuthorize == null) {
            return p.proceed();
        }

        String value = preAuthorize.value();
        String s = value.split("\\(")[0];
        String spelVal = value.split("'")[1];

        String resV = generateKeyBySpEL(spelVal, p);
        Method m = SecurityExpressionOperations.class.getMethod(s, String.class);
        boolean res = (boolean) m.invoke(securityExpressionOperations, resV);


        if (res) {
            return p.proceed();
        }

        throw new NoPermissionException("无权限访问");
    }

    public String generateKeyBySpEL(String spELString, ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        Expression expression = parser.parseExpression(spELString);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }
}