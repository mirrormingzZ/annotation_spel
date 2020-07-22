package io.github.mirrormingzz.annotation_spel.method;

import org.springframework.stereotype.Component;

/**
 * @author Mireal
 */
@Component
public class SecurityExpressionRoot implements SecurityExpressionOperations {
    @Override
    public final boolean hasAuthority(String authority) {
        return "ren".equals(authority);
    }
}
