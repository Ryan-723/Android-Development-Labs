package com.example.task6;

import java.io.Serializable;

public class User implements Serializable {

    // Declare member variables
    String UserId;
    String EmailId;

    // Initializing User object with userId and emailId
    public User(String userId, String emailId) {
        UserId = userId;
        EmailId = emailId;
    }
}
