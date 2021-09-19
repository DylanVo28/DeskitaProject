package com.deskita.admin.controller;

import java.util.ArrayList;
import java.util.List;

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

<<<<<<< HEAD
=======
import com.deskita.admin.dto.ProductDetailsDTO;
import com.deskita.admin.service.BrandService;
import com.deskita.admin.service.CategoryService;
import com.deskita.admin.service.ProductDetailService;
import com.deskita.admin.service.ProductImageService;
>>>>>>> 806736dcb21d83f4adf57da5e142b529fff07b49
import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;

@Controller
public class ProductController {
	
	public static int PRODUCT_PER_PAGE=10;
	
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
	public String pagingProduct(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<Product> listProducts=service.pagingProduct(currentPage).getContent();
		Long total=(service.pagingProduct(currentPage).getTotalElements()/PRODUCT_PER_PAGE) +1;
		System.out.println(listProducts);
		
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("totalPage",total);
		return "product/products";
	}
	
	
	@GetMapping("/products/new")
	public String createProduct(Model model) {
		Product product=new Product();
<<<<<<< HEAD
		ProductDetail productDetails=new ProductDetail();
=======
		ProductDetailsDTO productDetails=new ProductDetailsDTO();
		List<Brand> listBrand=brandService.listAll();
		List<Category> listCategories=categoryService.getListCategoryIsEnabled();
>>>>>>> 806736dcb21d83f4adf57da5e142b529fff07b49
		
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("product",product);
		model.addAttribute("listBrand",listBrand);
		model.addAttribute("listProductDetails",productDetails.productDetails);
		model.addAttribute("actionSave","/DeskitaAdmin/products/save");
		
		return "product/product_form";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product,Model model,HttpServletRequest request,
			@RequestParam(name="nameDetail",required=false) String[] nameDetail,
			@RequestParam(name="valueDetail",required=false) String[] valueDetail,
			@RequestParam(name="stockDetail",required=false) String[] stockDetail,
			@RequestParam(name="fileImage",required=false) List<MultipartFile> images
			) {
	
		List<String> listImage=new ArrayList<>();
		for(MultipartFile image:images) {
			
			listImage.add(StringUtils.cleanPath(image.getOriginalFilename()));
		}
//				
			service.saveProduct(product, nameDetail,valueDetail, stockDetail,listImage);
			return "redirect:/products";
//		
	}
		
	@PostMapping("/products/save/{id}")
	public String saveProductById(Product product,Model model) {
			List<ProductDetail> listProductDetails= service.listProductDetails();
			model.addAttribute("product",product);
			model.addAttribute("listProductDetails",listProductDetails);
			model.addAttribute("actionSave","/DeskitaAdmin/products/save/"+product.getId());		
			service.saveProduct(product);
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name="id") Integer id,Model model) {
		
		Product product=service.getProductById(id);
		List<ProductDetail> listProductDetails=productDetailService.findAll(id);
		List<ProductImage> listProductImages=productImageService.findImageByProductId(id);
		List<Brand> listBrand=brandService.listAll();
		List<Category> listCategories=categoryService.getListCategoryIsEnabled();
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("listBrand",listBrand);
		model.addAttribute("product",product);
		model.addAttribute("listProductDetails",listProductDetails);
		model.addAttribute("listProductImages",listProductImages);
		model.addAttribute("actionSave","/DeskitaAdmin/products/save");
		return "product/product_form";
	}
	
	@GetMapping("products/delete/{id}")
	public String deleteProduct(@PathVariable(name="id") Integer id) {
		service.deleteProduct(id);
		return "redirect:/products";
	}
}
