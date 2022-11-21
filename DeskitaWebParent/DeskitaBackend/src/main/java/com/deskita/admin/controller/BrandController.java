package com.deskita.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deskita.admin.service.BrandService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class BrandController {
	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			"cloud_name", "tmasolution",
			"api_key", "811712366169655",
			"api_secret", "9UxeBRilDj55JpJ6iV42HopcxTE",
			"secure", true));
	@Autowired
	private BrandService service;

	@GetMapping("/brands")
	public String listAll(Model model) {
		return "redirect:/brands/page/1";
	}

	@GetMapping("/brands/page/{currentPage}")
	public String pagingBrand(@PathVariable(name = "currentPage") int currentPage, Model model) {
		List<Brand> allBrands = service.listAll();
		int totalPage = allBrands.size() / 10 + 1;
		List<Brand> listBrands = service.pagingBrand(currentPage);
		for (Brand brand : listBrands) {
			System.out.println("image_brand" + brand.getLogo());
		}
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("totalPage", totalPage);
		return "brand/brands";
	}

	@GetMapping("/brands/new")
	public String createBrand(Model model) {
		Brand brand = new Brand();
		model.addAttribute("brand", brand);
		model.addAttribute("actionSave", "/DeskitaAdmin/brands/save");

		return "brand/brand_form";
	}

	@PostMapping("/brands/save")
	public String saveBrand(Brand brand, Model model, HttpServletRequest request,
			@RequestParam(name = "fileImage", required = false) MultipartFile image) throws IOException {

		String filePath = request.getServletContext().getRealPath("/");
		File file = new File(filePath);
		image.transferTo(file);
		Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		brand.setLogo(uploadResult.get("url").toString());
		model.addAttribute("brand", brand);
		model.addAttribute("actionSave", "/DeskitaAdmin/brands/save");
		service.saveBrand(brand);

		return "redirect:/brands";

	}

	@PostMapping("/brands/save/{id}")
	public String saveBrandById(@PathVariable(name = "id") Integer id, Model model, HttpServletRequest request,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "fileImage", required = false) MultipartFile image) throws IOException {
		Brand brand = service.getBrandById(id);

		if (image.isEmpty()) {
			brand.setName(name);
			model.addAttribute("brand", brand);
			model.addAttribute("actionSave", "/DeskitaAdmin/brands/save/");
			service.saveBrand(brand);
			return "redirect:/brands";
		} else {
			String filePath = request.getServletContext().getRealPath("/");
			File file = new File(filePath);
			image.transferTo(file);
			Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
			brand.setLogo(uploadResult.get("url").toString());
			brand.setName(name);
			model.addAttribute("brand", brand);
			model.addAttribute("actionSave", "/DeskitaAdmin/brands/save/");
			service.saveBrand(brand);
			return "redirect:/brands";
		}

	}

	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Integer id, Model model) {
		System.out.println(id);
		Brand brand = service.getBrandById(id);
		model.addAttribute("brand", brand);
		model.addAttribute("actionSave", "/DeskitaAdmin/brands/save/" + brand.getId());
		return "brand/brand_form";
	}

}
