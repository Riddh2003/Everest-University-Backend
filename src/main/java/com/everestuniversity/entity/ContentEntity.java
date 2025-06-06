package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "content")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID contentId;

	@Column(nullable = false)
	String title;

	@Column(nullable = false)
	String body;

	@Column(nullable = false)
	String author;

	@CreationTimestamp
	LocalDateTime created_at;

	@UpdateTimestamp
	LocalDateTime updated_at;
}