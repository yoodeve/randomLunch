package app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Menu implements Serializable {
    private String name;
    private LocalDateTime createdAt;
    private int votes;

    public Menu(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.votes = 0;
    }

    public void addVote() {
        this.votes++;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name= \'" + name + "\'" +
                ", createdAt= \'" + createdAt + "\'" +
                ", votes= \'" + votes + "\'" +
                '}';
    }
}