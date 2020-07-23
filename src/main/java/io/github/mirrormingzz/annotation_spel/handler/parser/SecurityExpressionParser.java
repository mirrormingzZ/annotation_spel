package io.github.mirrormingzz.annotation_spel.handler.parser;

import io.github.mirrormingzz.annotation_spel.handler.HandlerEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Mireal
 * @date 2020/7/23 15:54
 */
@FunctionalInterface
public interface SecurityExpressionParser {
    SpelExpressionParser parser = new SpelExpressionParser();
    DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    default String parse(String spElString, ProceedingJoinPoint p) {
        MethodSignature methodSignature = (MethodSignature) p.getSignature();
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        Expression expression = parser.parseExpression(spElString);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = p.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }

    HandlerEntity parse(ProceedingJoinPoint p, String value);
}

