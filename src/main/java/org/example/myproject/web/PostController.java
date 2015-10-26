package com.sf.sfwxshu.web.controller;

import com.sf.sfwxshu.model.Post;
import com.sf.sfwxshu.web.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by k on 9/22/15.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity get(Pageable pageable) {
        logger.info("> get posts");

        PageRequest orderByUpdateTime = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "updateTime"));

        Page<Post> posts = service.findAll(orderByUpdateTime);

        logger.info("< get posts");
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable String id) {
        logger.info("> get Post");

        Optional<Post> post = service.findOne(id);
        if (!post.isPresent()) {
            logger.info("< get Post");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("< get Post");

        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity create(Post Post) {
        logger.info("> post Post");

        Optional<Post> saved = service.create(Post);

        if (!saved.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        logger.info("< post Post");
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity update(Post Post) {
        logger.info("> put Post");

        Optional<Post> updated = service.update(Post);

        logger.info("< put Post");
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        logger.info("> delete Post");

        service.delete(id);

        logger.info("< delete Post");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/shake")
    public ResponseEntity shake() {
        logger.info("> shake Post");

        Optional shake = service.shake();

        logger.info("< shake Post");

        return new ResponseEntity<>(shake.orElseGet(null), HttpStatus.OK);
    }

    @RequestMapping(value = "/infoshake")
    public ResponseEntity infoShake(String infoid) {
        logger.info("> info shake Post");

        service.infoShake(infoid);

        logger.info("< info shake Post");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
