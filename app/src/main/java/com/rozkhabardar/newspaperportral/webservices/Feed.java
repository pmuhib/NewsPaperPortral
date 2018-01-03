package com.rozkhabardar.newspaperportral.webservices;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
@Root(name = "rss", strict = false)
public class Feed implements Serializable {
    @Element(name = "channel")
    private Channel channel;

    public Feed(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Feed() {
    }
}
