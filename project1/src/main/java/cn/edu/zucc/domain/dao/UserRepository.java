package cn.edu.zucc.domain.dao;

import cn.edu.zucc.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u where u.name=:name")
    User findUser(@Param("name")String name);

    @Query("from User u where u.name like %:name1%")
    List<User> findByNameLike(@Param("name1") String name1);


    @Query("from User u where u.name=:name and u.password=:password")
    User findByNameAndPassword(String name, String password);

    @Query("from User u where u.name=:name")
    User findByName(String name);


}
