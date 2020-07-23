package io.github.mirrormingzz.annotation_spel.handler.parser;

import io.github.mirrormingzz.annotation_spel.handler.HandlerEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mireal
 * @date 2020/7/23 15:54
 */
@FunctionalInterface
public interface SecurityExpressionParser {
    SpelExpressionParser PARSER = new SpelExpressionParser();
    DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    default Object parse(String spElString, ProceedingJoinPoint p) {
        MethodSignature methodSignature = (MethodSignature) p.getSignature();
        //获得方法参数名字列表
        String[] paramNames = NAME_DISCOVERER.getParameterNames(methodSignature.getMethod());
        Expression expression = PARSER.parseExpression(spElString);
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = p.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(Objects.requireNonNull(paramNames)[i], args[i]);
        }
        return expression.getValue(context);
    }

    HandlerEntity parse(ProceedingJoinPoint p, String value);
}

