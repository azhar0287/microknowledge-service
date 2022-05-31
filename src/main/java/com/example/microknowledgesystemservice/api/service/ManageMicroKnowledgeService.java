package com.example.microknowledgesystemservice.api.service;

import com.example.microknowledgesystemservice.microknowledge.MicroKnowledge;
import com.example.microknowledgesystemservice.microknowledge.MicroKnowledgeRepository;
import com.example.microknowledgesystemservice.user.User;
import com.example.microknowledgesystemservice.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManageMicroKnowledgeService {

    private static final Logger LOGGER = LogManager.getLogger(ManageMicroKnowledgeService.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    MicroKnowledgeRepository microknowledgeRepository;

    public boolean createMicroKnowledge(String uuid, String keywords, String content, String userId) {
        try {
            MicroKnowledge oldMk = microknowledgeRepository.getMicroKnowledgeByUUid(uuid);
            if(oldMk == null) { //If no object exits against provided unique id (uuid)
                MicroKnowledge mk = new MicroKnowledge();
                mk.setKeywords(keywords);
                mk.setContent(content);
                mk.setUuid(UUID.randomUUID().toString());
                mk.setStarNumber(0);

                /*Setting user instances*/
                User user = userRepository.getUserByUserId(userId); //Assuming current user from id
                user.setMicroKnowledge(mk);
                mk.setBelongedUser(user);
                mk.setContainedUser(user);
                microknowledgeRepository.save(mk);
                return true;
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    boolean modifyMicroKnowledge(String uuid, String keywords, String content) {
        try {
            MicroKnowledge oldMk = microknowledgeRepository.getMicroKnowledgeByUUid(uuid);
            if(oldMk != null) {
                oldMk.setContent(content);
                oldMk.setKeywords(keywords);
                //here StarNumber will remain previous one as we are not setting it here
                return true;
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
