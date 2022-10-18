package com.deskita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.HashMap;

@Controller
@RequestMapping("/chat")
@PermitAll
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
    @PermitAll
    public String createMessage(){
        return "<div>That funny!</div>";
    }

}
