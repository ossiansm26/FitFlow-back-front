package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
