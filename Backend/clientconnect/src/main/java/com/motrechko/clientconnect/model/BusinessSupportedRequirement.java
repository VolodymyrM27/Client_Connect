package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@Table(name = "business_supported_requirements", schema = "client_connect")
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSupportedRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

}