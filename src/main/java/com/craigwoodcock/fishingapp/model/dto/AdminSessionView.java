package com.craigwoodcock.fishingapp.model.dto;

import com.craigwoodcock.fishingapp.model.entity.Session;

import java.time.LocalDate;

public class AdminSessionView {
    private final Long id;
    private final String ownerUsername;
    private final String venue;
    private final LocalDate startDate;
    private final Integer durationHours;

    public AdminSessionView(Session session) {
        this.id = session.getId();
        this.ownerUsername = session.getUser().getUsername();
        this.venue = session.getVenue();
        this.startDate = session.getStartDate();
        this.durationHours = session.getDurationHours();
    }

    public Long getId() {
        return id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getDurationHours() {
        return durationHours;
    }
}