package com.deskita.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import com.deskita.admin.repository.ProductRepository;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Test
		public void testCreateRestProduct() {
			Product productPC=new Product(1,"may-tinh-ban", "may-tinh-ban", "ok", "ngon");
			Product productLaptop=new Product(2,"may-tinh-xach-tay", "may-tinh-xach-tay", "okok", "ngon-lanh");
			Product productAccessory=new Product(3,"linh-kien-dien-tu", "linh-kien-dien-tu", "okokok", "ngon-lanh-qua");
			
			productRepository.save(productPC);
			productRepository.save(productLaptop);
			productRepository.save(productAccessory);
}
}