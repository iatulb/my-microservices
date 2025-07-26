package com.eazybytes.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto() {
}
