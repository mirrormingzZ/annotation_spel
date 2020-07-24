package io.github.mirrormingzz.annotation_spel.handler;

import java.util.List;

/**
 * @author Mireal
 */
@FunctionalInterface
public interface SecurityMethodHandler {
    HandlerResult handler(List<Object> param);
}
