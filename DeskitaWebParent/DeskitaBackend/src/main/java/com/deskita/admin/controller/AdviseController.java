package com.deskita.admin.controller;

import com.deskita.admin.repository.AdviseRepository;
import com.deskita.common.entity.Advise;
import com.deskita.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdviseController {

    @Autowired
    AdviseRepository adviseRepository;

    @GetMapping("advises")
    public String pagingBrand(Model model) {

        List<Advise> advises= (List<Advise>) adviseRepository.findAll();

        model.addAttribute("advises", advises);
        return "advises/advises";
    }
}
