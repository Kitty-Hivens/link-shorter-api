package haru.linkshorter.dto;

public record UrlResponse(
    String shortUrl,
    String originalUrl
) {}
