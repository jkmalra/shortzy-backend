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

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody UrlRequest request) {
        UrlMapping urlMapping = urlShortenerService.createShortUrl(request.getOriginalUrl());

        String shortUrl = "http://localhost:8080/api/r/" + urlMapping.getShortCode();

        ShortUrlResponse response = new ShortUrlResponse(
                shortUrl,
                urlMapping.getOriginalUrl(),
                urlMapping.getClickCount(),
                urlMapping.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/r/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        UrlMapping urlMapping = urlShortenerService.getUrlMappingByShortCode(shortCode);

        urlShortenerService.incrementClickCount(urlMapping);

        response.sendRedirect(urlMapping.getOriginalUrl());
    }
    @GetMapping("/clicks/{shortCode}")
    public ResponseEntity<Integer> getClickCount(@PathVariable String shortCode) {
        UrlMapping urlMapping = urlShortenerService.getUrlMappingByShortCode(shortCode);
        return ResponseEntity.ok(urlMapping.getClickCount());
    }
}
