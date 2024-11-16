package com.craigwoodcock.fishingapp.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "venue", nullable = false, length = 100)
    private String venue;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "duration_hours", nullable = false)
    private Integer durationHours;

//    @ManyToMany
//    @JoinTable(name = "angler_session",
//            joinColumns = @JoinColumn(name = "session_id"),
//            inverseJoinColumns = @JoinColumn(name = "angler_id"))
//    private Set<Angler> anglers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Catch> catches = new LinkedHashSet<>();

    @OneToMany(mappedBy = "session")
    private Set<AnglerSession> anglerSessions = new LinkedHashSet<>();

    public Session() {

    }

    public Session(Long id, User user, String venue, LocalDate startDate, Integer durationHours, Set<Catch> catches, Set<AnglerSession> anglerSessions) {
        this.id = id;
        this.user = user;
        this.venue = venue;
        this.startDate = startDate;
        this.durationHours = durationHours;
        this.catches = catches;
        this.anglerSessions = anglerSessions;
    }

    public Set<AnglerSession> getAnglerSessions() {
        return anglerSessions;
    }

    public void setAnglerSessions(Set<AnglerSession> anglerSessions) {
        this.anglerSessions = anglerSessions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

//    public Set<Angler> getAnglers() {
//        return anglers;
//    }
//
//    public void setAnglers(Set<Angler> anglers) {
//        this.anglers = anglers;
//    }

    public Set<Catch> getCatches() {
        return catches;
    }

    public void setCatches(Set<Catch> catches) {
        this.catches = catches;
    }
}