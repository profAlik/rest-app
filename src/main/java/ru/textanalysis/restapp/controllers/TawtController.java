package ru.textanalysis.restapp.controllers;

import org.springframework.web.bind.annotation.*;
import ru.textanalysis.restapp.MyText;
import ru.textanalysis.restapp.WordWithParameters;
import ru.textanalysis.restapp.Words;
import ru.textanalysis.tawt.gama.main.Gama;
import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;
import ru.textanalysis.tawt.graphematic.parser.text.GraphematicParser;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.grammeme.MorfologyParameters;
import ru.textanalysis.tawt.ms.internal.ref.RefOmoFormList;
import ru.textanalysis.tawt.ms.internal.sp.BearingPhraseSP;
import ru.textanalysis.tawt.ms.storage.OmoFormList;
import ru.textanalysis.tawt.ms.storage.ref.RefBearingPhraseList;
import ru.textanalysis.tawt.ms.storage.ref.RefParagraphList;
import ru.textanalysis.tawt.ms.storage.ref.RefSentenceList;
import ru.textanalysis.tawt.ms.storage.ref.RefWordList;
import ru.textanalysis.tawt.sp.api.SyntaxParser;


import java.io.PrintStream;
import java.util.*;

@RestController
@RequestMapping("tawt")
public class TawtController {

    private JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
    private Gama gama = new Gama();
    private GraphematicParser parser = new GParserImpl();
    private SyntaxParser sp = new SyntaxParser();

    @GetMapping
    public String hello () {
        return "Glory to Mankind!";
    }


    @PostMapping ("morf/getAllCharacteristicsOfForm")
    public List<OmoFormList> getAllCharacteristicsOfForm (@RequestBody MyText myText) {

        List<OmoFormList> omoFormLists = Collections.singletonList(jMorfSdk.getAllCharacteristicsOfForm(myText.getText()));

        return omoFormLists;
    }

    @PostMapping ("morf/getAllCharacteristicsOfFormArray")
    public List<String> getAllCharacteristicsOfFormArray (@RequestBody Words words) {

        List<String> listStr = new ArrayList<String>();;
        for (String word : words.getWords()) {
            jMorfSdk.getAllCharacteristicsOfForm(word).forEach(form -> {
                if (form.getTheMorfCharacteristics(MorfologyParameters.Gender.class) == words.getGender()) {
                    listStr.add(form + " - " + word);
                }
            });
        }

        return listStr;
    }

    @PostMapping ("morf/getDerivativeForm")
    public List<String> getDerivativeForm (@RequestBody WordWithParameters word) {

        List<String> derivativeForms = null;
        try {
            if (word.getNumbers() == null) {
                derivativeForms = jMorfSdk.getDerivativeForm(word.getWord(),
                        word.getTypeOfSpeech());
            } else if (word.getTypeOfSpeech() == null) {
                derivativeForms = jMorfSdk.getDerivativeForm(word.getWord(),
                        word.getNumbers());
            } else {
                derivativeForms = jMorfSdk.getDerivativeForm(word.getWord(),
                        word.getTypeOfSpeech(),
                        word.getNumbers());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return derivativeForms;
    }

    @PostMapping ("morf/getMorphologyCharacteristics")
    public List<Long> getMorphologyCharacteristics (@RequestBody MyText myText) {

        List<Long> longList = jMorfSdk.getMorphologyCharacteristics(myText.getText());

        return longList;
    }

    @PostMapping ("morf/getRefOmoFormList")
    public List<RefOmoFormList> getRefOmoFormList (@RequestBody MyText myText) {

        List<RefOmoFormList> refOmoFormLists = Collections.singletonList(jMorfSdk.getRefOmoFormList(myText.getText()));

        return refOmoFormLists;
    }

    @PostMapping ("morf/getStringInitialForm")
    public List<String> getStringInitialForm (@RequestBody MyText myText) {

        List<String> stringInitialForm = jMorfSdk.getStringInitialForm(myText.getText());

        return stringInitialForm;
    }

    @PostMapping ("morf/getTypeOfSpeeches")
    public List<Byte> getTypeOfSpeeches (@RequestBody MyText myText) {

        List<Byte> typeOfSpeeches = jMorfSdk.getTypeOfSpeeches(myText.getText());

        return typeOfSpeeches;
    }

    @PostMapping ("morf/isFormExistsInDictionary")
    public boolean isFormExistsInDictionary (@RequestBody MyText myText) {

        boolean formExistsInDictionary = jMorfSdk.isFormExistsInDictionary(myText.getText());

        return formExistsInDictionary;
    }

    @PostMapping ("morf/isInitialForm")
    public byte isInitialForm (@RequestBody MyText myText) {

        byte initialForm = jMorfSdk.isInitialForm(myText.getText());

        return initialForm;
    }

    @PostMapping ("morf/finish")
    public String finish () {
        jMorfSdk.finish();
        return "Performed";
    }

/*    @PostMapping ("addForm")
    public void addForm (...) {
        jMorfSdk.addForm(...);
    }*/

    @PostMapping ("gama/getMorphParagraph")
    public RefSentenceList getMorphParagraph (@RequestBody MyText sentence) {

        gamaInit();
        RefSentenceList refSentenceList = gama.getMorphParagraph(sentence.getText());

        return refSentenceList;

    }

    @PostMapping ("gama/getMorphBearingPhrase")
    public RefWordList getMorphBearingPhrase (@RequestBody MyText bearingPhrase) {

        gamaInit();
        RefWordList refWordList = gama.getMorphBearingPhrase(bearingPhrase.getText());

        return refWordList;

    }

    @PostMapping ("gama/getMorphSentence")
    public RefBearingPhraseList getMorphSentence (@RequestBody MyText sentence) {

        gamaInit();
        RefBearingPhraseList refBearingPhraseList = gama.getMorphSentence(sentence.getText());

        return refBearingPhraseList;

    }

    @PostMapping ("gama/getMorphText")
    public RefParagraphList getMorphText (@RequestBody MyText myText) {

        gamaInit();
        RefParagraphList refSentenceLists = gama.getMorphText(myText.getText());

        return refSentenceLists;

    }

    @PostMapping ("gama/getMorphWord")
    public RefOmoFormList getMorphWord (@RequestBody MyText word) {

        gamaInit();
        RefOmoFormList refOmoFormList = gama.getMorphWord(word.getText());

        return refOmoFormList;

    }

/*    @PostMapping ("gama/setGamaMorphSdk")
    public void setGamaMorphSdk (@RequestBody IGamaMorfSdk iGamaMorfSdk) {

        gamaInit();
        gama.setGamaMorphSdk(iGamaMorfSdk);

    }

    @PostMapping ("gama/setGamaMorphSdk")
    public void setGamaMorphSdk (@RequestBody IGamaParser iGamaParser) {

        gamaInit();
        gama.setGamaParser(iGamaParser);

    }*/

//    @PostMapping ("gama/gamaInit")
    private void gamaInit () {

        gama.init();

    }

    @PostMapping ("parser/parserSentence")
    public List<List<String>> parserSentence (@RequestBody MyText sentence) {

        List<List<String>> parserSentence = parser.parserSentence(sentence.getText());

        return parserSentence;

    }

    @PostMapping ("parser/parserParagraph")
    public List<List<List<String>>> parserParagraph (@RequestBody MyText sentence) {

        List<List<List<String>>> parserParagraph = parser.parserParagraph(sentence.getText());

        return parserParagraph;

    }

    @PostMapping ("parser/parserBasicsPhase")
    public List<String> parserBasicsPhase (@RequestBody MyText sentence) {

        List<String> parserBasicsPhase = parser.parserBasicsPhase(sentence.getText());

        return parserBasicsPhase;

    }

    @PostMapping ("parser/parserText")
    public List<List<List<List<String>>>> parserText (@RequestBody MyText sentence) {

        List<List<List<List<String>>>> parserText = parser.parserText(sentence.getText());

        return parserText;

    }



    @PostMapping ("sp/getTreeSentence")
    public List<BearingPhraseSP> getTreeSentence (@RequestBody MyText sentence) {

        sp.init();
        List<BearingPhraseSP> treeSentence = sp.getTreeSentence(sentence.getText());

        return treeSentence;

    }




//---------------------------------------------------------------- MORF --------------------------------------------
/*
fetch(
  '/tawt/morf/getAllCharacteristicsOfForm',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------

fetch(
  '/tawt/morf/getAllCharacteristicsOfFormArray',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({'words':["осенний", "осенней", "площадь", "стол", "играть", "конференций", "на", "бежала"], 'gender': 8})
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------

fetch(
  '/tawt/morf/getDerivativeForm',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({word:'дерево', typeOfSpeech: 17, numbers: 32})
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/getMorphologyCharacteristics',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/getRefOmoFormList',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/getStringInitialForm',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/getTypeOfSpeeches',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/isFormExistsInDictionary',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------------
fetch(
  '/tawt/morf/isInitialForm',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("село")
    }
).then(result => result.json().then(console.log))


------------------------------------------------------------------------------ GAMA ----------------------------------
fetch(
  '/tawt/gama/getMorphParagraph',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Осенний марафон - стало ясно, что будет с российской валютой. Справедливый курс, по мнению аналитиков, — на уровне 65-66.")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------

fetch(
  '/tawt/gama/getMorphBearingPhrase',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Я пошел гулять")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------

fetch(
  '/tawt/gama/getMorphSentence',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Осенний марафон - стало ясно, что будет с российской валютой. Справедливый курс, по мнению аналитиков, — на уровне 65-66.")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------

fetch(
  '/tawt/gama/getMorphText',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Осенний марафон - стало ясно, что будет с российской валютой. Справедливый курс, по мнению аналитиков, — на уровне 65-66.")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------

fetch(
  '/tawt/gama/getMorphWord',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Осенний")
    }
).then(result => result.json().then(console.log))

------------------------------------------------------------------------ PARSER ----------------------------------------

fetch(
  '/tawt/parser/parserSentence',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Parser - это программа начального анализа естественного текста, представленного в виде цепочки символов, вырабатывающая информацию, необходимую для дальнейшей обработки.")
    }
).then(result => result.json().then(console.log))

---------------------------------------------------------------------------

fetch(
  '/tawt/parser/parserParagraph',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Parser - это программа начального анализа естественного текста, представленного в виде цепочки символов, вырабатывающая информацию, необходимую для дальнейшей обработки.")
    }
).then(result => result.json().then(console.log))

---------------------------------------------------------------------------

fetch(
  '/tawt/parser/parserBasicsPhase',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Parser - это программа начального анализа естественного текста, представленного в виде цепочки символов, вырабатывающая информацию, необходимую для дальнейшей обработки.")
    }
).then(result => result.json().then(console.log))

---------------------------------------------------------------------------

fetch(
  '/tawt/parser/parserText',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Parser - это программа начального анализа естественного текста, представленного в виде цепочки символов, вырабатывающая информацию, необходимую для дальнейшей обработки.")
    }
).then(result => result.json().then(console.log))

--------------------------------------------------------------------------- SP -----------------------------------

fetch(
  '/tawt/sp/getTreeSentence',
    {
        method: 'POST',
                headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify("Стало ясно, что будет с российской валютой.")
    }
).then(result => result.json().then(console.log))

*/


}