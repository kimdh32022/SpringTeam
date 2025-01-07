package com.busanit501.bootproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MatchingRoom matchingRoom;

    @ManyToOne
    private User reviewer;

    @ManyToOne
    private User reviewed;

    private Float rating;
    private String comment;
}