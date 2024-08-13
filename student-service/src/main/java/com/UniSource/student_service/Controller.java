package com.UniSource.student_service;

import com.UniSource.student_service.client.IdentityClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class Controller {
    private final IdentityClient identityClient;
    @GetMapping("/{id}")
    public String test(@PathVariable int id){
        try{
            var identityResponse=this.identityClient.getUserById(id);
            System.out.println(identityResponse.get().getData());
            return "Done";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "Fail";
        }
    }
}
