package io.twodigits.urlshortener.validation.dto;

import com.sun.istack.NotNull;
import io.twodigits.urlshortener.validation.constrains.UserExist;

import javax.validation.constraints.Pattern;


public class URLDto {

private String id;

@Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
private String url;

@UserExist
private String user;

    public URLDto(String id, String url, String user) {
        this.id = id;
        this.url = url;
        this.user = user;
    }

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
