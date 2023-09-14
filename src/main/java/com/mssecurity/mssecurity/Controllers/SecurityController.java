package com.mssecurity.mssecurity.Controllers;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.services.EncryptionService;
import com.mssecurity.mssecurity.services.jwtService;

import com.mssecurity.mssecurity.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("public/security")

public class SecurityController {
    @Autowired
    private jwtService jwtService;
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String token = "";
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser!=null && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword())) ){
            //generar token
            token = jwtService.generateToken(actualUser);

        }else {
            //manejar el problema
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }
}
