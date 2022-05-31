package com.example.microknowledgesystemservice.api.controller;

import com.example.microknowledgesystemservice.api.service.ManageMicroKnowledgeService;
import com.example.microknowledgesystemservice.api.service.ManageUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/MicroKnowledge"})
@CrossOrigin("*")
public class MicroKnowledgeController {
    private static final Logger LOGGER = LogManager.getLogger(MicroKnowledgeController.class);

    final ManageUserService manageUserService;
    final ManageMicroKnowledgeService manageMicroKnowledgeService;

    public MicroKnowledgeController(ManageMicroKnowledgeService manageMicroKnowledgeService, ManageUserService manageUserService) {
        this.manageMicroKnowledgeService = manageMicroKnowledgeService;
        this.manageUserService = manageUserService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public boolean createMicroKnowledge(@RequestParam("keywords") String keywords, @RequestParam("content") String content,
                                        @RequestParam("userId") String userId, @RequestParam("uuid") String uuid) {
        LOGGER.info("Request received for MK creation");
        return manageMicroKnowledgeService.createMicroKnowledge(uuid, keywords, content, userId);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public boolean createUser(@RequestParam("name") String name, @RequestParam("password") String password,
                              @RequestParam("uuid") String uuid, @RequestParam("userId") String userId) {
        LOGGER.info("Request received for user creation");
        return manageUserService.createUser(uuid, name, userId, password);
    }

    @RequestMapping(value = "/writeComment", method = RequestMethod.POST)
    @ResponseBody
    public boolean writeComment(@RequestParam("name") String name, @RequestParam("password") String password,
                              @RequestParam("uuid") String uuid) {
        LOGGER.info("Request received for user creation");
        //return manageUserService.createUser(uuid, name, password);
        return false;
    }
}
