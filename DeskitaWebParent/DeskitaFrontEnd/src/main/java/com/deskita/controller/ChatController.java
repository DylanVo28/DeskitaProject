package com.deskita.controller;

import com.deskita.common.entity.Advise;
import com.deskita.common.entity.Product;
import com.deskita.dto.MessageResponseDTO;
import com.deskita.repository.AdviseRepository;
import com.deskita.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String getChatPage(){
        return "chat/index";
    }

    @Autowired
    private ProductService productService;

    @Autowired
    private AdviseRepository adviseRepository;

    @GetMapping("/message")
    public ModelAndView message(){
        HashMap<String,Object> model= new HashMap<>();
        return new ModelAndView("/chat/chat-window", model);
    }
    @RequestMapping(value = "/message",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity createMessage(@RequestParam("choice")String choice,
                                                            @RequestParam("value")String value,
                                        @RequestParam("name")String name,
                                        @RequestParam("phone")String phone,
                                        @RequestParam("productId")String productId
    ){
        switch (choice){
            case "tu_van":
                return new ResponseEntity<>(new MessageResponseDTO("choice_sp","<div>Bạn vui lòng nhập tên sản phẩm để chúng mình giúp đỡ bạn nhé</div>"),HttpStatus.OK);
            case "choice_sp":
                return new ResponseEntity<>(new MessageResponseDTO("show_sp",productService.findProductByName(value)), HttpStatus.OK);
            case "send_customer":
                Advise advise=new Advise(phone,name,Integer.parseInt(productId));
                adviseRepository.save(advise);
                return new ResponseEntity<>(new MessageResponseDTO("end","<div>Chúng tôi đã nhận được đẩy đủ thông tin từ bạn, chúng tôi sẽ liện hệ sớm với bạn</div>"), HttpStatus.OK);


        }
        return null;

    }

}
