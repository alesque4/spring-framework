package com.training.springcore.model;

import java.time.Instant;

public class Measure {

    /**
     * instant de la mesure
     */
    private Instant instant;

    /**
     * Valeur de la mesure (W)
     */
    private Integer valueInWatt;

    /**
     * Capteur qui a fait la mesure
     */
    private Captor captor;

    public Measure(Instant instant, Integer valueInWatt, Captor captor) {
        this.instant = instant;
        this.valueInWatt = valueInWatt;
        this.captor = captor;
    }

    @Override
    public String toString(){
        return "Measure : (Instant : "+instant
                +", Value : "+valueInWatt+"(W)"
                +", Capteur : "+captor+")";
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().equals(this.getClass())){
            Measure m = (Measure) o;
            return this.getInstant().equals(m.getInstant())
                    && this.getValueInWatt() == m.getValueInWatt()
                    && this.getCaptor().equals(m.getCaptor());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return getInstant().hashCode()
                + getCaptor().hashCode()
                + getValueInWatt().hashCode();
    }

    /*
     * Getters and Setters
     */
    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Integer getValueInWatt() {
        return valueInWatt;
    }

    public void setValueInWatt(Integer valueInWatt) {
        this.valueInWatt = valueInWatt;
    }

    public Captor getCaptor() {
        return captor;
    }

    public void setCaptor(Captor captor) {
        this.captor = captor;
    }
}
