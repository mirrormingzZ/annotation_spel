package io.github.mirrormingzz.annotation_spel.handler.parser;

import io.github.mirrormingzz.annotation_spel.handler.HandlerEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Mireal
 * @date 2020/7/23 16:12
 */
@Component
public class MySecurityExpressionParser implements SecurityExpressionParser {

    private static final String SPLIT_EXPRESS = "=>";
    private static final String SPLITOR = ",";

    @Override
    public HandlerEntity parse(ProceedingJoinPoint p, String value) {
        Assert.isTrue(value.contains(SPLIT_EXPRESS), () -> String.format("权限表达式语法错误: %s, 例：project => #id,#auth,#param", value));
        Assert.isTrue(value.split(SPLIT_EXPRESS).length == 2, () -> String.format("权限表达式语法错误: %s, 例：project => #id,#auth,#param", value));
        String m = value.split(SPLIT_EXPRESS)[0].trim();
        String params = value.split(SPLIT_EXPRESS)[1].trim();

        return HandlerEntity.of(m, Arrays.stream(params.split(SPLITOR)).map(s -> parse(s, p)).collect(Collectors.toList()));
    }
}
