package com.deskita.admin.controller;

import com.cloudinary.utils.ObjectUtils;
import com.deskita.admin.repository.AdviseRepository;
import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Advise;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class AdviseController {

    @Autowired
    AdviseRepository adviseRepository;

    @Autowired
    ProductService productService;

    @GetMapping("advises")
    public String pagingBrand(Model model) {

        List<Advise> advises= (List<Advise>) adviseRepository.findAll();

        model.addAttribute("advises", advises);
        return "advises/advises";
    }

    @PostMapping("/advises/save")
    public String saveBrand(Advise advise, Model model, HttpServletRequest request) throws IOException {
        adviseRepository.save(advise);
        return "redirect:/advises/edit/"+advise.getId();

    }


    @GetMapping("/advises/edit/{id}")
    public String editAdvise(@PathVariable(name = "id") Integer id, Model model) {
        Advise advise= adviseRepository.findById(id).get();
        Product product=productService.findByID(advise.getProductId());
        model.addAttribute("advise", advise);
        model.addAttribute("product", product);
        return "advises/advise_form";
    }
}
