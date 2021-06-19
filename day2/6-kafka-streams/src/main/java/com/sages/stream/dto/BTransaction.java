package com.sages.stream.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

// Ability to customize the serde with jackson annotations
public class BTransaction {

    @JsonProperty("transaction_id")
    private String id;

    private Double value;

    @JsonProperty("object_date")
    private LocalDateTime timestamp;

    private String type;

    public BTransaction(ATransaction transaction, String type) {
        super();
        this.id = transaction.getId();
        this.value = transaction.getValue();
        this.timestamp = transaction.getTimestamp();
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
