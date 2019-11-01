package com.mark.dockerproject.api;


import com.mark.dockerproject.VO.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    private Map<String,Object> dataMap = new HashMap<>();


    @RequestMapping(value="/login", method = RequestMethod.GET)
    public Map<String,Object> login(@RequestParam("id")String id) {
        UserVO userVO = (UserVO) dataMap.get(id);
        Map<String,Object> result = new HashMap<>();
        result.put("userVO",userVO);
        return result;
    }

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public Map<String,Object> add(@RequestParam("username")String username,
                                  @RequestParam("password")String password) {

        String id = new Date().getTime()+"";
        Map<String,Object> result = new HashMap<>();
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setName(username);
        userVO.setPwd(password);
        result.put("userVO",userVO);
        dataMap.put(id,userVO);
        return result;
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public Map<String,Object> update(@RequestBody UserVO reqUserVO) {
        UserVO userVO = (UserVO) dataMap.get(reqUserVO.getId());
        if(userVO == null){
            return null;
        }else{
            dataMap.put(reqUserVO.getId(),reqUserVO);
            userVO = (UserVO) dataMap.get(reqUserVO.getId());
            Map<String,Object> result = new HashMap<>();
            result.put("userVO",userVO);
            return result;
        }

    }


    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public Map<String,Object> delete(@RequestParam("id")String id) {
        UserVO userVO = (UserVO) dataMap.get(id);
        if(userVO == null){
            return null;
        }
        dataMap.put(id,null);
        Map<String,Object> result = new HashMap<>();
        result.put("userVO",userVO);
        return result;
    }




}
