package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Product;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.User;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IPostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class PostRepositoryImpl implements IPostRepository {

    private List<Post> listPost;

    @Autowired
    private ProductRepositoryImpl productRepository;

    public PostRepositoryImpl() {
        this.listPost = new ArrayList<>();
    }

    // Agregado para respetar el orden temporal de la carga de datos dummy de los Productos que popularan los Posts
    @PostConstruct
    public void init() {
        initializePosts();
    }

    private void initializePosts() {
        List<Product> products = productRepository.getAllProducts();
        Random random = new Random();
        LocalDate startDate = LocalDate.of(2023, 1, 1);

        for (int i = 0; i < 15; i++) {
            int userId = random.nextInt(100) + 1;
            LocalDate date = startDate.plusDays(random.nextInt(365));
            Product product = products.get(i % products.size());
            int category = random.nextInt(5) + 1;
            double price = 100.0 + (random.nextDouble() * 1000.0);

            Post post = new Post(userId, date, product, category, price);
            listPost.add(post);
        }
    }
}
