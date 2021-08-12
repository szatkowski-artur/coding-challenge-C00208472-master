package io.twodigits.urlshortener.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Statistic {

    /**
     * The uniqe ID of and Statistic
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    private String url;


    private Timestamp timestamp;

    private String userAgent;

    private String refer;

    private String cookies;



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getUrl() {
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
    }



    public Timestamp getTimestamp() {
        return timestamp;
    }



    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



    public String getUserAgent() {
        return userAgent;
    }



    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }



    public String getRefer() {
        return refer;
    }



    public void setRefer(String refer) {
        this.refer = refer;
    }



    public String getCookies() {
        return cookies;
    }



    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
