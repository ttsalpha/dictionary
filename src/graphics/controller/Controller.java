package graphics.controller;

import graphics.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import terminal.Dictionary;
import terminal.DictionaryCommandLine;
import terminal.DictionaryManagement;
import terminal.Word;

import java.io.IOException;

import static graphics.controller.GoogleTranslate.translate;

public class Controller extends Parent {

    @FXML
    public String target;
    @FXML
    TextField getTextSearch,
            getTextEdit1, getTextEdit2, getTextEdit3,
            getTextRemove,
            getTextAdd1, getTextAdd2, getTextAdd3, getTextEdit4,
            getTextSentence;
    @FXML
    TextArea showExplainSearch, showExplainSentence;
    @FXML
    Label explainSearch;
    @FXML
    ListView<String> listViewSearch, listViewEdit, listViewRemove;
    @FXML
    Word wordSelectedRemove, wordSelectedEdit;
    @FXML
    Button buttonRemove, buttonAdd, buttonSearch, buttonEdit;
    DictionaryCommandLine dc = new DictionaryCommandLine();
    private Object ActionEvent;

    public Controller() {
//        dc.insertFromFile();
    }


    /**
     * switch to another window
     * set the main scene for goTo
     *
     * @param goTo
     * @throws IOException
     */
    @FXML
    public void goTo(String goTo) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(goTo));
        Scene scene = new Scene(parent);
        scene.setRoot(parent);
        Main.mainStage.setScene(scene);
    }

    @FXML
    public void goToAbout() throws IOException {
        goTo("../view/About.fxml");
        System.out.println("> Go to About me");
    }

    @FXML
    public void goToAddWord() throws IOException {
        goTo("../view/AddWord.fxml");
        System.out.println("> Go to window Add word");
    }

    @FXML
    public void goToRemoveWord() throws IOException {
        goTo("../view/RemoveWord.fxml");
        System.out.println("> Go to window Remove word");
    }

    @FXML
    public void goToEditWord() throws IOException {
        goTo("../view/EditWord.fxml");
        System.out.println("> Go to window Edit word");
    }

    @FXML
    public void goToSearch() throws IOException {
        goTo("../view/Search.fxml");
        System.out.println("> Go to window Search by dictionary");
    }

    @FXML
    public void goToGoogle() throws IOException {
        goTo("../view/Google.fxml");
        System.out.println("> Go to window Search by Google");
    }


    /**
     * search for words using continuous input
     */
    @FXML
    public void search() {
        String textSearch = getTextSearch.getText(); //get text from text field

        listViewSearch.getItems().clear();
        listViewSearch.getItems().addAll(dc.dictionaryLookup(textSearch));  //add list search start with to list view

        listViewClicked();
    }

    /**
     * select a word from the list and print the meaning in text
     */
    public void listViewClicked() {
        listViewSearch.setOnMouseClicked(event -> {
            target = listViewSearch.getSelectionModel().getSelectedItem();
            getTextSearch.setText(target);

            showExplainSearch.clear();
            showExplainSearch.appendText(dc.dictionarySearcher(target));
        });

        listViewSearch.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                target = listViewSearch.getSelectionModel().getSelectedItem();
                getTextSearch.setText(target);

                showExplainSearch.clear();
                showExplainSearch.appendText(dc.dictionarySearcher(target));
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                if (listViewSearch.getSelectionModel().getSelectedIndex() == 0)
                    getTextSearch.requestFocus();
            }
        });
    }


    @FXML
    public void add() {
        String textAdd1 = getTextAdd1.getText();
//        String textAdd2 = getTextAdd2.getText();
        String textAdd3 = getTextAdd3.getText();
//        textAdd3 = "/" + textAdd2 + "/\t" + textAdd3;

        Word wordAdd = new Word();
        wordAdd.setWord_target(textAdd1);
        wordAdd.setWord_explain(textAdd3);

        buttonAdd.setOnAction(actionEvent -> {
            Dictionary.dictionary.add(wordAdd);
            System.out.println("> Add word: " + wordAdd.getWord_target());

            DictionaryManagement.dictionaryExportToFile();
        });
    }

    @FXML
    public void edit() {
        String textEdit = getTextEdit1.getText();

        listViewEdit.getItems().clear();
        listViewEdit.getItems().addAll(dc.dictionaryLookup(textEdit));

        listViewEdit.setOnMouseClicked(event -> {
            String target = listViewEdit.getSelectionModel().getSelectedItem();
            getTextEdit1.setText(target);
            wordSelectedEdit = dc.dictionaryFinder(target);
//            System.out.println(wordSelectedEdit.getWord_target() + wordSelectedEdit.getWord_explain());
            getTextEdit2.setText(wordSelectedEdit.getWord_target());
            getTextEdit4.setText(wordSelectedEdit.getWord_explain());
        });

        buttonEdit.setOnAction(actionEvent -> {
            for (var word : Dictionary.dictionary) {
                if (wordSelectedEdit.equals(word)) {
                    word.setWord_target(getTextEdit2.getText());
                    word.setWord_explain(getTextEdit4.getText());
//                    System.out.println(word);
                }
            }

            System.out.println("> Edited word: " + wordSelectedEdit);

            DictionaryManagement.dictionaryExportToFile();
        });
    }

    @FXML
    public void remove() {
        String textRemove = getTextRemove.getText();
        listViewRemove.getItems().clear();
        listViewRemove.getItems().addAll(dc.dictionaryLookup(textRemove));

        listViewRemove.setOnMouseClicked(event -> {
            String target = listViewRemove.getSelectionModel().getSelectedItem();
            getTextRemove.setText(target);
            wordSelectedRemove = dc.dictionaryFinder(target);
        });

        listViewRemove.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String target = listViewRemove.getSelectionModel().getSelectedItem();
                getTextRemove.setText(target);
                wordSelectedRemove = dc.dictionaryFinder(target);
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                if (listViewRemove.getSelectionModel().getSelectedIndex() == 0) {
                    getTextRemove.requestFocus();
                }
            }
        });

        buttonRemove.setOnAction(actionEvent -> {
            Dictionary.dictionary.remove(wordSelectedRemove);
            System.out.println("> Has been removed: " + wordSelectedRemove.getWord_target());
            listViewRemove.getItems().clear();
            listViewRemove.getItems().addAll(dc.dictionaryLookup(textRemove));

            DictionaryManagement.dictionaryExportToFile();
        });
    }

    /**
     * translate sentences by Google API
     */
    public void google() throws IOException {
        String getTextGG = getTextSentence.getText();
        System.out.println("> Translated text: " + translate("en", "vi", getTextGG));
        showExplainSentence.setText(translate("en", "vi", getTextGG));
    }

    /**
     * press button to speak
     *
     * @throws Exception
     */
    public void voice() throws Exception {
        SpeedController.voice(target);
    }
}