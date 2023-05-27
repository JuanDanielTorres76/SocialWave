package model;

import java.util.ArrayList;

import java.util.List;

public class User {
    
    private String name;
    
    private List<User> friends;

    public User(String name) {
        
        this.name = name;
        
        this.friends = new ArrayList<>();
    
    }

    public String getName() {
        
        return name;
    
    }

    public List<User> getFriends() {
        
        return friends;
    
    }

    public void addFriend(User friend) {
        
        friends.add(friend);
    
    }

}