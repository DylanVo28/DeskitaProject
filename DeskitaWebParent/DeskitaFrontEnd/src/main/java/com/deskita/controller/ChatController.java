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
            case "product":return "<div>Nếu mình đã chọn được sản phẩm, vui lòng cho em xin tên sản phẩm và địa chỉ để em kiểm tra thời gian và hỗ trợ nhanh hơn.</div>";
            case "insurance":return "<div>Bạn cần tra cứu sản phẩm nào.</div>";
            case "staff":return "<div>Bạn đợi một lát ạ.</div>";
            case "advise":return "<div>Tất nhiên là được ạ.</div>";
        }
        return "<div>I don't understand</div>";

    }

}
