package com.example.microknowledgesystemservice.api.service;


import com.example.microknowledgesystemservice.comment.Comment;
import com.example.microknowledgesystemservice.comment.CommentRepository;
import com.example.microknowledgesystemservice.microknowledge.MicroKnowledge;
import com.example.microknowledgesystemservice.microknowledge.MicroKnowledgeRepository;
import com.example.microknowledgesystemservice.user.User;
import com.example.microknowledgesystemservice.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MicroKnowledgeSystem {
    private static final Logger LOGGER = LogManager.getLogger(MicroKnowledgeSystem.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    MicroKnowledgeRepository microKnowledgeRepository;
    @Autowired
    CommentRepository commentRepository;

    public User currentUser;
    public MicroKnowledge currentMicroKnowledge;

    Boolean doLogin(String userId, String password) {
        try {
            if(!userId.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                User user = userRepository.getUserByCredentials(userId, password);
                if(user != null) {
                    currentUser = user;
                    LOGGER.info("User has successfully logged in");
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    boolean starMicroKnowledge() {
        try {
            //pre condition
            if(this.currentUser != null && this.currentMicroKnowledge !=null) {
                int prevStarNumber = this.currentMicroKnowledge.getStarNumber();
                this.currentMicroKnowledge.setStarNumber(prevStarNumber + 1);
                Set<MicroKnowledge> mk = new HashSet<>();
                mk.add(this.currentMicroKnowledge);
                currentUser.setStaredMicroKnowledge(mk);
                return true;
            }
            else {
                LOGGER.info("Either current user or currentMK dont exists");
                return false;
            }
        } catch (Exception e) {
            //need to throw Exception here
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    boolean writeComment(String content) {
        try {
            if(this.currentUser != null && this.currentMicroKnowledge !=null) {
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setUuid(UUID.randomUUID().toString());
                Set<Comment> comments  = new HashSet<>();
                comments.add(comment);
                this.currentUser.setReaderToComment(comments);
                this.currentMicroKnowledge.setContainedComment(comments);
                comment.setCommentToMicroKnowledge(this.currentMicroKnowledge);
                comment.setCommentToReader(this.currentUser);
                commentRepository.save(comment);
                return true;
            } else {
                LOGGER.info("Either current user or currentMK dont exists");
                return false;
            }

        } catch (Exception e) {
            //need to throw Exception here
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    Set<MicroKnowledge> listStaredMicroKnowledge() {
        try {
            if(this.currentUser != null) {
                return this.currentUser.getStaredMicroKnowledge();
            }
            else {
                LOGGER.info("Either current user or currentMK dont exists");
                //need to throw Exception here
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
