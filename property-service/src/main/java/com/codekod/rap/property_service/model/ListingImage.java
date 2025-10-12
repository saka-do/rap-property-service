package com.codekod.rap.property_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name = "listing_images")
@Builder @EntityListeners(AuditingEntityListener.class)
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "s3_key", nullable = false)
    private String s3Key;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "listing_id", nullable = false)
    private Listing listing;


}
