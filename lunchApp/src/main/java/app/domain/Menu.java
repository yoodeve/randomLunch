package app.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Menu implements Serializable {
    private String name;
    private LocalDateTime createdAt;
    private int wins;

    public Menu(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.wins = 0;
    }

    public void addWins() {
        this.wins++;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getWins() {
        return wins;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "name= \'" + name + "\'" +
                ", createdAt= \'" + createdAt + "\'" +
                ", wins= \'" + wins + "\'" +
                '}';
    }
}