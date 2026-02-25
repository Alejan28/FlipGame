package gui;

import Domain.*;
import Services.GameException;
import Services.IObserver;
import Services.IServices;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GridPaneController implements IObserver {
    @FXML
    private Button newGameButton;
    ObservableList<WordGame> model=javafx.collections.FXCollections.observableArrayList();

    @FXML
    private TableColumn<WordGame, String> nickname;
    @FXML
    private TableColumn<WordGame,LocalDateTime> start_time;
    @FXML
    private TableColumn<WordGame,Duration> duration;
    @FXML
    private TableColumn<WordGame, Integer> turns;
    @FXML
    private TableView<WordGame> ranking;
    private IServices service;
    private Player player;
    private WordGame game;
    @FXML
    private GridPane gridPane;
    private List<String> words;
    public void setWords(List<String> words) {
        this.words = words;

    }
    public void initTable(){
        nickname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlayer().getNickname())
        );
        start_time.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        turns.setCellValueFactory(new PropertyValueFactory<>("turns"));
        ranking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    private void initModel(){
        try{
            List<WordGame> games=service.getRankingWordGame();
            model.setAll(games);
        }catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("You won");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void setService(IServices service) {
        words = new ArrayList<>();
        try{
        WordGame wordGame=service.startWordGame(player);
        this.game=wordGame;
        for(Word w:wordGame.getConfig().getWords()){
            words.add(w.getWord());
        }
        newGameButton.setDisable(true);

        }catch(GameException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        this.service = service;
        initialize();
        initTable();
        initModel();
        ranking.setItems(model);
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    private void initialize() {
        int rowSize = 4;
        int colSize = 3;

        List<String> wordList = new ArrayList<>();
        for (String word : words) {
            wordList.add(word);
            wordList.add(word);
        }
        Collections.shuffle(wordList);

        int index = 0;

        // Store the first and second selected cards
        final Button[] selectedButtons = new Button[2];
        final int[] selectionCount = {0};
        AtomicInteger count= new AtomicInteger();
        AtomicInteger allChoices = new AtomicInteger();
        for (int row = 0; row < colSize; row++) {
            for (int col = 0; col < rowSize; col++) {
                Button card = new Button("?");
                card.setPrefSize(80, 80);

                String word = wordList.get(index++);
                card.setUserData(null);
                card.setUserData(word);

                card.setOnAction(e -> {
                    Button clicked = (Button) e.getSource();

                    if (selectionCount[0] == 2 || clicked.getText() != "?") {
                        return; // Ignore clicks when two cards are already selected or already revealed
                    }

                    String value = (String) clicked.getUserData();
                    clicked.setText(value);

                    selectedButtons[selectionCount[0]++] = clicked;

                    if (selectionCount[0] == 2) {
                        Button first = selectedButtons[0];
                        Button second = selectedButtons[1];
                        allChoices.incrementAndGet();
                        try{
                            Choice choice= new Choice();
                            choice.setWordGame(game);
                            choice.setFirst_word((String) first.getUserData());
                           choice.setSecond_word((String) second.getUserData());
                           service.addChoice(choice);
                        }catch (GameException ex) {
                            System.out.println(ex.getMessage());
                        }
                        if (first.getUserData().equals(second.getUserData())) {
                            count.getAndIncrement();
                            // Match
                            first.setDisable(true);
                            second.setDisable(true);
                            selectionCount[0] = 0;
                            if(count.get()==wordList.size()/2){
                                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText(null);
                                alert.setContentText("You won");
                                alert.showAndWait();
                                try{
                                    game.setStatus(Game.gameStatus.WON);
                                    LocalDateTime endTime = LocalDateTime.now();
                                    LocalDateTime startTime=game.getStartTime();
                                    java.time.Duration duration = java.time.Duration.between(startTime, endTime);
                                    game.setStatus(Game.gameStatus.WON);
                                    game.setDuration(duration);
                                    game.setTurns(allChoices.get());
                                    service.updateWordGame(game);
                                    newGameButton.setDisable(false);
                                }catch(GameException ex){
                                    System.out.println(ex.getMessage());
                                }
                            }

                        } else {
                            // Not a match – flip back after delay
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> {
                                first.setText("?");
                                second.setText("?");
                                selectionCount[0] = 0;
                            });
                            pause.play();
                        }
                    }
                });

                gridPane.add(card, col, row);
            }
        }
    }

    @Override
    public void gameWon(Game game) throws GameException {

    }

    @Override
    public void wordGame(WordGame wordGame) throws GameException {
        Platform.runLater(()->{
            initModel();
            ranking.setItems(model);
            if(wordGame.getId()==this.game.getId()){
                int position = model.indexOf(wordGame);
                position++;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You won");
                alert.setHeaderText(null);
                alert.setContentText("Your current ranking: "+ position);
                alert.showAndWait();
            }
        });
    }

    public void pressNewGame(ActionEvent actionEvent) {
        try{
            WordGame game= service.startWordGame(player);
            this.game = game;
            words.clear();
            for(Word w:game.getConfig().getWords()){
                words.add(w.getWord());
            }
            gridPane.getChildren().clear();
            Platform.runLater(this::initialize);
            newGameButton.setDisable(true);
        }catch(GameException ex){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
