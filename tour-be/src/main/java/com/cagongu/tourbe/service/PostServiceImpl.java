package com.cagongu.tourbe.service;

import com.cagongu.tourbe.enums.StatusAction;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.model.Post;
import com.cagongu.tourbe.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CacheManager cacheManager;


    @Override
    public Post createNewPost(Post post) {
        if (cacheManager.getCache("postListCache") != null) {
            cacheManager.getCache("postListCache").clear();
        }

        return postRepository.save(post);
    }

    @Override
    @CachePut(value = "postListCache", key = "id")
    public Post updatePost(Post post, Long postId) {
        Post updated = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        if(StringUtils.hasText(post.getContentPost())){
            updated.setContentPost(post.getContentPost());
        }

        if(StringUtils.hasText(post.getDescription())){
            updated.setDescription(post.getDescription());
        }

        if(StringUtils.hasText(post.getStatusAction().name())){
            updated.setStatusAction(post.getStatusAction());
        }

        if(StringUtils.hasText(post.getTitlePost())){
            updated.setTitlePost(post.getTitlePost());
        }

        if(StringUtils.hasText(post.getImage())){
            updated.setImage(post.getImage());
        }

        clearCache(postId);

        return postRepository.save(updated);
    }

    private void clearCache(Long tourId) {
        cacheManager.getCache("postCache").evict(tourId);
        cacheManager.getCache("postListCache").clear();
        if (cacheManager.getCache("postCache") != null ){
            cacheManager.getCache("postCache").evict(tourId);
        }

        if (cacheManager.getCache("postListCache") != null) {
            cacheManager.getCache("postListCache").clear();
        }
    }

    @Cacheable(cacheNames = "postListCache")
    @Override
    public List<Post> getAll() {
        return postRepository.findAll().stream()
                .filter(post -> !"DELETE".equals(post.getStatus()))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "postCache", key = "#id")
    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).get();
    }


    @CacheEvict(cacheNames = "postCache", key = "#id")
    @Override
    public Post delete(Long id) {
        Post deletePost = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        deletePost.setStatus(StatusAction.DELETE.name());

        return postRepository.save(deletePost);
    }
}
