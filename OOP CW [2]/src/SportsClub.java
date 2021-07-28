import java.io.Serializable;

public abstract class SportsClub implements Serializable {
    private final int clubId;
    private String clubName;
    private String location;



    public SportsClub(int clubId, String clubName, String location) {

        this.clubId = clubId;
        this.clubName = clubName;
        this.location = location;
    }

    public int getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

//    public void setClubName(String clubName) {
//        this.clubName = clubName;
//    }

    public String getLocation() {
        return location;
    }

//    public void setLocation(String location) {
//        this.location = location;
//    }

}
