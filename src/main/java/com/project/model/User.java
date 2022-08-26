package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Data
@Component
public class User {

    private String userId;
    private String userName;
    private String passWord;
    private String email;

    public User() {
        userId = generateId();
    }

    public User (String userName, String passWord, String email) {
        this.userId = generateId();
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    public String generateId() {
        Random random = new Random();
        int n = random.nextInt();
        String hexDecimal = Integer.toHexString(n);
        return hexDecimal;
    }

}
