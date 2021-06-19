package com.sages.stream.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

// Ability to customize the serde with jackson annotations
public class ATransaction {

    @JsonProperty("transaction_id")
    private String id;

    private Double value;

    @JsonProperty("object_date")
    private LocalDateTime timestamp;

    public ATransaction(String id, Double value, LocalDateTime timestamp) {
        super();
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
