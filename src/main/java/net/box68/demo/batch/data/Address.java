package net.box68.demo.batch.data;

import org.springframework.core.style.ToStringCreator;


/**
 * @author Matthias Jell
 *
 */
public class Address {

    private String street;
    private String city;
    private String zip;

    /**
     * @return the street
     */
    public String getStreet() {

        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(final String name1) {

        this.street = name1;
    }

    /**
     * @return the city
     */
    public String getCity() {

        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(final String name2) {

        this.city = name2;
    }

    /**
     * @return the zip
     */
    public String getZip() {

        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(final String name3) {

        this.zip = name3;
    }

    @Override
    public String toString() {

        return new ToStringCreator(this)
            .append("street", street)
            .append("city", city)
            .append("zip", zip)
            .toString();
    }

}
