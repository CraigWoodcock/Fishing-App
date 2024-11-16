package com.craigwoodcock.fishingapp.model.entity;

import com.craigwoodcock.fishingapp.model.id.AnglerSessionId;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "angler_session")
public class AnglerSession {
    @EmbeddedId
    private AnglerSessionId id;

    @MapsId("anglerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "angler_id", nullable = false)
    private Angler angler;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    @MapsId("sessionId")
    private Session session;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    public AnglerSessionId getId() {
        return id;
    }

    public void setId(AnglerSessionId id) {
        this.id = id;
    }

    public Angler getAngler() {
        return angler;
    }

    public void setAngler(Angler angler) {
        this.angler = angler;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}