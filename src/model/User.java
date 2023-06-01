package model;

import java.util.ArrayList;

import java.util.List;

public class User {

    private String name;

    private List<User> friends;

    private int mutualConnections;

    public User(String name) {

        this.name = name;

        this.friends = new ArrayList<>();

        mutualConnections = 0;

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

    public int getMutualConnections() {
        return mutualConnections;
    }

    public void setMutualConnections(int mutualConnections) {
        this.mutualConnections = mutualConnections;
    }
}