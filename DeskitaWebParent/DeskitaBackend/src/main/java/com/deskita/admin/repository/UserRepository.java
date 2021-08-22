package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.deskita.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
