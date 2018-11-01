package com.in28minutes.rest.webservices.restfulwebservices.user.dao;


import com.in28minutes.rest.webservices.restfulwebservices.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack", new Date()));
    }

    // Get a list of all users
    public List<User> findAll() {
        return users;
    }


    // Save a new user
    public User save(User user) {

        if(user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    // Get details about a single user by id
    public User findOne(int id) {
        for(User user:users) {
            if(user.getId()==id) {
                return user;
            }
        }
        return null;
    }

    // Delete a single user by id
    public User deleteById(int id) {

        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

}
