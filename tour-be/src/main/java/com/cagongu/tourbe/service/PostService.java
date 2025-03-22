package com.cagongu.tourbe.service;

import com.cagongu.tourbe.model.Post;

import java.util.List;


public interface PostService {
    Post createNewPost(Post post);

    Post updatePost(Post post, Long postId);

    List<Post> getAll();

    Post getById(Long id);

    Post delete(Long id);
}
