package io.ningelschlingel.pca.post.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "likes")
public class LikeEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID likerId;

    @Column(nullable = false)
    private UUID postId;

    // JPA requires a no-arg constructor
    protected LikeEntity() {}

    public LikeEntity(UUID id, UUID likerId, UUID postId) {
        this.id = id;
        this.likerId = likerId;
        this.postId = postId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getLikerId() {
        return likerId;
    }

    public UUID getPostId() {
        return postId;
    }
}

