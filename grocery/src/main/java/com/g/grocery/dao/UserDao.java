package com.g.grocery.dao;
import com.g.grocery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(@Param("email") String email);

    @Query("select u from User u where u.role='user'")
    List<User> getAllUser();

    @Query("select u.email from User u where u.role='admin'")
    List<String> getAllAdmin();


}
