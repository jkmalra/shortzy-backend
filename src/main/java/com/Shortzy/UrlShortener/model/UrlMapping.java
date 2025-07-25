package com.Shortzy.UrlShortener.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 2048) // maybe length is better for urls as text is not portable for db.
    private String originalUrl;

    @Column(unique = true, nullable = true)
    private String shortCode;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int clickCount = 0;

    public UrlMapping(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseClickCount(){
        this.clickCount++;
    }
}
