package com.jainam.Kimonime.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animes")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is required")
    @Size(max = 400, message = "Cant be longer than 400 characters")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Size(max = 255, message = "Genre cant be more than 255 chars")
    private String genre;

    @Size(max = 225, message = "No longer than 255")
    private String studio;

    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2030, message = "Year must be before 2030")
    private Integer year;

    @Min(value = 1, message = "Episodes must be at least 1")
    private Integer episodes;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AnimeStatus status;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "mal_id", unique = true)
    private Integer malId; // MyAnimeList ID for API integration

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "mal_score")
    private Double malScore;

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAnime> userAnimes = new HashSet<>();

    public Anime() {
        this.createdAt = LocalDateTime.now();
    }

    public Anime(String title, String description, Integer year, String genre) {
        this();
        this.title = title;
        this.description = description;
        this.year = year;
        this.genre = genre;
    }

//    Get Average rating for this particular anime

    public Double getAverageRating(){
        double sum = this.userAnimes.stream().filter(ua -> ua.getRating() != null).mapToDouble(ua -> ua.getRating()).sum();

        long count = this.userAnimes.stream().filter(ua -> ua.getRating() != null).count();

        if (count != 0) {
            return (double) Math.round((sum / count) * 100) /100;
        } else {
            return 0.0;
        }
    }

    public Long getRatingCount(){
        return this.userAnimes.stream().filter(ua -> ua != null).count();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getStudio() { return studio; }
    public void setStudio(String studio) { this.studio = studio; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getEpisodes() { return episodes; }
    public void setEpisodes(Integer episodes) { this.episodes = episodes; }

    public AnimeStatus getStatus() { return status; }
    public void setStatus(AnimeStatus status) { this.status = status; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getMalId() { return malId; }
    public void setMalId(Integer malId) { this.malId = malId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<UserAnime> getUserAnimes() { return userAnimes; }
    public void setUserAnimes(Set<UserAnime> userAnimes) { this.userAnimes = userAnimes; }

    // Helper method
    public void addUserAnime(UserAnime userAnime) {
        userAnimes.add(userAnime);
        userAnime.setAnime(this);
    }

    public enum AnimeStatus {
        ONGOING, COMPLETED, UPCOMING
    }


}
