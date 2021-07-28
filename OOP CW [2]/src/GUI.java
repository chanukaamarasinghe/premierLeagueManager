import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class GUI extends Application {
    ArrayList<FootballClub> footballClubs = new ArrayList<>();
    ArrayList<PlayedMatch> playedMatches = new ArrayList<>();
    PremierLeagueManager leagueManager = new PremierLeagueManager();

    ObservableList<Object> clubList = FXCollections.observableArrayList();
    ObservableList<Object> matchList = FXCollections.observableArrayList();
    ObservableList<Object> search = FXCollections.observableArrayList();

    TextField dayTxt, monthTxt, yearTxt;
    TableView<Object> matchHistoryTable;
    int day, month, year;

    public ObservableList<Object> getDataFromFootballClubs() {
        try {
            FileInputStream input = new FileInputStream("save.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(input);
            footballClubs = (ArrayList<FootballClub>) objectInputStream.readObject();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("File created");
        }catch (NullPointerException e){
            System.out.println("No clubs listed yet");
        }
        clubList.clear();
        Comparator<FootballClub> sortByPoints = Comparator.comparing(FootballClub::getPoints).thenComparing(FootballClub::getGoalDef);
        footballClubs.sort(sortByPoints.reversed());
        clubList.addAll(footballClubs);
        return clubList;
    }
    public ObservableList<Object> getDataFromPlayedMatches(){
        try {
            FileInputStream inputMatchHistory = new FileInputStream("Match History.ser");
            ObjectInputStream objectInputMatchHistory = new ObjectInputStream(inputMatchHistory);
            playedMatches = (ArrayList<PlayedMatch>) objectInputMatchHistory.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("File created");
        }catch (NullPointerException e){
            System.out.println("No match played yet");
        }
        matchList.clear();
        matchList.addAll(playedMatches);
        return matchList;
    }


    Scene mainMenu;
    Scene leagueTable;
    Scene matchHistory;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create Main Menu

        AnchorPane mainMenuPane = new AnchorPane();
        primaryStage.setTitle("Premier League Manager");

        Label welcomeLbl = new Label("Premier League Manager");
        welcomeLbl.setPrefWidth(240);
        welcomeLbl.setPrefHeight(20);
        welcomeLbl.setLayoutX(180);
        welcomeLbl.setLayoutY(30);
        welcomeLbl.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Button premierLeagueTableBtn = new Button("Premier League Table");
        premierLeagueTableBtn.setLayoutX(200);
        premierLeagueTableBtn.setLayoutY(100);
        premierLeagueTableBtn.setPrefWidth(200);
        premierLeagueTableBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(leagueTable);
            }
        });

        Button matchHistoryBtn = new Button("Match History");
        matchHistoryBtn.setLayoutX(200);
        matchHistoryBtn.setLayoutY(140);
        matchHistoryBtn.setPrefWidth(200);
        matchHistoryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(matchHistory);
            }
        });
        Button generateMatch = new Button("Generate Match");
        generateMatch.setLayoutX(200);
        generateMatch.setLayoutY(180);
        generateMatch.setPrefWidth(200);
        generateMatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                leagueManager.generateMatch();
                getDataFromFootballClubs();
                getDataFromPlayedMatches();
            }
        });

        Button closeBtn = new Button("Close");
        closeBtn.setLayoutX(200);
        closeBtn.setLayoutY(230);
        closeBtn.setPrefWidth(200);
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        mainMenuPane.getChildren().addAll(welcomeLbl,premierLeagueTableBtn,matchHistoryBtn,generateMatch,closeBtn);
        mainMenu = new Scene(mainMenuPane,600,300);
        mainMenu.getStylesheets().addAll("Style.css");
        primaryStage.setScene(mainMenu);
        primaryStage.show();

        // create premier league Table

        AnchorPane leagueTablePane = new AnchorPane();
        TableView premierLeagueTable = new TableView();
        premierLeagueTable.setLayoutX(20);
        premierLeagueTable.setLayoutY(20);

        TableColumn<Object, String> clubNameClm = new TableColumn<>("Club Name");
        clubNameClm.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        clubNameClm.setPrefWidth(100);

        TableColumn<Object, String> pointsClm = new TableColumn<>("Points");
        pointsClm.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsClm.setPrefWidth(100);

        TableColumn<Object, String> winsClm = new TableColumn<>("Wins");
        winsClm.setCellValueFactory(new PropertyValueFactory<>("wins"));
        winsClm.setPrefWidth(100);

        TableColumn<Object, String> defeatClm = new TableColumn<>("Defeats");
        defeatClm.setCellValueFactory(new PropertyValueFactory<>("defeats"));
        defeatClm.setPrefWidth(100);

        TableColumn<Object, String> drawsClm = new TableColumn<>("Draws");
        drawsClm.setCellValueFactory(new PropertyValueFactory<>("draws"));
        drawsClm.setPrefWidth(100);

        TableColumn<Object, String> goalsScoredClm = new TableColumn<>("Goals Scored");
        goalsScoredClm.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        goalsScoredClm.setPrefWidth(130);

        TableColumn<Object, String> goalsReceivedClm = new TableColumn<>("Goals Received");
        goalsReceivedClm.setCellValueFactory(new PropertyValueFactory<>("goalsReceived"));
        goalsReceivedClm.setPrefWidth(130);

        premierLeagueTable.setItems(getDataFromFootballClubs());
        premierLeagueTable.getColumns().addAll(clubNameClm, pointsClm, winsClm, defeatClm, drawsClm, goalsScoredClm, goalsReceivedClm);

        Button sortByMatchWinsBtn = new Button("Sort By Match Wins");
        sortByMatchWinsBtn.setPrefWidth(150);
        sortByMatchWinsBtn.setLayoutX(50);
        sortByMatchWinsBtn.setLayoutY(450);
        sortByMatchWinsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clubList.clear();
                Comparator<FootballClub> sortByMatchWins = Comparator.comparing(FootballClub::getWins);
                footballClubs.sort(sortByMatchWins.reversed());
                clubList.addAll(footballClubs);
            }
        });

        Button sortByGoalsScoredBtn = new Button("Sort by Scored Goals");
        sortByGoalsScoredBtn.setPrefWidth(180);
        sortByGoalsScoredBtn.setLayoutX(250);
        sortByGoalsScoredBtn.setLayoutY(450);
        sortByGoalsScoredBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clubList.clear();
                Comparator<FootballClub> sortByScoredGoals = Comparator.comparing(FootballClub::getGoalsScored);
                footballClubs.sort(sortByScoredGoals.reversed());
                clubList.addAll(footballClubs);
            }
        });

        Button sortByPointsBtn = new Button("Sort By Points");
        sortByPointsBtn.setPrefWidth(150);
        sortByPointsBtn.setLayoutX(480);
        sortByPointsBtn.setLayoutY(450);
        sortByPointsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clubList.clear();
                Comparator<FootballClub> sortByPoints = Comparator.comparing(FootballClub::getPoints).thenComparing(FootballClub::getGoalDef);
                footballClubs.sort(sortByPoints.reversed());
                clubList.addAll(footballClubs);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setLayoutY(500);
        backBtn.setLayoutX(680);
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainMenu);
            }
        });

        leagueTablePane.getChildren().add(premierLeagueTable);
        leagueTablePane.getChildren().addAll(sortByGoalsScoredBtn, sortByMatchWinsBtn, sortByPointsBtn,backBtn);

        leagueTable = new  Scene(leagueTablePane, 800, 550);
        leagueTable.getStylesheets().addAll("Style.css");

//creating match history table
        AnchorPane matchHistoryPane = new AnchorPane();

        Label matchHistoryLbl = new Label("Match History");
        matchHistoryLbl.setPrefWidth(135);
        matchHistoryLbl.setPrefHeight(25);
        matchHistoryLbl.setLayoutY(40);
        matchHistoryLbl.setLayoutX(385);
        matchHistoryLbl.setStyle("-fx-font: normal 22px 'serif';");

        matchHistoryTable = new TableView();
        matchHistoryTable.setLayoutX(30);
        matchHistoryTable.setLayoutY(100);

        TableColumn<Object,String> dateClm = new TableColumn<>("Date");
        dateClm.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateClm.setPrefWidth(110);
        // dateClm.setStyle("-fx-alignment: center;");

        TableColumn<Object,String > teamOneClm = new TableColumn<>("Home");
        teamOneClm.setCellValueFactory(new PropertyValueFactory<>("homeClubName"));
        teamOneClm.setPrefWidth(120);
        //teamOneClm.setStyle("-fx-alignment: center;");

        TableColumn<Object,String > teamTwoClm = new TableColumn<>("Away");
        teamTwoClm.setCellValueFactory(new PropertyValueFactory<>("awayClubName"));
        teamTwoClm.setPrefWidth(120);
        //teamTwoClm.setStyle("-fx-alignment: center;");

        TableColumn<Object,String > teamOneGoalsClm = new TableColumn<>("Home team Goals");
        teamOneGoalsClm.setCellValueFactory(new PropertyValueFactory<>("homeGoals"));
        teamOneGoalsClm.setPrefWidth(140);
        //teamOneGoalsClm.setStyle("-fx-alignment: center;");

        TableColumn<Object,String > teamTwoGoalsClm = new TableColumn<>("Away team Goals");
        teamTwoGoalsClm.setCellValueFactory(new PropertyValueFactory<>("awayGoals"));
        teamTwoGoalsClm.setPrefWidth(140);
        //teamTwoGoalsClm.setStyle("-fx-alignment: center;");

        matchHistoryTable.setItems(getDataFromPlayedMatches());
        matchHistoryTable.getColumns().addAll(dateClm,teamOneClm,teamOneGoalsClm,teamTwoClm,teamTwoGoalsClm);



        Label dateLbl = new Label("Search by Date");
        dateLbl.setPrefHeight(20);
        dateLbl.setPrefWidth(110);
        dateLbl.setLayoutX(680);
        dateLbl.setLayoutY(100);
        dateLbl.setStyle("-fx-font: normal 18px 'serif';");


        dayTxt = new TextField();
        dayTxt.setLayoutX(700);
        dayTxt.setLayoutY(146);
        dayTxt.setPrefWidth(50);
        dayTxt.setPromptText("DD");

        monthTxt = new TextField();
        monthTxt.setLayoutX(760);
        monthTxt.setLayoutY(146);
        monthTxt.setPrefWidth(50);
        monthTxt.setPromptText("MM");

        yearTxt = new TextField();
        yearTxt.setLayoutX(820);
        yearTxt.setLayoutY(146);
        yearTxt.setPrefWidth(80);
        yearTxt.setPromptText("YYYY");

        Button searchBtn = new Button("Search");
        searchBtn.setPrefHeight(25);
        searchBtn.setPrefWidth(130);
        searchBtn.setLayoutX(680);
        searchBtn.setLayoutY(200);
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchByDate();
            }
        });

        Button clearBtn = new Button("Clear Search");
        clearBtn.setPrefHeight(25);
        clearBtn.setPrefWidth(130);
        clearBtn.setLayoutX(820);
        clearBtn.setLayoutY(200);
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                matchHistoryTable.setItems(getDataFromPlayedMatches());
                dayTxt.setText("");
                monthTxt.setText("");
                yearTxt.setText("");
            }
        });


        Button backBtnHistory = new Button("Back");
        backBtnHistory.setPrefHeight(25);
        backBtnHistory.setPrefWidth(90);
        backBtnHistory.setLayoutX(800);
        backBtnHistory.setLayoutY(460);
        backBtnHistory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(mainMenu);
            }
        });


        matchHistoryPane.getChildren().addAll(matchHistoryLbl,matchHistoryTable,dateLbl,dayTxt, monthTxt,yearTxt,
                searchBtn,clearBtn,backBtnHistory);


        matchHistory = new Scene(matchHistoryPane,980,550);
        matchHistory.getStylesheets().addAll("Style.css");
    }

    public void searchByDate(){
        search.clear();
        try {
            day = Integer.parseInt(dayTxt.getText());
            month = Integer.parseInt(monthTxt.getText());
            year = Integer.parseInt(yearTxt.getText());
        }catch (Exception e){
            System.out.println("Please enter a date");
        }
        for (PlayedMatch matchResult: playedMatches){
            if (day == matchResult.getDate().getDay() && month == matchResult.getDate().getMonth() &&
                    year == matchResult.getDate().getYear()){
                search.add(matchResult);
            }
        }
        matchHistoryTable.setItems(search);
    }

    public static void main (String[]args){
        launch(args);
    }
}
