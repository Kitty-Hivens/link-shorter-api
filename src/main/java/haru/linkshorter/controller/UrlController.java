package haru.linkshorter.controller;

import haru.linkshorter.dto.CreateUrlRequest;
import haru.linkshorter.dto.UrlResponse;
import haru.linkshorter.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostMapping("/api/v1/shorten")
    public ResponseEntity<UrlResponse> shorten(@Valid @RequestBody CreateUrlRequest request) {
        String shortCode = urlService.createShortUrl(request);
        String fullShortUrl = baseUrl + shortCode;
        
        return ResponseEntity.ok(new UrlResponse(fullShortUrl, request.originalUrl()));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        
        return ResponseEntity.status(HttpStatus.FOUND) // 302 Redirect
                .location(URI.create(originalUrl))
                .build();
    }
}
