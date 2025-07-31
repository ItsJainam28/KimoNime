package com.jainam.Kimonime.repository;

import com.jainam.Kimonime.entity.UserAnime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnimeRepository  extends JpaRepository<UserAnime, Long> {
}
