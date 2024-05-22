package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Comments;
import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.CommentRepository;
import com.ossian.FitFlow.repository.CommunityRepository;
import com.ossian.FitFlow.repository.PostRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.CommunityService;
import org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService{
    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private SwaggerWelcomeWebMvc swaggerWelcome;

    public Community removeUserFromCommunity(Long id, Long idUser) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        community.getUserAdded().remove(user);
        user.getCommunityAssociated().remove(community);

        userRepository.save(user);
        return communityRepository.save(community);
    }
    public Community addUserToCommunity(Long id, Long idUser) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        community.getUserAdded().add(user);
        user.getCommunityAssociated().add(community);

        userRepository.save(user);
        return communityRepository.save(community);
    }
public Community createCommunity(Long idUser,Community community) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getCommunityCreated().add(community);
        user.getCommunityAssociated().add(community);
        community.getUserCreated().add(user);
        community.getUserAdded().add(user);
        userRepository.save(user);
        return communityRepository.save(community);
    }
    public List<Community> getAllCommunity(){
        return communityRepository.findAll();
    }

    @Override
    public void deleteCommunity(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        communityRepository.delete(community);
    }

    @Override
    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));
    }



    public Community updateCommunity(Community community, Long id) {
        Community communityUpdated = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        communityUpdated.setName(community.getName());
        communityUpdated.setDescription(community.getDescription());
        return communityRepository.save(communityUpdated);

    }

    public Community addPostToCommunity(Long idCommunity, Long idUser, Post post) {
        Community community = communityRepository.findById(idCommunity)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        post.setCommunity(community);
        community.getPost().add(post);
        return communityRepository.save(community);
    }

    @Override
    public Post addReplyToPost(Long idPost, Long idUser, Comments comment) {
          Post post = postRepository.findById(idPost)
                 .orElseThrow(() -> new RuntimeException("Post not found"));
            User user = userRepository.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            comment.setUser(user);
          post.getComment().add(comment);
          comment.setPost(post);
          commentRepository.save(comment);
          return postRepository.save(post);
    }


}
