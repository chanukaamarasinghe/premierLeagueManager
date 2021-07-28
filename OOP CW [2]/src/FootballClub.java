public class FootballClub extends SportsClub {
    private int wins;
    private int defeats;
    private int draws;
    private int goalsScored;
    private int goalsReceived;
    private int points;
    private  int goalDef;

    public FootballClub(int clubId, String clubName, String location, int wins, int defeats, int draws, int goalsScored, int goalsReceived) {
        super(clubId,clubName,location);
        this.wins = wins;
        this.defeats = defeats;
        this.draws = draws;
        this.goalsScored = goalsScored;
        this.goalsReceived = goalsReceived;
        this.points = generatePoints(wins,draws);
        this.goalDef = goalsScored - goalsReceived;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public void setGoalsReceived(int goalsReceived) {
        this.goalsReceived = goalsReceived;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int generatePoints(int wins, int draws){
        return wins * 3 + draws;
    }

    public int getGoalDef() {
        return goalDef;
    }

    public void showClubStatics(){
        System.out.println(
                "Club Name : " + getClubName() +"\n"
                + "City : " + getLocation() +"\n"
                + "Number of Wins : " + getWins() +"\n"
                + "Number of Defeats : " + getDefeats() +"\n"
                + "Number of Draws Matches : " + getDraws() +"\n"
                + "Total Scored Goals : " + getGoalsScored() +"\n"
                + "Total Received Goals : " + getGoalsReceived() +"\n"
                + "Total Points : " + getPoints() +"\n"
                + "ID : " + getClubId() +"\n"
        );
    }
}
