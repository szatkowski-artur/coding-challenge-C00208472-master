package io.twodigits.urlshortener.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class URL {

    /**
     * The unique ID of an URL
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /**
     * The URL for which a short URL is provided
     */
    @Column(length = 10000)
    private String url;

    /**
     * The ID of a user to which this URL belongs
     */
    private String user;



    public String getId() {
        return this.id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getURL() {
        return this.url;
    }



    public void setUrl(String url) {
        this.url = url;
    }



    public String getUser() {
        return this.user;
    }



    public void setUser(String user) {
        this.user = user;
    }
}
