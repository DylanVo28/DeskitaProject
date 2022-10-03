package com.deskita.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deskita.admin.dto.ProductDetailsDTO;
import com.deskita.admin.service.BrandService;
import com.deskita.admin.service.CategoryService;
import com.deskita.admin.service.ProductDetailService;
import com.deskita.admin.service.ProductImageService;
import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@Controller
public class ProductController {

	Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
			"cloud_name", "tmasolution",
			"api_key", "811712366169655",
			"api_secret", "9UxeBRilDj55JpJ6iV42HopcxTE",
			"secure", true));
	public static int PRODUCT_PER_PAGE = 10;

	@Autowired
	private ProductService service;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
	private ProductImageService productImageService;

	@GetMapping("/products")
	public String listAll(Model model) {

		return "redirect:/products/page/1";
	}

	@GetMapping("/products/page/{currentPage}")
	public String pagingProduct(@PathVariable(name = "currentPage") int currentPage, Model model) {
		List<Product> listProducts = service.pagingProduct(currentPage).getContent();
		Long total = (service.pagingProduct(currentPage).getTotalElements() / PRODUCT_PER_PAGE) + 1;
		System.out.println(listProducts);

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("totalPage", total);
		return "product/products";
	}

	@GetMapping("/products/new")
	public String createProduct(Model model) {
		Product product = new Product();
		ProductDetailsDTO productDetails1 = new ProductDetailsDTO();
		List<Brand> listBrand = brandService.listAll();
		List<Category> listCategories = categoryService.listAll();

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("product", product);
		model.addAttribute("listBrand", listBrand);
		model.addAttribute("listProductDetails", productDetails1.productDetails);
		model.addAttribute("actionSave", "/DeskitaAdmin/products/save");

		return "product/product_form";
	}

	@PostMapping("/products/save")
	public String saveProduct(Product product, Model model, HttpServletRequest request,
			@RequestParam(name = "nameDetail", required = false) String[] nameDetail,
			@RequestParam(name = "valueDetail", required = false) String[] valueDetail,
			@RequestParam(name = "stockDetail", required = false) String[] stockDetail,
			@RequestParam(name = "fileImage", required = false) List<MultipartFile> images,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,

			@RequestParam(name = "detailIds", required = false) String[] detailIds) throws IllegalStateException, IOException {

		List<String> listImage = new ArrayList<>();
		int idx = 0;
		System.out.println("hello");
		for (MultipartFile image : images) {
			if ((!image.isEmpty() && imageIDs == null) ||
					(!image.isEmpty() && idx < imageIDs.length && imageIDs[idx] != null) ||
					(!image.isEmpty() && idx >= imageIDs.length)) {
				String filePath = request.getServletContext().getRealPath("/");
				File file = new File(filePath);
				image.transferTo(file);
				Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
				listImage.add(uploadResult.get("url").toString());
			}

			if (image.isEmpty() && imageIDs[idx] != null) {
				listImage.add(imageNames[idx]);
				System.out.println("image2:" + imageNames[idx]);
			}

			idx++;
		}

		service.saveProduct(product, nameDetail, valueDetail, stockDetail, listImage, imageIDs, detailIds);
		return "redirect:/products";

	}

	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name = "id") Integer id, Model model) {

		Product product = service.getProductById(id);
		List<ProductDetail> listProductDetails = productDetailService.findAll(id);
		List<ProductImage> listProductImages = productImageService.findImageByProductId(id);
		List<Brand> listBrand = brandService.listAll();
		List<Category> listCategories = categoryService.listAll();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("listBrand", listBrand);
		model.addAttribute("product", product);
		model.addAttribute("listProductDetails", listProductDetails);
		model.addAttribute("listProductImages", listProductImages);
		model.addAttribute("actionSave", "/DeskitaAdmin/products/save");

		return "product/product_form";
	}

	@GetMapping("products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		service.deleteProduct(id);
		return "redirect:/products";
	}
}
