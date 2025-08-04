package com.example.urlshortener.service;

import com.example.urlshortener.dto.UrlRequest;
import com.example.urlshortener.dto.UrlResponse;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public UrlResponse createShortUrl(UrlRequest request) {
        String shortCode = request.getCustomAlias() != null && !request.getCustomAlias().isEmpty()
                ? request.getCustomAlias()
                : UUID.randomUUID().toString().substring(0, 6);

        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        url.setAccessCount(0);

        urlRepository.save(url);

        UrlResponse response = new UrlResponse();
        response.setShortUrl("http://localhost:8080/" + shortCode);
        return response;
    }

    public Optional<Url> getOriginalUrl(String shortCode) {
        Optional<Url> url = urlRepository.findByShortCode(shortCode);
        url.ifPresent(u -> {
            u.setAccessCount(u.getAccessCount() + 1);
            urlRepository.save(u);
        });
        return url;
    }

    public Optional<Url> getStats(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }
}
