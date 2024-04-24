package com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.impl;

import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.ExceptionDto;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.dto.PostDTO;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.entity.Post;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.IncorrectDateException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.exception.NotFoundException;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IPostRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IProductRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.repository.IUserRepository;
import com.be_java_hisp_w26_g13.be_java_hisp_w26_g13.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IPostRepository postRepository;

    @Override
    public ExceptionDto create(PostDTO postDTO){
        if(userRepository.findById(postDTO.getUserId()) == null){
            throw new NotFoundException("Usuario no encontrado");
        }
        if(productRepository.findById(postDTO.getProduct().getProductId()) == null){
            throw new NotFoundException("Producto no encontrado");
        }
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        Post post = mapper.convertValue(postDTO, Post.class);
        if(post.getDate().isBefore(LocalDate.now())){
            throw  new IncorrectDateException("La fecha ingresada es incorrecta");
        }
        postRepository.create(post);
        return new ExceptionDto("Se creo el post correctamente");
    }
}
