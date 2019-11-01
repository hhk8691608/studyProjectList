package com.mark.dockerproject.api;


import com.mark.dockerproject.model.User;
import com.mark.dockerproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value="/query", method = RequestMethod.GET)
    public User testQuery(@RequestParam("name")String name) {
        return userService.selectUserByName(name);
    }

    @RequestMapping(value="/findAll", method = RequestMethod.GET)
    public List<User> findAll() {
        return userService.selectAllUser();
    }

    @RequestMapping(value="/insert", method = RequestMethod.POST)
    public List<User> testInsert(@RequestBody User user) {
        userService.insertService(user);
        return userService.selectAllUser();
    }

    @RequestMapping(value="/changemoney", method = RequestMethod.GET)
    public List<User> testchangemoney(@RequestParam("sourId")int sourId,
                                      @RequestParam("sourMoney")double sourMoney,
                                      @RequestParam("targetId")int targetId) {
        userService.changemoney(sourId,sourMoney,targetId);
        return userService.selectAllUser();
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public String testDelete(@RequestParam("id")int id) {
        userService.deleteService(id);
        return "OK";
    }



}
