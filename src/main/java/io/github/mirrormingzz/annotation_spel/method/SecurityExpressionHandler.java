package io.github.mirrormingzz.annotation_spel.method;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

//todo
public interface SecurityExpressionHandler<T> extends AopInfrastructureBean {
    ExpressionParser getExpressionParser();

    EvaluationContext createEvaluationContext(Authentication authentication, T invocation);
}