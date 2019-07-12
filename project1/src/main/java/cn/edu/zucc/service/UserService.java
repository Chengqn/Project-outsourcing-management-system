package cn.edu.zucc.service;

import cn.edu.zucc.domain.entity.User;

import java.util.List;


public interface UserService {
    /**
     * 新增用户
     * @param user
     */
     void create(User user);
    /**
     *根据ID修改用户
     * @param user
     */
    void update(User user);
    /**
     * 根据ID删除用户
     * @param id
     */
    void deleteById(Long id);
    /**
     * 获取用户列表
     * @return
     */
    List<User> getAllUsers();
    /**
     * 获取某个用户的信息
     * @param id
     */
    User getUser(Long id);

    /**
     * 通过用户名获取用户
     * @param username
     */
    User getUser(String username);

    /**
     * 通过账号密码获取用户
     * @param username
     * @param password
     */
    User getUser(String username, String password);


    List<User> findByNameLike(String name1);

    List<User> getDeveloperList(String role);
}