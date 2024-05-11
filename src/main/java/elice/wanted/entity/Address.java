package elice.wanted.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String country;
    private String town;
    private String street;
    private String building;

}
