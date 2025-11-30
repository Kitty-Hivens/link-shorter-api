package haru.linkshorter.service;

import haru.linkshorter.dto.CreateUrlRequest;
import haru.linkshorter.model.UrlEntity;
import haru.linkshorter.repository.UrlRepository;
import haru.linkshorter.util.Base62Encoder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final Base62Encoder base62Encoder;

    @Transactional
    public String createShortUrl(CreateUrlRequest request) {
        var entity = UrlEntity.builder()
                .originalUrl(request.originalUrl())
                .createdAt(LocalDateTime.now())
                .build();
        
        var savedEntity = repository.save(entity);

        String shortCode = base62Encoder.encode(savedEntity.getId() + 100000L);

        savedEntity.setShortCode(shortCode);
        repository.save(savedEntity);

        return shortCode;
    }

    @Transactional(readOnly = true)
    public String getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));
    }
}
