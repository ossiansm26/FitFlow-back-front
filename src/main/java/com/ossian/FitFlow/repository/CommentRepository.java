package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.Comment;
import com.ossian.FitFlow.model.Community;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {

}
