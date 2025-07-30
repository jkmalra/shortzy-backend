package com.Shortzy.UrlShortener.controller;

import com.Shortzy.UrlShortener.dto.ShortUrlResponse;
import com.Shortzy.UrlShortener.dto.UrlRequest;
import com.Shortzy.UrlShortener.model.UrlMapping;
import com.Shortzy.UrlShortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "https://jkmalra.github.io/Shortzy/")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody UrlRequest request) {
        try {
            if (request.getOriginalUrl() == null || request.getOriginalUrl().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            UrlMapping urlMapping = urlShortenerService.createShortUrl(request.getOriginalUrl());
            String shortUrl = "http://localhost:8080/api/r/" + urlMapping.getShortCode();

            ShortUrlResponse response = new ShortUrlResponse(
                    shortUrl,
                    urlMapping.getOriginalUrl(),
                    urlMapping.getClickCount(),
                    urlMapping.getCreatedAt()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/r/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        try {
            UrlMapping urlMapping = urlShortenerService.getUrlMappingByShortCode(shortCode);
            urlShortenerService.incrementClickCount(urlMapping);
            response.sendRedirect(urlMapping.getOriginalUrl());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found");
        }
    }

    @GetMapping("/clicks/{shortCode}")
    public ResponseEntity<Integer> getClickCount(@PathVariable String shortCode) {
        try {
            UrlMapping urlMapping = urlShortenerService.getUrlMappingByShortCode(shortCode);
            return ResponseEntity.ok(urlMapping.getClickCount());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
