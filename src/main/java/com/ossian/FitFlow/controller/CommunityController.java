package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.serviceImpl.CommunityServiceImpl;
import com.ossian.FitFlow.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/community")
public class CommunityController {
    @Autowired
    private CommunityServiceImpl communityService;

    @PostMapping("/{idUser}")
    public ResponseEntity<Community> createCommunity(@RequestBody Community community,
                                                     @PathVariable Long idUser) {
        Community newCommunity = communityService.createCommunity(idUser, community);
        return ResponseEntity.ok(newCommunity);
    }
    @DeleteMapping("/{id}/removeUser/{idUser}")
    public ResponseEntity<Community> removeUserFromCommunity(@PathVariable Long id,
                                                             @PathVariable Long idUser) {
        Community community = communityService.removeUserFromCommunity(id, idUser);
        return ResponseEntity.ok(community);
    }
    @PostMapping("/{id}/addUser/{idUser}")
    public ResponseEntity<Community> addUserToCommunity(@PathVariable Long id,
                                                        @PathVariable Long idUser) {
        Community community = communityService.addUserToCommunity(id, idUser);
        return ResponseEntity.ok(community);
    }
    @GetMapping
    public ResponseEntity<List<Community>> getAllCommunity() {
        return ResponseEntity.ok(communityService.getAllCommunity());
    }


}
