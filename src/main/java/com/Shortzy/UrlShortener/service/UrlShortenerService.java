package com.Shortzy.UrlShortener.service;

import com.Shortzy.UrlShortener.model.UrlMapping;
import com.Shortzy.UrlShortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMapping createShortUrl(String originalUrl) {
        // 🔁 Check if original URL already exists
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByOriginalUrl(originalUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get(); // ✅ Return existing mapping
        }

        // 🔧 Create new mapping if not exists
        String shortCode = generateShortCode();

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortCode(shortCode);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setClickCount(0);

        return urlMappingRepository.save(urlMapping);
    }

    public UrlMapping getUrlMappingByShortCode(String shortCode) {
        return urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
    }

    public void incrementClickCount(UrlMapping urlMapping) {
        urlMapping.increaseClickCount();
        urlMappingRepository.save(urlMapping);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
