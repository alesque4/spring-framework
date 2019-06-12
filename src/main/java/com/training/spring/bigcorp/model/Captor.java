package com.training.spring.bigcorp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract public class Captor {
    /**
     * Captor id
     */
    @Id
    private String id;

    /**
     * Captor name
     */
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    /**
     * Captor site
     */
    @ManyToOne
    private Site site;

    /**
     * Version
     */
    @Version
    private int version;

    /**
     * Power source
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private PowerSource powerSource;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    public Captor() {
        // Use for serializer or deserializer
    }

    /**
     * Constructor to use with required property
     * @param name
     * @param site
     */
    public Captor(String name, Site site, PowerSource powerSource) {
        this.name = name;
        this.site = site;
        this.powerSource = powerSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Captor captor = (Captor) o;
        return Objects.equals(name, captor.name);
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }
}
