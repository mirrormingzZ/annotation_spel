# annotation_spel
> 模仿SpringSecurity的@PreAuthorize("hasPermission(‘xx’)") 实现注解调用方法并支持spel

## 思路
- 定义注解
```
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface MyAnnotation {
    String value() default "";
}


ex:
    @MyAnnotation("project => #id,#auth,#param")
```
- Aop 拦截注解，解析注解表达式 `project => #id,#auth,#param`
- 根据表达式从`SecurityMethodHandlerFactory`中取得对应的处理器
```java
public class SecurityMethodHandlerFactory {
    private static Map<String, SecurityMethodHandler> handlerMap = new ConcurrentHashMap<>();
    private static SecurityMethodHandler EmptyHandler = param -> HandlerResult.reject();

    static {
        handlerMap.put("project", new ProjectSecurityMethodHandler());
        handlerMap.put("task", new TaskSecurityMethodHandler());
    }

    public static SecurityMethodHandler getHandler(HandlerEntity entity) {
        return handlerMap.get(entity.getM()) == null ? EmptyHandler : handlerMap.get(entity.getM());
    }
}
```
- SpEl解析 `#id,#auth,#param` 取得对应的参数集合，传入handler做自定义处理
```java
    @MyAnnotation("project => #id,#auth,#param")
    @GetMapping("test")
    public String test(Integer id, String param, Auth auth) {}
```