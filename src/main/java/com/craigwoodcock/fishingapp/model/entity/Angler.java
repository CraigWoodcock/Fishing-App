package com.craigwoodcock.fishingapp.model.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "anglers")
public class Angler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "angler")
    private Set<Catch> catches = new LinkedHashSet<>();

    @OneToMany(mappedBy = "angler")
    private Set<AnglerSession> anglerSessions = new LinkedHashSet<>();

    public Angler(Long id, String name, Set<Catch> catches, Set<AnglerSession> anglerSessions) {
        this.id = id;
        this.name = name;
        this.catches = catches;
        this.anglerSessions = anglerSessions;
    }

    public Angler() {

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