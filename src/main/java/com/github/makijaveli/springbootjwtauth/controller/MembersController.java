package com.github.makijaveli.springbootjwtauth.controller;

import com.github.makijaveli.springbootjwtauth.pojo.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MembersController {

    @GetMapping("/hello")
    public ResponseEntity<Msg> hello() {
        return new ResponseEntity<>(new Msg("World"), HttpStatus.OK);
    }

}
