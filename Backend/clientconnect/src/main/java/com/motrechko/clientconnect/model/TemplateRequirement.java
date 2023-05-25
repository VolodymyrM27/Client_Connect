package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "template_requirements", schema = "client_connect")
public class TemplateRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "template_id", referencedColumnName = "template_id")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", referencedColumnName = "requirement_id")
    private Requirement requirement;

    @Size(max = 255)
    @Column(name = "requirement_value")
    private String requirementValue;

}