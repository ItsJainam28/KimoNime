package com.jainam.Kimonime.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_animes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "anime_id", "user_list_id"}))
public class UserAnime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Rating must be between 1 and 10")
    @Max(value = 10, message = "Rating must be between 1 and 10")
    private Integer rating;

    @Min(value = 0, message = "Episodes watched cannot be negative")
    @Column(name = "episodes_watched")
    private Integer episodesWatched = 0;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "started_date")
    private LocalDate startedDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // User who owns this entry
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Anime being tracked
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    // List this anime belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_list_id", nullable = false)
    private UserList userList;

    public UserAnime() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UserAnime(User user, Anime anime, UserList userList) {
        this();
        this.user = user;
        this.anime = anime;
        this.userList = userList;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Calculate progress percentage
    public Double getProgressPercentage() {
        if (anime.getEpisodes() == null || anime.getEpisodes() == 0) {
            return 0.0;
        }

        return Math.min(100.0, (episodesWatched * 100.0) / anime.getEpisodes());
    }

    // Check if anime is completed by user
    public boolean isCompleted() {
        return anime.getEpisodes() != null &&
                episodesWatched != null &&
                episodesWatched.equals(anime.getEpisodes());
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public Integer getEpisodesWatched() { return episodesWatched; }
    public void setEpisodesWatched(Integer episodesWatched) { this.episodesWatched = episodesWatched; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getStartedDate() { return startedDate; }
    public void setStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public LocalDate getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Anime getAnime() { return anime; }
    public void setAnime(Anime anime) { this.anime = anime; }

    public UserList getUserList() { return userList; }
    public void setUserList(UserList userList) { this.userList = userList; }
}