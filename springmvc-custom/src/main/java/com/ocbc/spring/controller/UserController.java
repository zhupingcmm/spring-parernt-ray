package com.ocbc.spring.controller;

import com.ocbc.spring.mvc.annotation.RayController;
import com.ocbc.spring.mvc.annotation.RequestMapping;
import com.ocbc.spring.mvc.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RayController
@RequestMapping("user")
public class UserController {

    @RequestMapping("query")
    @ResponseBody
    public Map query(Integer id, String name){
        Map<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name",name);
        return result;
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(Integer id,String name){
        return "OK";
    }
}
