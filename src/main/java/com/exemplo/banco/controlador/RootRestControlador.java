package com.exemplo.banco.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootRestControlador {

    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of("status", "ok", "app", "Sistema Bancário Backend");
    }
}