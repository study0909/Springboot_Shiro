package com.yrg.service;

import com.yrg.domain.User;

public interface UserService {

    public User findByName(String name);

    public User findById(Integer id);
}
