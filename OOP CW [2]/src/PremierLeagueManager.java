import java.io.*;
import java.util.*;

public class PremierLeagueManager implements LeagueManager{


    ArrayList<FootballClub> clubs = new ArrayList<>();
    ArrayList<PlayedMatch> matchHistory = new ArrayList<>();
    Scanner userInput = new Scanner(System.in);

    {
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            clubs = (ArrayList<FootballClub>)objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File save.ser created");
        }
    }
    {
        try {
            FileInputStream fileInputStreamMatchHistory = new FileInputStream("matchHistory.ser");
            ObjectInputStream objectInputStreamMatchHistory = new ObjectInputStream(fileInputStreamMatchHistory);
            matchHistory = (ArrayList<PlayedMatch>)objectInputStreamMatchHistory.readObject();
            objectInputStreamMatchHistory.close();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("File Match History.ser created");
        }
    }

    static void serialize(ArrayList<FootballClub> footballClubs) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("save.ser", false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(footballClubs);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void serializeMatchHistory(ArrayList<PlayedMatch> matchHistory){
        try {
            FileOutputStream fileOutputStreamMatchHistory = new FileOutputStream("Match History.ser",false);
            ObjectOutputStream objectOutputStreamMatchHistory = new ObjectOutputStream(fileOutputStreamMatchHistory);
            objectOutputStreamMatchHistory.writeObject(matchHistory);
            objectOutputStreamMatchHistory.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void mainMenu(){

        while (true){
            String menu = "Enter 1 to Create a Club \n"
                    + "Enter 2 to Relegate a Club \n"
                    + "Enter 3 to Display League Table \n" + "Enter 4 to Display Statics of a Club \n" + "Enter 5 to add a match \n"
                    + "Enter 6 to Open GUI \n"+ "Enter 0 to Exit \n";

            System.out.println(menu);
            System.out.println("Enter Your Response : ");
            String choice = userInput.next();
                switch (choice){
                    case "1":
                        createClub();
                        break;
                    case "2":
                        relegateClub();
                        break;
                    case "3":
                        displayLeagueTable();
                        break;
                    case "4":
                        showStatistics();
                        break;
                    case "5":
                        addPlayedMatch();
                        break;
                    case "6":
                        openGUI();
                        break;
                    case "0":
                        System.exit(0);
                    default:
                        System.out.println("Please Re-Enter a valid Response\n ");
                        break;
                }
        }
    }

    public void createClub(){
        System.out.println("Club Name : ");
        String clubName = userInput.next();
        System.out.println("Club City : ");
        String clubLocation = userInput.next();
        System.out.println("Number of Wins : ");
        int wins = userInput.nextInt();
        System.out.println("Number of Defeats : ");
        int defeats = userInput.nextInt();
        System.out.println("Number of Draw Matches : ");
        int draws = userInput.nextInt();
        System.out.println("Total Scored Goals : ");
        int goalsScored = userInput.nextInt();
        System.out.println("Total Received Goals : ");
        int goalsReceived = userInput.nextInt();
        System.out.println("Club ID : ");
        int clubId = userInput.nextInt();
        System.out.println(clubName + " Club created\n");
        FootballClub footballClub = new FootballClub(clubId,clubName,clubLocation,wins,defeats,draws,goalsScored,goalsReceived);
        clubs.add(footballClub);
        serialize(clubs);

    }

    public void relegateClub(){
        if (clubs.size() > 0){
            System.out.println("Choose the Club to Relegate: ");
            String clubName = userInput.next().toLowerCase();
            int index = 0;
            for (FootballClub club : clubs){
                index++;
                if (club.getClubName().toLowerCase().equals(clubName)){
                    clubs.remove(club);
                    serialize(clubs);
                    System.out.println(clubName + " relegated from the league");
                    return;
                }else if (index >= clubs.size()){
                    System.out.println(clubName.toUpperCase() + " does not exist try again...\n");
                    return;
                }

            }
        }else System.out.println("No Clubs in the list \n");

    }

    public void showStatistics(){
        if (clubs.size() > 0){
            System.out.println("Choose Club to Show statics");
            String clubName = userInput.next().toLowerCase();
            int index = 0;
            for (FootballClub club : clubs){
                index++;
                if (club.getClubName().toLowerCase().equals(clubName)){
                    club.showClubStatics();
                    return;
                }else if (index >= clubs.size()){
                    System.out.println(clubName.toUpperCase() + " does not exist try again...\n");
                    return;
                }

            }
        }else System.out.println("No clubs in the list");

    }

    public void addPlayedMatch(){
        if (clubs.size() <= 1){
            System.out.println("You need at least two clubs to Play...");
        } else {
            System.out.println("Choose the clubs : ");
            int i = 1;
            int arraySize = clubs.size();
            System.out.println("Array Size : "+arraySize);
            for (FootballClub club : clubs){
                System.out.println(i + " : "+club.getClubName());
                i++;
            }
            System.out.println("Enter the Club by Number\n" +
                    "Home : ");
            int homeClub = userInput.nextInt() - 1;
            if (homeClub +1 > clubs.size()){
                System.out.println("Invalid input Check again\n");
                return;
            }
            System.out.println("Goals scored by Home team : ");
            int homeGoals = userInput.nextInt();
            System.out.println("Enter the Club by Number\n" +
                    "Away : ");
            int awayClub = userInput.nextInt() - 1;
            System.out.println(awayClub);
            if (awayClub + 1 > clubs.size()){
                System.out.println("Invalid Input Check again\n");
                return;
            }
            System.out.println("Goals scored by Away team : ");
            int awayGoals = userInput.nextInt();

            // add Date
            System.out.println("Enter the Day : ");
            int day = userInput.nextInt();
            System.out.println("Enter Month : ");
            int month = userInput.nextInt();

            matchDecisions(homeClub,homeGoals,awayClub,awayGoals);
            Date date = new Date(day,month);
            PlayedMatch playedMatch = new PlayedMatch(clubs.get(homeClub).getClubName(),clubs.get(awayClub).getClubName(),homeGoals,awayGoals,date);
            matchHistory.add(playedMatch);
            serialize(clubs);
            serializeMatchHistory(matchHistory);
            System.out.println("match added to the system and statics updated");
        }

    }

    public void generateMatch(){
        if (clubs.size() <= 1){
            System.out.println("You need at least two clubs to Play...");
        } else {
            Random randomNum = new Random();
            int homeClub;
            int awayClub;

            homeClub = randomNum.nextInt(clubs.size());
            do {
                awayClub = randomNum.nextInt(clubs.size());
            }while (homeClub == awayClub);

            int homeGoals = randomNum.nextInt(9);
            int awayGoals = randomNum.nextInt(9);

            //generate Date
            int day = randomNum.nextInt(29) + 1;
            int month = randomNum.nextInt(11) + 1;

            matchDecisions(homeClub,homeGoals,awayClub,awayGoals);
            Date date = new Date(day,month);
            PlayedMatch playedMatch = new PlayedMatch(clubs.get(homeClub).getClubName(),clubs.get(awayClub).getClubName(),homeGoals,awayGoals,date);
            matchHistory.add(playedMatch);
            serializeMatchHistory(matchHistory);
        }

    }

    private void matchDecisions(int homeClub, int homeGoals,int awayClub,int awayGoals){
        FootballClub home = clubs.get(homeClub);
        FootballClub away = clubs.get(awayClub);

        home.setGoalsScored(home.getGoalsScored() + homeGoals);
        home.setGoalsReceived(home.getGoalsReceived() + awayGoals);
        away.setGoalsScored(away.getGoalsScored() + awayGoals);
        away.setGoalsReceived(away.getGoalsReceived() + homeGoals);

        if (homeGoals == awayGoals){
            home.setDraws(home.getDraws()+1);
            away.setDraws(away.getDraws()+1);
        }else if (homeGoals > awayGoals){
            home.setWins(home.getWins() + 1);
            away.setDefeats(away.getDefeats() + 1);
        }else {
            home.setDefeats(home.getDefeats() + 1);
            away.setWins(away.getWins() + 1);
        }

        home.generatePoints(home.getWins(),home.getDraws());
        away.generatePoints(away.getWins(),away.getDraws());

        serialize(clubs);
        serializeMatchHistory(matchHistory);
    }
    
    public void displayLeagueTable(){

        Comparator<FootballClub> sortByPoints = Comparator.comparing(FootballClub::getPoints).thenComparing(FootballClub::getGoalDef);
        clubs.sort(sortByPoints);
        Collections.reverse(clubs);

        System.out.println("----------------------------STATISTICS TABLE----------------------------------");
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("%10s %5s %5s %5s %5s %10s %10s", "| CLUB NAME |", "POINTS |", "WINS |", "DEFEATS |", "DRAWS |", "GOALS SCORED |", "GOALS RECEIVED |");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------");

        for(FootballClub club: clubs){
            System.out.format("%5s %10s %10s %7s %7s %10s %10s",
                    club.getClubName(), club.getPoints(), club.getWins(), club.getDefeats(), club.getDraws(),
                    club.getGoalsScored(), club.getGoalsReceived());
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");
    }
    public void openGUI(){
        GUI.main(null);
    }

}
