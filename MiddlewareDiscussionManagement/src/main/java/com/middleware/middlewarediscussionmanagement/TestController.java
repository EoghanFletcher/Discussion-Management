package com.middleware.middlewarediscussionmanagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class TestController {

    @GetMapping(path = "")
    public String testForConnectivity () {
        System.out.println("testForConnectivity");

        try {
            System.out.println("Connection established");
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [testForConnectivity], ex: " + ex);
            ex.printStackTrace();
        }
        return null;
    }
}
