package com.middleware.middlewarediscussionmanagement;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.CoderResult;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
//@CrossOrigin
public class UserController {
    @GetMapping(path = "/test")
//    @CrossOrigin(origins = "http://localhost:8081")
    public List test() { return List.of("Test Message"); }

    @GetMapping(path = "/authenticate")
    public String authenticate() { return "test"; }
}
