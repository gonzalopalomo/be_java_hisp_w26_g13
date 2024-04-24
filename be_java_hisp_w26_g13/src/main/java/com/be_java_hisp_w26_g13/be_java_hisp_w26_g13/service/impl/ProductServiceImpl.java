package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private List<Post> orderByDate(List<Post> posts, String order){
        if(order.equals("date_asc")){
            posts.sort(Comparator.comparing(Post::getDate));
        } else if(order.equals("date_desc")){
            posts.sort(Comparator.comparing(Post::getDate).reversed());
        }
        return posts;
    }
}
