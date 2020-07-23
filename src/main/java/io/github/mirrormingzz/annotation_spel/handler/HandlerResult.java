package io.github.mirrormingzz.annotation_spel.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * @author Mireal
 * @date 2020/7/23 16:28
 */
@Data
@AllArgsConstructor(staticName = "of")
public class HandlerResult<T> {
    private SecurityResultEnum resultEnum;
    private List<String> permissions;

    public static HandlerResult permit() {
        return new HandlerResult(SecurityResultEnum.PERMIT, null);
    }

    public static HandlerResult reject() {
        return new HandlerResult(SecurityResultEnum.REJECT, null);
    }

    public static HandlerResult permitPermissions(List<String> permissions) {
        return new HandlerResult(SecurityResultEnum.REJECT, permissions);
    }
}
