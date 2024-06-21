package org.sparta.jenview.service;

import org.sparta.jenview.repository.JenViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JenViewService {

    @Autowired
    private JenViewRepository jenViewRepository;

    public String hello() {
        return "hello";
    }
}
