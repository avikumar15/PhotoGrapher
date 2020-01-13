package com.example.photographer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muskan Hussain on 13-01-2020
 */
public class MathpixRequest {
    @SerializedName("src")
    private String src;
    @SerializedName("ocr")
    private String[] ocr;
    @SerializedName("formats")
    private String[] formats;

    public MathpixRequest() {
        ocr = new String[]{"math", "text"};
        formats = new String[]{"text", "latex_normal", "wolfram"};
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String[] getOcr() {
        return ocr;
    }

    public void setOcr(String[] ocr) {
        this.ocr = ocr;
    }

    public String[] getFormats() {
        return formats;
    }

    public void setFormats(String[] formats) {
        this.formats = formats;
    }
}
