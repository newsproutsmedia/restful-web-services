package com.in28minutes.rest.webservices.restfulwebservices.user.repositories;

import com.in28minutes.rest.webservices.restfulwebservices.user.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {



}
