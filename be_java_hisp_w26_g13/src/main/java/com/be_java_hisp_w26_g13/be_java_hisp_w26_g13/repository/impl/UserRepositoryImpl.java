package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private List<User> listUser;

    public UserRepositoryImpl() {
        this.listUser = new ArrayList<>();
        initializeUsers();
    }

    private void initializeUsers() {
        listUser.add(new User(1, "AliceMorrison"));
        listUser.add(new User(2, "BobSmith"));
        listUser.add(new User(3, "CharlieGarcia"));
        listUser.add(new User(4, "DaisyJohnson"));
        listUser.add(new User(5, "EdwardWilson"));
        listUser.add(new User(6, "FionaCampbell"));
        listUser.add(new User(7, "GeorgeMiller"));
        listUser.add(new User(8, "HannahScott"));
        listUser.add(new User(9, "IanPeterson"));
        listUser.add(new User(10, "JuliaEvans"));
        listUser.add(new User(11, "KevinBrown"));
        listUser.add(new User(12, "LauraWhite"));
        listUser.add(new User(13, "MikeDavis"));
        listUser.add(new User(14, "NoraBaker"));
        listUser.add(new User(15, "OscarLee"));
    }

    @Override
    public List<User> getAll() {
        return listUser;
    }

    @Override
    public User findById(int id) {
        return listUser.stream().filter(user -> user.getUserId() == id).findFirst().orElse(null);
    }
}
