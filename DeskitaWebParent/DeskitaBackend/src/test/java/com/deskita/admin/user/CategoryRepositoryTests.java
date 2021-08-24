package com.deskita.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.deskita.admin.repository.CategoryRepository;
import com.deskita.admin.repository.UserRepository;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testCategory() {
		Category category=new Category("May anh");
		categoryRepository.save(category);
		User user=userRepository.findById(4).get();
		System.out.println(user);
	}
}
