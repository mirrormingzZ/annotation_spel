package io.github.mirrormingzz.annotation_spel.controller;

import io.github.mirrormingzz.annotation_spel.annotation.MyAnnotation;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mireal
 */
@RestController
public class MyController {
    @Data
    static class Auth {
        private Integer id;
        private String name;
    }

    @MyAnnotation("hasAuthority('#auth.name')")
    @GetMapping("test")
    public String test(String param, Auth auth) {
        System.out.println(param);
        System.out.println(auth);
        return "233";
    }
}
