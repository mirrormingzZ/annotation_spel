package io.github.mirrormingzz.annotation_spel.handler;

import io.github.mirrormingzz.annotation_spel.handler.HandlerResult;

import java.util.List;

/**
 * @author Mireal
 */
@FunctionalInterface
public interface SecurityMethodHandler {
    HandlerResult handler(List<String> param);
}
