package io.github.mirrormingzz.annotation_spel.handler.parser;

import io.github.mirrormingzz.annotation_spel.handler.HandlerEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Mireal
 * @date 2020/7/23 16:12
 */
@Component
public class MySecurityExpressionParser implements SecurityExpressionParser {

    public static final String SPLIT_EXPRESS = "=>";

    @Override
    public HandlerEntity parse(ProceedingJoinPoint p, String value) {
        if (!value.contains(SPLIT_EXPRESS) || value.split(SPLIT_EXPRESS).length != 2) {
            throw new RuntimeException("权限表达式错误");
        }
        String m = value.split(SPLIT_EXPRESS)[0].trim();
        String params = value.split(SPLIT_EXPRESS)[1].trim();
        String[] split = params.split(",");
        return HandlerEntity.of(m, Arrays.stream(split).map(s -> parse(s, p)).collect(Collectors.toList()));
    }
}
