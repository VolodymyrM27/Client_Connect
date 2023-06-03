package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NamedEntityGraph(
        name = "template-with-all-fields",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("category"),
                @NamedAttributeNode("reviews"),
                @NamedAttributeNode(value = "templateRequirements", subgraph = "requirement-field"),
                @NamedAttributeNode("userTemplateHistories")
        }, subgraphs = {
        @NamedSubgraph(
                name = "requirement-field",
                attributeNodes = {
                        @NamedAttributeNode("template"),
                        @NamedAttributeNode("requirement")
                }
        )
}
)
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
    private Long id;

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}