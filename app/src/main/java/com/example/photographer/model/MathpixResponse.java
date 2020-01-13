package com.example.photographer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muskan Hussain on 13-01-2020
 */
public class MathpixResponse {
    @SerializedName("text")
    private String text;
    @SerializedName("latex_normal")
    private String latex_normal;
    @SerializedName("wolfram")
    private String wolfram;
    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLatex_normal() {
        return latex_normal;
    }

    public void setLatex_normal(String latex_normal) {
        this.latex_normal = latex_normal;
    }

    public String getWolfram() {
        return wolfram;
    }

    public void setWolfram(String wolfram) {
        this.wolfram = wolfram;
    }
}
