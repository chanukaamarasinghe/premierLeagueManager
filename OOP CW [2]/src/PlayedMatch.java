import java.io.Serializable;

public class PlayedMatch implements Serializable {
    private String homeClubName;
    private String awayClubName;
    private int homeGoals;
    private int awayGoals;
    Date date;

    public PlayedMatch(String homeClubName, String awayClubName, int homeGoals, int awayGoals, Date date) {
        this.homeClubName = homeClubName;
        this.awayClubName = awayClubName;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.date = date;
    }

    public String getHomeClubName() {
        return homeClubName;
    }

    public void setHomeClubName(String homeClubName) {
        this.homeClubName = homeClubName;
    }

    public String getAwayClubName() {
        return awayClubName;
    }

    public void setAwayClubName(String awayClubName) {
        this.awayClubName = awayClubName;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
