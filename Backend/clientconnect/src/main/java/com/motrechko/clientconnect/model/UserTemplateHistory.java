package com.motrechko.clientconnect.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "user_template_history", schema = "client_connect")
@NoArgsConstructor
@AllArgsConstructor
public class UserTemplateHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "used_at")
    private Instant usedAt;

}