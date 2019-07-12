package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u where u.username like %:username%")
    List<User> findUser(@Param("username")String username);

    @Query("from User u where u.username like %:username1%")
    List<User> findByNameLike(@Param("username1") String name1);


    @Query("from User u where u.username=:username and u.password=:password")
    User findByNameAndPassword(String username, String password);

    @Query("from User u where u.username=:username")
    User findByName(String username);

    @Query("from User u where u.role=:role")
    List<User> findDeveloper(String role);
}
