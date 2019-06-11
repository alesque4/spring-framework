package com.training.spring.bigcorp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SIMULATED")
public class SimulatedCaptor extends Captor {

    @Column(name = "MIN_POWER_IN_WATT")
    private Integer minPowerInWatt;

    @Column(name = "MAX_POWER_IN_WATT")
    private Integer maxPowerInWatt;

    @Deprecated
    public SimulatedCaptor() {
        super();
        // used only by serializer and deserializer
    }

    public SimulatedCaptor(String name, Site site) {
        super(name, site);
    }

    /*
        Getters and Setters
     */
    public Integer getMinPowerInWatt() {
        return minPowerInWatt;
    }

    public void setMinPowerInWatt(Integer minPowerInWatt) {
        this.minPowerInWatt = minPowerInWatt;
    }

    public Integer getMaxPowerInWatt() {
        return maxPowerInWatt;
    }

    public void setMaxPowerInWatt(Integer maxPowerInWatt) {
        this.maxPowerInWatt = maxPowerInWatt;
    }
}
