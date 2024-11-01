package com.craigwoodcock.fishingapp.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "catches")
public class Catch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "angler_id", nullable = false)
    private Angler angler;

    @Column(name = "catch_time", nullable = false)
    private LocalDateTime catchTime;

    @Column(name = "peg_or_swim", length = 50)
    private String pegOrSwim;

    @Column(name = "fish_type", nullable = false, length = 50)
    private String fishType;

    @Column(name = "weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "photo_url")
    private String photoUrl;

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

    public LocalDateTime getCatchTime() {
        return catchTime;
    }

    public void setCatchTime(LocalDateTime catchTime) {
        this.catchTime = catchTime;
    }

    public String getPegOrSwim() {
        return pegOrSwim;
    }

    public void setPegOrSwim(String pegOrSwim) {
        this.pegOrSwim = pegOrSwim;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Angler getAngler() {
        return angler;
    }

    public void setAngler(Angler angler) {
        this.angler = angler;
    }

    @Override
    public String toString() {
        return "Catch{" +
                "id=" + id +
                ", session=" + session +
                ", angler=" + angler +
                ", catchTime=" + catchTime +
                ", pegOrSwim='" + pegOrSwim + '\'' +
                ", fishType='" + fishType + '\'' +
                ", weight=" + weight +
                ", notes='" + notes + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}