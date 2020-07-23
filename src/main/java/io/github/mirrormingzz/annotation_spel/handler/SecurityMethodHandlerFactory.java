package io.github.mirrormingzz.annotation_spel.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mireal
 * @date 2020/7/23 14:32
 */
public class SecurityMethodHandlerFactory {
    private static Map<String, SecurityMethodHandler> handlerMap = new ConcurrentHashMap<>();
    private static EmptyHandler EmptyHandler = new EmptyHandler();

    static class EmptyHandler implements SecurityMethodHandler {
        @Override
        public HandlerResult handler(List<String> param) {
            return HandlerResult.reject();
        }
    }

    static {
        handlerMap.put("project", new ProjectSecurityMethodHandler());
        handlerMap.put("task", new TaskSecurityMethodHandler());
    }

    public static SecurityMethodHandler getHandler(HandlerEntity entity) {
        return handlerMap.get(entity.getM()) == null ? EmptyHandler : handlerMap.get(entity.getM());
    }
}
