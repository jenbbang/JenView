package org.sparta.jenview.controller;

import org.sparta.jenview.service.JenViewService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jenview")
public class JenViewController {

    private final JenViewService jenViewService;

    public JenViewController(JenViewService jenViewService) {
        this.jenViewService = jenViewService;
    }

    @RequestMapping("/hello")
    public String hello() {
        return jenViewService.hello();
    }}
