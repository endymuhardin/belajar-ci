package com.muhardin.endy.belajar.ci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired private Environment environment;
    @Autowired(required = false) private GitProperties gitProperties;

    @GetMapping("/host")
    public Map<String, Object> hostInfo(HttpServletRequest request){
        Map<String, Object> info = new TreeMap<>();
        info.put("Hostname", request.getLocalName());
        info.put("IP Address Local", request.getLocalAddr());
        info.put("Port Local", request.getLocalPort());
        info.put("Active Profiles", environment.getActiveProfiles());
        if (gitProperties != null){
            info.put("Git Branch", gitProperties.getBranch());
            info.put("Git Commit ID", gitProperties.getShortCommitId());
            info.put("Git Commit Time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gitProperties.getCommitTime()));
        }
        return info;
    }

    @GetMapping("/session")
    public Map<String, Object> getSessionVariables(HttpSession session){
        Map<String, Object> sessionVariables = new TreeMap<>();
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