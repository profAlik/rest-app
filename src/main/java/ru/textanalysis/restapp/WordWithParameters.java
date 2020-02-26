package ru.textanalysis.restapp;

public class WordWithParameters {

    private String word;

    /**
     *         byte NOUN = 17;
     *         byte ADJECTIVEFULL = 18;
     *         byte ADJECTIVESHORT = 19;
     *         byte VERB = 20;
     *         byte INFINITIVE = 21;
     *         byte PARTICIPLEFULL = 22;
     *         byte PARTICIPLE = 23;
     *         byte GERUND = 25;
     *         byte GERUNDIMPERFECT = 26;
     *         byte GERUND_SHI = 27;
     *         byte NUMERAL = 28;
     *         byte COLLECTIVENUMERAL = 29;
     *         byte NOUNPRONOUN = 30;
     *         byte ANAPHORICPRONOUN = 31;
     *         byte ADVERB = 9;
     *         byte COMPARATIVE = 10;
     *         byte PREDICATE = 11;
     *         byte PRETEXT = 12;
     *         byte UNION = 13;
     *         byte PARTICLE = 14;
     *         byte INTERJECTION = 15;
     *         byte IDENTIFIER = 31;
     */
    private Byte typeOfSpeech;

    /**
     *         long SINGULAR = 32L;
     *         long PLURAL = 48L;
     *         long IDENTIFIER = 48L;
     */
    private Long numbers;

    public WordWithParameters(String word, Byte typeOfSpeech, Long numbers) {
        this.word = word;
        this.typeOfSpeech = typeOfSpeech;
        this.numbers = numbers;
    }


    public String getWord() {
        return word;
    }

    public Byte getTypeOfSpeech() {
        return typeOfSpeech;
    }

    public Long getNumbers() {
        return numbers;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTypeOfSpeech(byte typeOfSpeech) {
        this.typeOfSpeech = typeOfSpeech;
    }

    public void setNumbers(long numbers) {
        this.numbers = numbers;
    }


}
