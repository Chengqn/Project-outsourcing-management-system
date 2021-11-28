package cn.edu.zucc.service.impl;

import cn.edu.zucc.domain.dao.UserRepository;
import cn.edu.zucc.domain.entity.User;
import cn.edu.zucc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    //@CachePut(value = "user",key = "#user.id")
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    //@CachePut(value = "user",key = "#user.id")
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    //@CacheEvict(value = "user",key = "#id")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    //@Cacheable(value = "user", key = "#id")
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    // @Cacheable(value = "user",key = "#name")
    public User getUser(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }
}
