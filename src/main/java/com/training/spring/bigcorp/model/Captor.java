package com.training.spring.bigcorp.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "CAPTOR")
public class Captor {
    /**
     * Captor id
     */
    @Id
    private String id = UUID.randomUUID().toString();;

    /**
     * Captor name
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Captor power source
     */
    @Enumerated(EnumType.STRING)
    @Column(name="POWER_SOURCE", nullable = false)
    private PowerSource powerSource;

    /**
     * Captor site
     */
    @ManyToOne
    private Site site;

    /**
     * Valeur par défaut (W)
     */
    @Column(name = "DEFAULT_POWER_IN_WATT")
    private Integer defaultValueInWatt;

    @Deprecated
    public Captor() {
        // Use for serializer or deserializer
    }

    /**
     * Constructor to use with required property
     * @param name
     * @param site
     */
    public Captor(String name, Site site) {
        this.name = name;
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Captor captor = (Captor) o;
        return Objects.equals(name, captor.name) &&
                powerSource == captor.powerSource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Captor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public PowerSource getPowerSource() {return powerSource;}

    public void setPowerSource(PowerSource powerSource) {this.powerSource = powerSource;}

    public Site getSite() {return site;}

    public void setSite(Site site) {this.site = site;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
