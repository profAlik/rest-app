package ru.textanalysis.restapp;

import java.util.ArrayList;
import java.util.List;

public class Words {

    private List<String> words = new ArrayList<String>();
    /**
     *         long COMMON = 0L;
     *         long UNCLEARGENDER = 0L;
     *         long MANS = 4L;
     *         long FEMININ = 8L;
     *         long NEUTER = 12L;
     *         long IDENTIFIER = 12L;
     */
    private Long gender;

    public Words(List<String> words, Long gender) {
        this.words = words;
        this.gender = gender;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public List<String> getWords() {
        return words;
    }

    public Long getGender() {
        return gender;
    }
}
