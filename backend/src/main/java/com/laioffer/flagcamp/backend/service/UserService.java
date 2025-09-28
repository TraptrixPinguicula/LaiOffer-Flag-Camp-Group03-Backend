package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.User;
import com.laioffer.flagcamp.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 使用构造函数注入依赖
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(User user) {
        // 检查邮箱是否已被注册
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }
        // 加密密码
        String hashedPassword = passwordEncoder.encode(user.password());
        
        // 因为 record 是不可变的, 我们需要创建一个新的 User 实例来保存
        User userToSave = new User(
                null, // userId 由数据库自动生成
                hashedPassword,
                user.email(),
                user.phoneNum(),
                user.address(),
                user.userIcon(),
                user.nickname(),
                user.notes()
        );
        return userRepository.save(userToSave);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found or password incorrect"));

        if (passwordEncoder.matches(password, user.password())) {
            return user;
        } else {
            throw new RuntimeException("User not found or password incorrect");
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public User updateUser(Long id, User updatedUserInfo) {
        User existingUser = getUserById(id);

        // 使用现有用户的信息创建一个新的 record 实例，并用新信息覆盖
        User userToUpdate = new User(
                existingUser.userId(),
                existingUser.password(), // 密码通常在专门的“修改密码”接口中更新
                updatedUserInfo.email(), // 假设邮箱可以更新
                updatedUserInfo.phoneNum(),
                updatedUserInfo.address(),
                updatedUserInfo.userIcon(),
                updatedUserInfo.nickname(),
                updatedUserInfo.notes()
        );
        return userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}