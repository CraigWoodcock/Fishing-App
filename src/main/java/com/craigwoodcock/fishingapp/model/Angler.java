package com.craigwoodcock.fishingapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "anglers")
public class Angler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "angler", cascade = CascadeType.ALL)
    private List<Catch> catches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Catch> getCatches() {
        return catches;
    }

    public void setCatches(List<Catch> catches) {
        this.catches = catches;
    }

    @Override
    public String toString() {
        return "Angler{" +
                "id=" + id +
                ", session=" + session +
                ", name='" + name + '\'' +
                ", catches=" + catches +
                '}';
    }
}