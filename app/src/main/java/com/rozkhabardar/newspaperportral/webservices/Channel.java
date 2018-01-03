package com.rozkhabardar.newspaperportral.webservices;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
@Root(name = "channel",strict = false)
public class Channel implements Serializable{

    @ElementList(inline =true,name="item" )
    private List<Feeditems> feeditemsList;
    public List<Feeditems> getFeeditemsList() {
        return feeditemsList;
    }

    public Channel() {
    }

    public Channel(List<Feeditems> feeditemsList) {
        this.feeditemsList = feeditemsList;
    }
}
