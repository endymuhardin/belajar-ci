package com.muhardin.endy.belajar.ci.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/host")
    public Map<String, Object> hostInfo(HttpServletRequest request){
        Map<String, Object> info = new HashMap<>();
        info.put("Hostname", request.getLocalName());
        info.put("IP Address Local", request.getLocalAddr());
        info.put("Port Local", request.getLocalPort());
        return info;
    }

    @GetMapping("/session")
    public Map<String, Object> getSessionVariables(HttpSession session){
        Map<String, Object> sessionVariables = new HashMap<>();
        Collections.list(session.getAttributeNames())
                .forEach(name -> {
                    sessionVariables.put(name, session.getAttribute(name));
                });
        return sessionVariables;
    }

    @PostMapping("/session")
    @ResponseStatus(HttpStatus.CREATED)
    public void putSessionVariable(@RequestBody Map<String, String> data, HttpSession session){
        data.keySet()
                .stream()
                .forEach(key -> session.setAttribute(key, data.get(key)));
    }

    @DeleteMapping("/session/{varname}")
    @ResponseStatus(HttpStatus.OK)
    public void putSessionVariable(@PathVariable String varname, HttpSession session){
        session.removeAttribute(varname);
    }
}