package com.Shortzy.UrlShortener.service;

import com.Shortzy.UrlShortener.model.UrlMapping;
import com.Shortzy.UrlShortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;
    private final Random random = new Random();

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";

    public UrlMapping createShortUrl(String originalUrl) {
        // validation
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }
        
        // check if url already exists
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByOriginalUrl(originalUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get();
        }

        // create new mapping with shorter code
        String shortCode = generateUniqueShortCode();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setClickCount(0);

        return urlMappingRepository.save(urlMapping);
    }

    public UrlMapping getUrlMappingByShortCode(String shortCode) {
        if (shortCode == null || shortCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Short code cannot be empty");
        }
        
        return urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
    }

    public void incrementClickCount(UrlMapping urlMapping) {
        if (urlMapping == null) {
            throw new IllegalArgumentException("URL mapping cannot be null");
        }
        
        urlMapping.increaseClickCount();
        urlMappingRepository.save(urlMapping);
    }

    private String generateUniqueShortCode() {
        String shortCode;
        int attempts = 0;
        final int maxAttempts = 10;
        
        do {
            shortCode = generateShortCode();
            attempts++;
            
            // if we try too many times, increase the length
            if (attempts >= maxAttempts) {
                shortCode = generateShortCode(5);
                attempts = 0;
            }
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent());
        
        return shortCode;
    }

    private String generateShortCode() {
        return generateShortCode(4);
    }
    
    private String generateShortCode(int length) {
        StringBuilder shortCode = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            shortCode.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        
        return shortCode.toString();
    }
}
