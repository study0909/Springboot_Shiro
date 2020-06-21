package com.yrg.mapper;

import com.yrg.domain.User;

public interface UserMapper {

    public User findByName(String name);

    public User findById(Integer id);
}
