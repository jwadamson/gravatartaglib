package com.github.jwadamson.gravatar.taglib;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

import com.github.jwadamson.gravatar.DefaultAction;
import com.github.jwadamson.gravatar.GravatarUriBuilder;
import com.github.jwadamson.gravatar.Rating;

public class GravatarUrlTag extends TagSupport {

    //*****************************************************************************
    // CLASS
    //*****************************************************************************

    static private final long serialVersionUID = 1L;

    //*****************************************************************************
    // INSTANCE
    //*****************************************************************************

    private GravatarUriBuilder uriBuilder = new GravatarUriBuilder();
    private String var;
    private int scope = PageContext.PAGE_SCOPE;

    /**
     * Construct a new GravatarUrlTag instance.
     */
    public GravatarUrlTag() {
    }

    //
    // Generic Attributes
    //

    public void setVar(String var) {
        this.var = var;
    }

    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
    }

    //
    // URI Attributes
    //

    public void setEmail(String email) {
        uriBuilder.email(email);
    }

    public void setDefaultAction(String defaultAction) {
        uriBuilder.defaultAction(DefaultAction.valueOf(defaultAction));
    }

    public void setDefaultUri(String defaultUri)
    throws URISyntaxException {
        uriBuilder.defaultUri(new URI(defaultUri));
    }

    public void setExtension(String extension) {
        uriBuilder.extension(extension);
    }

    public void setForceDefault(Boolean forceDefault) {
        uriBuilder.forceDefault(forceDefault);
    }

    public void setRating(String rating) {
        uriBuilder.rating(Rating.valueOf(rating));
    }

    public void setSecure(Boolean secure) {
        uriBuilder.secure(secure);
    }

    public void setSize(Integer size) {
        uriBuilder.size(size);
    }

    //
    // Tag logic
    //

    // resets any parameters that might be sent
    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag()
    throws JspException {
        return SKIP_BODY;
    }

    // gets the right value, encodes it, and prints or stores it
    /**
     * {@inheritDoc}
     */
    @Override
    public int doEndTag()
    throws JspException {
        // add (already encoded) parameters
        URI gravatarUrl = uriBuilder.build();

        // store or print the output
        if (var != null) {
            pageContext.setAttribute(var, gravatarUrl.toString(), scope);
        }
        else {
           try {
               pageContext.getOut().print(gravatarUrl.toString());
           }
           catch (java.io.IOException ex) {
               throw new JspTagException(ex.toString(), ex);
           }
       }

       return EVAL_PAGE;
    }

    // Releases any resources we may have (or inherit)
    /**
     * {@inheritDoc}
     */
    @Override
    public void release() {
    }
}
