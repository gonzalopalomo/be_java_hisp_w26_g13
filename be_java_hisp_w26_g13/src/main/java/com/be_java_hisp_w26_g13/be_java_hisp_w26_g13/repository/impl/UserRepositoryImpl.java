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
        User aliceMorrison = new User(1, "AliceMorrison");
        User bobSmith = new User(2, "BobSmith");
        User charlyGarcia = new User(3, "CharlieGarcia");
        User daisyJohnson = new User(4, "DaisyJohnson");
        User edwardWilson = new User(5, "EdwardWilson");
        User fionaCampbell = new User(6, "FionaCampbell");
        User goergeMiller = new User(7, "GeorgeMiller");
        User hannahScott = new User(8, "HannahScott");
        User ianPeterson = new User(9, "IanPeterson");
        User juliaEvans = new User(10, "JuliaEvans");
        User kevinBrown = new User(11, "KevinBrown");
        User lauraWhite = new User(12, "LauraWhite");
        User mikeDavis = new User(13, "MikeDavis");
        User noraBaker = new User(14, "NoraBaker");
        User oscarLee = new User(15, "OscarLee");


        aliceMorrison.setFollowed(List.of(bobSmith, charlyGarcia));
        aliceMorrison.setFollowers(List.of(oscarLee));
        bobSmith.setFollowed(List.of(charlyGarcia));
        bobSmith.setFollowers(List.of(aliceMorrison, oscarLee));
        oscarLee.setFollowed(List.of(bobSmith, aliceMorrison));
        oscarLee.setFollowers(List.of(charlyGarcia));
        charlyGarcia.setFollowed(List.of(oscarLee));
        charlyGarcia.setFollowers(List.of(aliceMorrison, bobSmith));

        listUser.add(aliceMorrison);
        listUser.add(bobSmith);
        listUser.add(charlyGarcia);
        listUser.add(daisyJohnson);
        listUser.add(edwardWilson);
        listUser.add(fionaCampbell);
        listUser.add(goergeMiller);
        listUser.add(hannahScott);
        listUser.add(ianPeterson);
        listUser.add(juliaEvans);
        listUser.add(kevinBrown);
        listUser.add(lauraWhite);
        listUser.add(mikeDavis);
        listUser.add(noraBaker);
        listUser.add(oscarLee);
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
