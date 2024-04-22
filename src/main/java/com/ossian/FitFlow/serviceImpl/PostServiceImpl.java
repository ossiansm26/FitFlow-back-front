package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.CommunityRepository;
import com.ossian.FitFlow.repository.PostRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public Post createPost(Post post,Long idUser,Long idCommunity){
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Community community = communityRepository.findById(idCommunity)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        post.setUser(user);
        post.setCommunity(community);
        user.getPost().add(post);
        community.getPost().add(post);
        userRepository.save(user);
        communityRepository.save(community);
        return postRepository.save(post);
    }

}
