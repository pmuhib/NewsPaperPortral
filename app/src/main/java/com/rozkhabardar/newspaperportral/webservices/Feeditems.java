package com.rozkhabardar.newspaperportral.webservices;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
@Root(name = "item",strict = false)
public class Feeditems implements Serializable{

    @Element(name = "link")
    private String link;
    @Element(name = "title")
    private String title;
    @Element(name = "pubDate")
    private String pubDate;
    @Element(name = "description")
    private String description;
public Feeditems()
{

}
    public Feeditems(String link, String title, String pubDate, String description) {
         this.link = link;
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
