# Gravatar Library #


## Purpose ##

This Library contains two components

* GravatarUriBuilder -- an api for building a Gravatar Uri

* gravatar.tld -- a jsp taglib for creating a Garatar Uri within a jsp


## Usage Examples ##

GravatarUriBuilder Class

	import com.github.jwadamson.gravatar.GravatarUriBuilder;
	import com.github.jwadamson.gravatar.DefaultAction;
	import java.io.URI;
	
	GravatarUriBuilder uriBuilder = new GravatarUriBuilder();
	uriBuilder.setSecure(true);     // create https urls for images
	uriBuilder.setSize(50);         // override default 80x80 image
	uriBuilder.setExtension("jpg"); // add jpg extension to url
	uriBuilder.setDefaultAction(DefaultAction.IDENTICON); // alter the default image behavior to generate an identicon
	URI result = uriBuilder.build("user@example.com");

TagLib

* TODO


