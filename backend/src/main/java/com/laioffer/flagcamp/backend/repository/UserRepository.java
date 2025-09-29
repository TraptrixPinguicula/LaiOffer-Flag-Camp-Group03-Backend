package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * 根据邮箱查找用户，用于注册时检查用户是否已存在以及登录时验证
     * @param email 用户邮箱
     * @return 包含用户的Optional，如果不存在则为空
     */
    Optional<User> findByEmail(String email);
}