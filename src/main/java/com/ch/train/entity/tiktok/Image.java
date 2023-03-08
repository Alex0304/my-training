package com.ch.train.entity.tiktok;

import java.util.List;

/**
 * @author DXM-0189
 * 图片
 */
public class Image {

    private int height;
    private int width;
    private String thumb_uri;
    private List<String> thumb_url_list;
    private String uri;
    private List<String> url_list;

    public int getHeight() {
        return height;
    }

    public Image setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Image setWidth(int width) {
        this.width = width;
        return this;
    }

    public String getThumb_uri() {
        return thumb_uri;
    }

    public Image setThumb_uri(String thumb_uri) {
        this.thumb_uri = thumb_uri;
        return this;
    }

    public List<String> getThumb_url_list() {
        return thumb_url_list;
    }

    public Image setThumb_url_list(List<String> thumb_url_list) {
        this.thumb_url_list = thumb_url_list;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Image setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public List<String> getUrl_list() {
        return url_list;
    }

    public Image setUrl_list(List<String> url_list) {
        this.url_list = url_list;
        return this;
    }
}
