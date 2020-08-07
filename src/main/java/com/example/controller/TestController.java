package com.example.controller;
//

import com.example.annotation.Track;
import com.example.mo.DemoMO;
import com.example.mo.ResponseMO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    @Track(propertyName = "name")
    public ResponseMO test(String cardId) {
        DemoMO demoMO = new DemoMO();
        demoMO.setCard(cardId);
        demoMO.setName("张三");
        return ResponseMO.successWithData(demoMO);
    }
}