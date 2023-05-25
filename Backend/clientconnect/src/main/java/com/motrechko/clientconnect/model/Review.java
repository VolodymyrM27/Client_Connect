package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "reviews", schema = "client_connect")
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @Column(name = "review_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review_text")
    private String reviewText;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

}