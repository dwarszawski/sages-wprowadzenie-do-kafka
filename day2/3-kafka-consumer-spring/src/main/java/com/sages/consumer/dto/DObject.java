package com.sages.consumer.dto;

import java.time.LocalDate;

import com.sages.consumer.serde.LocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// Ability to customize the serde with jackson annotations
public class DObject {

	@JsonProperty("object_id")
	private String id;

	private String description;

	@JsonProperty("object_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;

	public DObject(String id, String description, LocalDate date) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}