package org.demo.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userControler {
    @RequestMapping("/index")
    public String index(){
            return "index.html";
        }
}
