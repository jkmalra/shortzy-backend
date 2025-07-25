package com.Shortzy.UrlShortener.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShortUrlResponse {
    private String shortUrl;
    private String originalUrl;
    private int clickCount;
    private LocalDateTime createdAt;

    public ShortUrlResponse(String shortUrl, String originalUrl, int clickCount, LocalDateTime createdAt) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }
}
