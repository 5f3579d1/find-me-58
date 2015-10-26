package com.sf.sfwxshu.web.service;

import com.sf.sfwxshu.dao.repo.PostRepository;
import com.sf.sfwxshu.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Created by k on 9/16/15.
 */
@Service
@Transactional(readOnly = true)
public class PostService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostRepository repository;

    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Post> findOne(String id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    @Transactional(readOnly = false)
    public Optional<Post> create(Post Post) {
        logger.info("> create");

        Post saved = repository.save(Post);

        logger.info("< create");
        return Optional.ofNullable(saved);
    }

    @Transactional(readOnly = false)
    public Optional<Post> update(Post post) {
        logger.info("> update {}", post.getId());

        Post persisted = repository.findOne(post.getId());
        if (persisted == null) {
            logger.error("Attempted to update a Post, but the entity does not exist.");
            logger.info("< update {}", post.getId());
            throw new NoResultException("Requested Post not found.");
        }

// TODO
//        Field[] fields = persisted.getClass().getDeclaredFields();
//        System.err.println(fields);

        if (!StringUtils.isEmpty(post.getDetail())) {
            persisted.setDetail(post.getDetail());
        }

        if (!StringUtils.isEmpty(post.getName())) {
            persisted.setName(post.getName());
        }

        if (!StringUtils.isEmpty(post.getPhone())) {
            persisted.setPhone(post.getPhone());
        }

        if (!StringUtils.isEmpty(post.getTitle())) {
            persisted.setTitle(post.getTitle());
        }

        post.setUpdateTime(new Date());

        Post updated = repository.save(persisted);

        logger.info("< update {}", updated.getId());
        return Optional.ofNullable(updated);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        logger.info("> delete {}", id);

        repository.delete(id);

        logger.info("< delete {}", id);
    }

    public Optional shake() {
        Collection top1 = repository.findTop1ByOrderByUpdateTimeDesc();
        return top1.parallelStream().findAny();
    }

    @Transactional(readOnly = false)
    public void infoShake(String infoid) {

        Post one = repository.findOne(infoid);
        one.setUpdateTime(new Date());
        repository.saveAndFlush(one);

    }

}
