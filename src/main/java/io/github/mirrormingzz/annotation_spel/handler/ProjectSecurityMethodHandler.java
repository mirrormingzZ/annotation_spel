package io.github.mirrormingzz.annotation_spel.handler;

import java.util.List;

/**
 * @author Mireal
 * @date 2020/7/23 14:35
 */
public class ProjectSecurityMethodHandler implements SecurityMethodHandler {

    @Override
    public HandlerResult handler(List<Object> param) {
        return HandlerResult.permit();
    }
}
