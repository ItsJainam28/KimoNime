package com.jainam.Kimonime.entity;

import com.jainam.Kimonime.entity.User;
import com.jainam.Kimonime.entity.UserAnime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_lists",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
public class UserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "List name is required")
    @Size(max = 100, message = "List name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "is_default")
    private boolean isDefault = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Owner of the list
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Anime entries in this list
    @OneToMany(mappedBy = "userList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAnime> userAnimes = new HashSet<>();

    public UserList() {
        this.createdAt = LocalDateTime.now();
    }

    public UserList(String name, User user, boolean isDefault) {
        this();
        this.name = name;
        this.user = user;
        this.isDefault = isDefault;
    }

    // Get count of anime in this list
    public int getAnimeCount() {
        return userAnimes.size();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Set<UserAnime> getUserAnimes() { return userAnimes; }
    public void setUserAnimes(Set<UserAnime> userAnimes) { this.userAnimes = userAnimes; }

    // Helper methods
    public void addUserAnime(UserAnime userAnime) {
        userAnimes.add(userAnime);
        userAnime.setUserList(this);
    }

    public void removeUserAnime(UserAnime userAnime) {
        userAnimes.remove(userAnime);
        userAnime.setUserList(null);
    }

    // Default list names as constants
    public static final String WATCHING = "Watching";
    public static final String COMPLETED = "Completed";
    public static final String PLAN_TO_WATCH = "Plan to Watch";
    public static final String DROPPED = "Dropped";
    public static final String ON_HOLD = "On Hold";
}