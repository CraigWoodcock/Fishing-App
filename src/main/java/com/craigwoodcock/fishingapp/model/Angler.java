package com.craigwoodcock.fishingapp.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "anglers")
public class Angler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(name = "angler_session",
            joinColumns = @JoinColumn(name = "angler_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    private List<Session> sessions = new LinkedList();

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "angler")
    private Set<Catch> catches = new LinkedHashSet<>();

    @OneToMany(mappedBy = "angler")
    private Set<AnglerSession> anglerSessions = new LinkedHashSet<>();

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

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Catch> getCatches() {
        return catches;
    }

    public void setCatches(Set<Catch> catches) {
        this.catches = catches;
    }

}