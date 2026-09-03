package com.helpdeskpro.web.dto;

public record LoginResponse(String token, String type, long expiresIn) {
}
