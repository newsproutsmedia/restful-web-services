package com.in28minutes.rest.webservices.restfulwebservices.user.repositories;

import com.in28minutes.rest.webservices.restfulwebservices.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {



}
