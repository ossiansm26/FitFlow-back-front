package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.serviceImpl.CommunityServiceImpl;
import com.ossian.FitFlow.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:8080")
public class CommunityController {
    @Autowired
    private CommunityServiceImpl communityService;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunity() {
        return ResponseEntity.ok(communityService.getAllCommunity());
    }
    @GetMapping("/getCommunityById/{id}")
    public ResponseEntity<Community> getCommunityById(@PathVariable Long id) {
        Community community = communityService.getCommunityById(id);
        return ResponseEntity.ok(community);
    }
    @DeleteMapping("/{id}/removeUser/{idUser}")
    public ResponseEntity<Community> removeUserFromCommunity(@PathVariable Long id,
                                                             @PathVariable Long idUser) {
        Community community = communityService.removeUserFromCommunity(id, idUser);
        return ResponseEntity.ok(community);
    }
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/addUser/{idUser}")
    public ResponseEntity<Community> addUserToCommunity(@PathVariable Long id,
                                                        @PathVariable Long idUser) {
        Community community = communityService.addUserToCommunity(id, idUser);
        return ResponseEntity.ok(community);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Community> updateCommunity(@RequestBody Community community,
                                                     @PathVariable Long id) {
        Community communityUpdated = communityService.updateCommunity(community, id);
        return ResponseEntity.ok(communityUpdated);
    }

    @PostMapping("/create/{idUser}")
    public ResponseEntity<Community> createCommunity(@RequestBody Community community,
                                                     @PathVariable Long idUser) {

        Community newCommunity = communityService.createCommunity(idUser, community);

        return ResponseEntity.ok(newCommunity);
    }
    @PostMapping("/addPost/{idCommunity}/{idUser}")
    public ResponseEntity<Community> addPostToCommunity(@PathVariable Long idCommunity,
                                                        @PathVariable Long idUser,
                                                        @RequestBody Post post) {
        Community community = communityService.addPostToCommunity(idCommunity, idUser, post);
        return ResponseEntity.ok(community);
    }





}
