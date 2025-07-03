package com.Mahima.app.model;

import jakarta.persistence.*;
import java.time.LocalDate; // Use LocalDate as per your controller's use of LocalDate.parse(travelDate)

@Entity
@Table(name = "diary_entries") // Adjust table name if different
public class DiaryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Stores the ID of the user who created the entry

    private String title;

    @Lob // For potentially large text content
    @Column(name = "content", columnDefinition = "TEXT") // TEXT is typical for long strings in PostgreSQL
    private String content;

    // ⭐ CRITICAL: Field name and its getter/setter must match usage in controller ⭐
    // Controller uses entry.getDate(), so field should be 'date'
    private LocalDate date; // Matches controller's entry.setDate(LocalDate.parse(travelDate)); and entry.getDate()

    @Lob // For storing image data (BLOB)
    @Column(name = "image") // You might need a specific columnDefinition for BLOB, e.g., "BYTEA" for PostgreSQL
    private byte[] image; // Stores image as byte array

    // Constructors
    public DiaryEntry() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    // ⭐ CRITICAL: Getter/Setter for 'date' field ⭐
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}