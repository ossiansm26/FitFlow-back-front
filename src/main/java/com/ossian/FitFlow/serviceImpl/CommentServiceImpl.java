package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Comment;
import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.CommentRepository;
import com.ossian.FitFlow.repository.PostRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository comment;
    @Autowired
    private UserRepository user;
    @Autowired
    private PostRepository post;


    public Comment addCommentToPost(Long idComment, Long idPost , Long idUser) {
        Comment comment = this.comment.findById(idComment)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Post post = this.post.findById(idPost)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = this.user.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setUser(user);
        user.getComment().add(comment);
        comment.setPost(post);
        post.getComment().add(comment);

        this.post.save(post);
        return this.comment.save(comment);

    }


    public List<Comment> getAllComment() {
    return comment.findAll();
    }
}
