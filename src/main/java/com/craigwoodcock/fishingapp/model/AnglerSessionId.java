package com.craigwoodcock.fishingapp.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AnglerSessionId implements Serializable {
    private Long anglerId;
    private Long sessionId;

    // Constructors, getters, setters, equals, and hashCode methods

    public AnglerSessionId() {}

    public AnglerSessionId(Long anglerId, Long sessionId) {
        this.anglerId = anglerId;
        this.sessionId = sessionId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnglerSessionId that = (AnglerSessionId) o;
        return Objects.equals(anglerId, that.anglerId) &&
                Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anglerId, sessionId);
    }
}