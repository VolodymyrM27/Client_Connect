package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "templates", schema = "client_connect")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "is_public")
    private Boolean isPublic;

    @OneToMany(mappedBy = "template")
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "template")
    private Set<TemplateRequirement> templateRequirements = new LinkedHashSet<>();

    @OneToMany(mappedBy = "template")
    private Set<UserTemplateHistory> userTemplateHistories = new LinkedHashSet<>();

}