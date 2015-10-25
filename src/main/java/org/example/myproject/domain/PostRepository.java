package org.example.myproject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, String>, JpaSpecificationExecutor {

    Collection findTop10ByOrderByCreateTimeDesc();

}
