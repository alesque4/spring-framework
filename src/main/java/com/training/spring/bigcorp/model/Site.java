package com.training.spring.bigcorp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SITE")
public class Site {
    /**
     * Site id
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * Site name
     */
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    /**
     * Site captors
     */
    @OneToMany(mappedBy = "site")
    private Set<Captor> captors;

    /**
     * Version
     */
    @Version
    private int version;

    @Deprecated
    public Site() {
        // Use for serializer or deserializer
    }

    /**
     * Constructor to use with required property
     * @param name Nom du site
     */
    public Site(String name) {
        this.name = name;
        captors = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return Objects.equals(name, site.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Site{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", captors=" + captors +
                '}';
    }

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

    public Set<Captor> getCaptors() {
        return captors;
    }

    public void setCaptors(Set<Captor> captors) {
        this.captors = captors;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
