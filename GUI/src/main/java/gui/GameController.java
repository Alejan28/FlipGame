package gui;

import Domain.Game;
import Domain.Move;
import Domain.Player;
import Domain.WordGame;
import Services.GameException;
import Services.IObserver;
import Services.IServices;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GameController implements IObserver {
    ObservableList<Game> model=javafx.collections.FXCollections.observableArrayList();
    public TableView<Game> ranking;
    public TableColumn<Game,String> nicknameColumn;
    public TableColumn<Game,LocalDateTime> startTimeColumn;
    public TableColumn<Game,Duration> durationColumn;
    public TableColumn<Game,Integer> noTrialsColumn;
    public TableColumn<Game,String> animalColumn;
    @FXML
    private Label hintLabel;
    @FXML
    private TableColumn<Object[],Object> firstColumn;
    @FXML
    private TableColumn<Object[],Object> secondColumn;
    @FXML
    private TableColumn<Object[],Object> thirdColumn;
    @FXML
    private TableColumn<Object[],Object> fourthColumn;
    @FXML
    private TableView<Object[]> gameBoard;
    private Game game;
    private Image animalImage;
    private Integer noTrials;
    private Player player;
    private IServices services;
    private TablePosition<Integer[], ?> selectedCellPosition;
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setServices(IServices services) {
        this.services = services;
        try{
        Game newGame= this.services.startGame(player);
        this.game = newGame;
        animalImage = new Image("file:/C:/Users/40741/OneDrive/Pictures/iss-app/elefant-adorabil-de-colorat.jpg");
        noTrials=0;
        initTable();
        initModel();
        ranking.setItems(model);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Game");
        alert.setHeaderText(null);
        alert.setContentText(newGame.getConfig().getAnimal());
        }catch(GameException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private String giveHint(Integer x, Integer y){
        if(x==game.getConfig().getColumn()) {
            if (y < game.getConfig().getRow()) {
                return "Go south";
            }
            if(y>game.getConfig().getRow()) {
                return "Go north";
            }
        }
        if(y==game.getConfig().getRow()) {
            if (x < game.getConfig().getColumn()) {
                return "Go east";
            }
            if(x>game.getConfig().getColumn()) {
                return "Go west";
            }
        }
        if(y>game.getConfig().getRow()) {
            if(x<game.getConfig().getColumn()) {
                return "Go north-east";
            }
            if(x>game.getConfig().getColumn()) {
                return "Go north-west";
            }
        }
        if(y<game.getConfig().getRow()) {
            if(x<game.getConfig().getColumn()) {
                return "Go south-east";
            }
            if(x>game.getConfig().getColumn()) {
                return "Go south-west";
            }
        }
        return "Do nothing";
    }
    public void initTable(){
        nicknameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlayer().getNickname())
        );
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        noTrialsColumn.setCellValueFactory(new PropertyValueFactory<>("trials"));
        animalColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getConfig().getAnimal())
        );
        ranking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    private void initModel(){
       try{
           List<Game> games=services.getRanking();
           model.setAll(games);
       }catch (Exception e)
       {
           Alert alert=new Alert(Alert.AlertType.ERROR);
           alert.setTitle("You won");
           alert.setHeaderText(null);
           alert.setContentText("You found the animal");
           alert.showAndWait();
       }
    }
   @FXML
    public void initialize() {
       Callback<TableColumn<Object[], Object>, TableCell<Object[], Object>> colorCellFactory =
               column -> new TableCell<Object[], Object>() {
                   private final ImageView imageView = new ImageView();

                   @Override
                   protected void updateItem(Object item, boolean empty) {
                       super.updateItem(item, empty);
                       setText(null);
                       setGraphic(null);

                       if (empty) {
                           setStyle("");
                           return;
                       }

                       int row = getIndex();
                       int col = column.getTableView().getVisibleLeafIndex(column);

                       if (item instanceof Image) {
                           imageView.setImage((Image) item);
                           imageView.setFitWidth(40);
                           imageView.setFitHeight(40);
                           imageView.setPreserveRatio(true);
                           setGraphic(imageView);
                           setStyle("-fx-background-color: white; -fx-alignment: center;");
                       } else if (selectedCellPosition != null &&
                               selectedCellPosition.getRow() == row &&
                               selectedCellPosition.getColumn() == col) {
                           setStyle("-fx-background-color: yellow; -fx-alignment: center;");
                       } else {
                           setStyle("-fx-background-color: lightgreen; -fx-alignment: center;");
                       }
                   }
               };
// Setup cell value factories and apply the green cell factory
       firstColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()[0]));
       firstColumn.setCellFactory(colorCellFactory);

       secondColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()[1]));
       secondColumn.setCellFactory(colorCellFactory);

       thirdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()[2]));
       thirdColumn.setCellFactory(colorCellFactory);

       fourthColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()[3]));
       fourthColumn.setCellFactory(colorCellFactory);



// Set selection to cell-based, if not already
       gameBoard.getSelectionModel().setCellSelectionEnabled(true);

// Set row height (optional)
       gameBoard.setRowFactory(tv -> {
           TableRow<Object[]> row = new TableRow<>();
           row.setPrefHeight(60);
           return row;
       });

// Initialize the data (green cells will appear immediately)
       ObservableList<Object[]> boardData = FXCollections.observableArrayList();

       for (int row = 0; row < 3; row++) {
           Object[] rowData = new Object[4];
           Arrays.fill(rowData, null);  // Initialize with null
           boardData.add(rowData);
       }
       gameBoard.setOnMouseClicked(event -> {
           noTrials++;
           TablePosition<Integer[], ?> pos = gameBoard.getSelectionModel().getSelectedCells().stream()
                   .findFirst()
                   .orElse(null);

           if (pos != null) {
               selectedCellPosition = pos;  // Track the selected cell
               int row = pos.getRow();
               int column = pos.getColumn();
               Move move= new Move();
               move.setGame(game);
               move.setX(column);
               move.setY(row);
               try{
                   services.makeMove(move);
               }catch(GameException e){
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error");
                   alert.setHeaderText(null);
                   alert.setContentText(e.getMessage());
                   alert.showAndWait();
               }
               if(row==game.getConfig().getRow()&&column==game.getConfig().getColumn()){
                   boardData.get(row)[column] = animalImage;
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("You won");
                   alert.setHeaderText(null);
                   alert.setContentText("You found the animal in "+ noTrials+ " trials");
                   alert.showAndWait();
                   gameBoard.setDisable(true);
                   LocalDateTime endTime = LocalDateTime.now();
                   LocalDateTime startTime=game.getStartTime();
                   Duration duration = Duration.between(startTime, endTime);
                   game.setStatus(Game.gameStatus.WON);
                   game.setDuration(duration);
                   game.setTrials(noTrials);
                   try{
                   services.updateGame(game);}
                   catch(GameException e){
                       alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("You lost");
                       alert.setHeaderText(null);
                       alert.setContentText("You lost");
                       alert.showAndWait();
                   }
               }else if(noTrials==3){
                   boardData.get(game.getConfig().getRow())[game.getConfig().getColumn()] = animalImage;
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("You lost");
                   alert.setHeaderText(null);
                   alert.setContentText("You lost");
                   alert.showAndWait();
                   gameBoard.setDisable(true);
                   LocalDateTime endTime = LocalDateTime.now();
                   LocalDateTime startTime=game.getStartTime();
                   Duration duration = Duration.between(startTime, endTime);
                   game.setStatus(Game.gameStatus.LOST);
                   game.setDuration(duration);
                   game.setTrials(-1);
                   try{
                       services.updateGame(game);}
                   catch(GameException e){
                       alert = new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("You lost");
                       alert.setHeaderText(null);
                       alert.setContentText("You lost");
                       alert.showAndWait();
                   }
               }else{
                   String hint=giveHint(column,row);
                   hintLabel.setText(hint);
               }
               gameBoard.refresh();  // Force UI update to reflect style change
           }
       });

       gameBoard.setItems(boardData);
   }
    private ObservableList<Object[]> boardData;

    private void setupBoard() {
        gameBoard.getSelectionModel().clearSelection();
        selectedCellPosition = null;
        boardData = FXCollections.observableArrayList();
        for (int row = 0; row < 3; row++) {
            Object[] rowData = new Object[4];
            Arrays.fill(rowData, null);
            boardData.add(rowData);
        }
        gameBoard.setItems(boardData);
        gameBoard.refresh();
    }

    public void pressNewGame(ActionEvent actionEvent) {
        try{
        Game newGame= this.services.startGame(player);
        this.game = newGame;
        animalImage = new Image("file:/C:/Users/40741/OneDrive/Pictures/iss-app/elefant-adorabil-de-colorat.jpg");
        initialize();
        noTrials=0;
        setupBoard();
        hintLabel.setText("Hints will show up here");
        gameBoard.setDisable(false);
        }catch (GameException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void gameWon(Game game) throws GameException {
        Platform.runLater(()->{
            initModel();
            ranking.setItems(model);
            if(game.getId()==this.game.getId()){
                int position = model.indexOf(game)+1;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You won");
                alert.setHeaderText(null);
                alert.setContentText("Your current ranking: "+ position);
                alert.showAndWait();
            }
        });


    }

    @Override
    public void wordGame(WordGame wordGame) throws GameException {

    }
}
