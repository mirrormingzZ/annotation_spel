package io.github.mirrormingzz.annotation_spel.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Mireal
 * @date 2020/7/23 16:13
 */
@Data
@AllArgsConstructor(staticName = "of")
public class HandlerEntity {
    private String m;
    private List<Object> params;
}
