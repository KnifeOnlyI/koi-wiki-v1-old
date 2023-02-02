package fr.koi.wikiapi.web.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/create-article")
    @RolesAllowed("create-article")
    public Map<String, String> createArticle() {
        Map<String, String> value = new HashMap<>();

        value.put("articleCreated", "true");

        return value;
    }

    @GetMapping("/delete-article")
    @RolesAllowed("delete-article")
    public Map<String, String> test() {
        Map<String, String> value = new HashMap<>();

        value.put("articleDeleted", "true");

        return value;
    }
}
