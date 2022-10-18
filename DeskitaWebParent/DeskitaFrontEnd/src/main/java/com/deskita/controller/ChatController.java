package com.deskita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.HashMap;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String getChatPage(){
        return "chat/index";
    }
    @GetMapping("/message")
    public ModelAndView message(){
        HashMap<String,Object> model= new HashMap<>();
        return new ModelAndView("/chat/chat-window", model);
    }
    @PostMapping("/message")
    @ResponseBody
    public String createMessage(@RequestParam("choice")String choice){
        switch (choice){
            case "joke":return "<div>That funny!</div>";
            case "google":return "<div>Hello</div>";
        }
        return "<div>I don't understand</div>";

    }

}
