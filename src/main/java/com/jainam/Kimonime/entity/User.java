package com.jainam.Kimonime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Valid Email Required")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required buddy!")
    @Size(min = 3, message = "I have already kept it at 3, dont go any lower, I know no one is gonna hack your acc but still man..")
    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "enabled")
    private boolean enabled = true;

    public User(){
        this.createdAt = LocalDateTime.now();
    }

    public User(String username, String email, String password){
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }

//    Lets define relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserList> userList = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<UserAnime> userAnimes = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//    Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
// Password
    public void setPassword(String password) {
        this.password = password;
    }

//    CreatedAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


// Enabled
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    UserList
    public Set<UserList> getUserList() {
        return userList;
    }

    public void setUserList(Set<UserList> userList) {
        this.userList = userList;
    }

//    User Anime
    public Set<UserAnime> getUserAnimes() {
        return userAnimes;
    }

    public void setUserAnimes(Set<UserAnime> userAnimes) {
        this.userAnimes = userAnimes;
    }

//    Id
    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
