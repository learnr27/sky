package com.example.sky.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author chengbb
 * @date 2019.11.27
 */
@RestController
@Slf4j
public class IndexController {

    @RequestMapping("index")
    public Object index(@RequestBody Map map) {
        log.info(map.toString() + "info:{}", System.currentTimeMillis());
        log.error(map.toString() + "error:{}", System.currentTimeMillis());
        log.warn(map.toString() + "warn:{}", System.currentTimeMillis());
        log.trace(map.toString() + "trace:{}", System.currentTimeMillis());
        log.debug(map.toString() + "debug:{}", System.currentTimeMillis());
        return "now:" + System.currentTimeMillis();
    }
}
