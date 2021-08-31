package com.deskita.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.deskita.admin.repository.UserRepository;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@Test
//	public void testCreateUser() {
//		Role roleAdmin = entityManager.find(Role.class, 1);
//		User userAdmin=new User("admindeskita@yopmail.com","admindeskita","Admin","Deskita");
//		userAdmin.addRole(roleAdmin);
//		User savedUser= userRepository.save(userAdmin);
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
//	
//	@Test
//	public void testCreateNewUserTwoRoles() {
//		
//		Role admin=new Role(1);
//		String encodedPassword=passwordEncoder.encode("admindeskita");
//		User user=new User("admindeskita@yopmail.com",encodedPassword,"Admin","Deskita");
//		user.addRole(admin);
//		
//		User savedUser=userRepository.save(user);
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testListAllUsers() {
//		Iterable<User> listUsers= userRepository.findAll();
//		listUsers.forEach(user->System.out.println(user.toString()));
//	}
	
//	@Test
//	public void testUserById() {
//		User user=userRepository.findById(2).get();
//		System.out.println(user.toString());
//	}
	
//	@Test
//	public void testUpdateUserById() {
//		User user=userRepository.findById(2).get();
//		Set<Role> roles=new HashSet<>();
//		Role role=new Role(4);
//		roles.add(role);
//		user.setRoles(roles);
//		userRepository.save(user);
//
//	}
	
//	@Test
//	public void testDeleteUser() {
//		int userId=2;
//		userRepository.deleteById(userId);
//	}
}
