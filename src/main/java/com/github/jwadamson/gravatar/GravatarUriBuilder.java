package com.github.jwadamson.gravatar;

// jsr311-api (and jersey server or other runtime delegate?)
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

// apache commons codec
import org.apache.commons.codec.digest.DigestUtils;

// apache commons lang
import org.apache.commons.lang.StringUtils;


public class GravatarUriBuilder implements Cloneable {

    //*****************************************************************************
    // CLASS
    //*****************************************************************************

    //*****************************************************************************
    // INSTANCE
    //*****************************************************************************

    private String email;
    private String extension;
    private DefaultAction defaultAction = null;
    private URI defaultUri = null;
    private boolean secure = false;
    private boolean forceDefault = false;
    private Rating rating = null;
    private Integer size = null;

    /**
     * Construct a new GravatarUriBuilder instance.
     */
    public GravatarUriBuilder() {
    }

    /**
     * Set the email address used for fetching the avatar image.
     * @return the updated FravatarUriBuilder instance
     */
    public GravatarUriBuilder email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Append the given file extension to the uri path.
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder extension(String extension) {
        this.extension = extension;
        return this;
    }

    /**
     * Set the default action when an avatar is not registered for the given email
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder defaultAction(DefaultAction defaultAction) {
        this.defaultAction = defaultAction;
        return this;
    }

    /**
     * Set the default image url when DefaultAction#IMAGE is specified
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder defaultUri(URI defaultUri) {
        this.defaultUri = defaultUri;
        return this;
    }


    /**
     * Use a http secure url.
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder secure() {
        return this.secure(true);
    }

    /**
     * Use a http secure url, defaults to false
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder secure(boolean secure){
        this.secure = secure;
        return this;
    }

    /**
     * Force the default image to ge returned
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder forceDefault() {
        return forceDefault(true);
    }

    /**
     * Force the default image to be returned.
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder forceDefault(boolean forceDefault) {
        this.forceDefault = forceDefault;
        return this;
    }

    /**
     * Set the rating of the avatar, defaults to Rating#G
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder rating(Rating rating){
        this.rating = rating;
        return this;
    }

    /**
     * Set the size of the image, defaults to 80px by 80px
     * @return the updated GravatarUriBuilder instance
     */
    public GravatarUriBuilder size(Integer size){
        this.size = size;
        return this;
    }

    /**
     * Build the URI
     * @return the Uri for the current state of the GravatarUriBuilder
     */
    public URI build() {
        UriBuilder b = UriBuilder.fromUri("http://www.gravatar.com/avatar/");

        if (secure) {
            b.scheme("https");
            b.host("secure.gravatar.com");
        }

        if (StringUtils.isEmpty(email)) {
            throw new IllegalStateException("Email address must be supplied");
        }

        String hash = DigestUtils.md5Hex(email.toLowerCase().trim());
        if (StringUtils.isNotEmpty(extension)) {
            hash += "." + extension;
        }
        b.path(hash);

        // query params
        if (defaultAction == DefaultAction.IMAGE) {
            if (defaultUri != null) {
                b.queryParam("default", defaultUri.toString());
            }
            else {
                throw new IllegalStateException("a default image URI must be supplied for default image behavior");
            }
        }
        else if (defaultAction != null) {
            b.queryParam("default", defaultAction.toString().toLowerCase());
        }

        if (forceDefault) {
            b.queryParam("forceDefault", "y");
        }
        if (rating != null) {
            b.queryParam("rating", rating);
        }
        if (size != null) {
            if ( size < 1 || 500 < size) {
                throw new IllegalStateException("Image size must be between 1 and 500");
            }
            b.queryParam("size", size);
        }

        return b.build();
    }

    /**
     * Create a copy of the GravatarUriBuilder preserving its state
     * @return a clone of this instance.
     */
    @Override
    public GravatarUriBuilder clone()
    throws CloneNotSupportedException {
        return (GravatarUriBuilder)super.clone();
    }
}
