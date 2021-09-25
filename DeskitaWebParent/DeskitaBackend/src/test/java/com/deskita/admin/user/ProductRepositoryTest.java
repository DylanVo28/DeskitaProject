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
}
}