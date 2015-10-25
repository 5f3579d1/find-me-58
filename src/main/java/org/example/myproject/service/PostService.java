package org.example.myproject.service;

import org.example.myproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by k on 9/16/15.
 */
public interface PostService {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findOne(String id);

    Optional<Post> create(Post Post);

    Optional<Post> update(Post post);

    void delete(String id);

    Optional shake();

}
