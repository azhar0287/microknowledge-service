package com.example.microknowledgesystemservice.api.service;


import com.example.microknowledgesystemservice.comment.Comment;
import com.example.microknowledgesystemservice.microknowledge.MicroKnowledge;
import com.example.microknowledgesystemservice.microknowledge.MicroKnowledgeRepository;
import com.example.microknowledgesystemservice.user.User;
import com.example.microknowledgesystemservice.user.UserRepository;
import io.cucumber.java.an.E;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class MicroKnowledgeSystemService {
    private static final Logger LOGGER = LogManager.getLogger(MicroKnowledgeSystemService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    MicroKnowledgeRepository microKnowledgeRepository;

    Boolean doLogin(String userId, String password) {
        try {
            if(!userId.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
                User user = userRepository.getUserByCredentials(userId, password);
                if(user != null) {
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

    List<User> searchUserByName(String name) {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.getUserByName(name);
            LOGGER.info("User list: "+users.size());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return users;
    }

    public List<MicroKnowledge> listMicroKnowledgeOfUser(String userId) {
        List<MicroKnowledge> microKnowledge = new ArrayList<>();
        try {
            User user = userRepository.getUserByUserId(userId);
            if(user != null) {
                microKnowledge = microKnowledgeRepository.getMicroKnowledgeByContainedUser(user.getUuid());
                LOGGER.info("MicroKnowledge list size "+microKnowledge.size());
            }

        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        return microKnowledge;
    }

    public List<MicroKnowledge> searchMicroKnowledge(String keywords) {
        List<MicroKnowledge> microKnowledge = new ArrayList<>();
        try {
            microKnowledge = microKnowledgeRepository.getMicroKnowledgeByKeywords(keywords);
            LOGGER.info("MicroKnowledge list size "+microKnowledge.size());

        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        return microKnowledge;
    }

    public MicroKnowledge viewMicroKnowledge(String uuid) {
        MicroKnowledge microKnowledge = new MicroKnowledge();
        try {
            LOGGER.info("uuid: "+uuid);
            microKnowledge = microKnowledgeRepository.getMicroKnowledgeByUUid(uuid);
            if( microKnowledge != null) {
                return microKnowledge;
            }
            else {
                throw new RuntimeException("No data found againts provided UUID");
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        return microKnowledge;
    }

    boolean starMicroKnowledge() {
        return false;
    }



    boolean createComment(String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUuid(UUID.randomUUID().toString());
            MicroKnowledge mk = new MicroKnowledge();
            User user = new User();
//            user.se

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }


}
