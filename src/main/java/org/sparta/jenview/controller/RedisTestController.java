package org.sparta.jenview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/set")
    public String setKeyValue(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Key-Value pair set successfully";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : "Key not found";
    }
}