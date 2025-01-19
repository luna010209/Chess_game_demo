package com.example.game_web.authentication.user;

import com.example.game_web.authentication.user.dto.UserDto;
import com.example.game_web.authentication.user.dto.UserRequest;
import com.example.game_web.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserDto newUser(@RequestBody UserRequest request){
        return userService.newUser(request);
    }
    @GetMapping
    public UserDto userLogin(){
        return userService.userLogin();
    }
}
