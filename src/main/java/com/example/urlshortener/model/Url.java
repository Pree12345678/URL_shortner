package com.example.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
    private int accessCount;

    // Getters and Setters
    // ...
}
