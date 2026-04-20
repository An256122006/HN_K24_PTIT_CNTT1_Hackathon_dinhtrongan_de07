package org.example.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ArtWorkDto {
    private Long id;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Artist is required")
    private String artist;

    @NotNull(message = "Year is required")
    @Min(value = 0, message = "Year must be positive")
    private Integer year;

    // ✅ Bỏ @NotEmpty vì validate file thủ công trong controller
    private String artImage;

    public ArtWorkDto() {}

    public ArtWorkDto(Long id, String title, String artist, Integer year, String artImage) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.artImage = artImage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getArtImage() { return artImage; }
    public void setArtImage(String artImage) { this.artImage = artImage; }
}