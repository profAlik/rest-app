package ru.textanalysis.restapp;

public class MyText {

    private String text;

    public MyText(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
