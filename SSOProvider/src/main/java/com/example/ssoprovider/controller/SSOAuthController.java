package com.example.ssoprovider.controller;

import com.example.ssoprovider.service.SSOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SSOAuthController {

    @Autowired
    private SSOAuthService ssoAuthService;


}
