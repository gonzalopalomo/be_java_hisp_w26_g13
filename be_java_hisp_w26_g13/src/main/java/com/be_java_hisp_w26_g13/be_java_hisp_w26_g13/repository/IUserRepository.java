package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> getAll();
    User findById(int id);
}
