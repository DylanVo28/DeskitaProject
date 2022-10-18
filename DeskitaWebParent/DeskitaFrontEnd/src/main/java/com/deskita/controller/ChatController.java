package com.deskita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String getChatPage(){
        return "chat/index";
    }
    @GetMapping("/message")
    @ResponseBody
    public String message(){
        return "hello";
    }
}
