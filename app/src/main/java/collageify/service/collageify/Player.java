package collageify.service.collageify;
import collageify.db.SQLAccess;
import collageify.exceptions.JSONNotPresent;
import collageify.exceptions.NoSPApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysql.cj.PreparedQuery;
import java.sql.SQLException;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
public class Player {

    private Integer userID;
    private String username;

    private Integer progressMS;
    private Double progressPercent;

    private String spURI;
    private String artistName;
    private String albumName;
    private String trackName;
    private Integer popularity;
    private Integer durationMS;

    private Boolean enoughPlayed;

    private Boolean playing;


    public SPAccess spAccess;

    public Player(Integer userID) throws NoSPApiException, SQLException {
        this.spAccess = new SPAccess(userID);
    }

    public void addInfo(Integer userID, String username, Integer progressMS, String spURI, String artistName, String albumName, String trackName, Integer popularity, Integer durationMS) throws NoSPApiException {
        this.userID = userID;
        this.username = username;
        this.progressMS = progressMS;
        this.spURI = spURI;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackName = trackName;
        this.popularity = popularity;
        this.durationMS = durationMS;
        this.progressPercent = (double) (progressMS / durationMS);
        if(this.progressPercent >= 0.66d){
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
    }

    public void initProgress(Optional<JsonNode> node){
        if(node.isPresent()){
            this.progressMS = node.get().get("progress_ms").asInt();
            this.durationMS = node.get().get("item").get("duration_ms").asInt();
        }

    }

    public void UpdateProgress(Integer newProgressMS){
        this.progressMS = newProgressMS;
        this.progressPercent = (double) (progressMS / durationMS);
        if(this.progressPercent >= 0.66d){
            this.enoughPlayed = true;
        } else{
            this.enoughPlayed = false;
        }
    }

    public void UpdateDB() throws Exception{
        if(enoughPlayed == true){
            SQLAccess  sql = new SQLAccess();
            try{
                sql.estConnection();
                sql.addPlayed(userID, username, spURI, artistName, albumName, trackName, popularity, durationMS);
            } catch (Exception e){
                throw e;
            }
        }
    }

    private Optional<JsonNode>  responseToJson(Optional<String> response) throws JsonProcessingException, JSONNotPresent {
        if(response.isPresent()){
            return Optional.ofNullable(new ObjectMapper().readTree(response.get()));
        } else {
            return Optional.empty();
        }
    }

    public void run() throws Exception, NoSPApiException, JSONNotPresent{
        while (this.spAccess.credentials.get().isTokenValid()){


            System.out.println("waiting");
            if(Optional.ofNullable(this.spAccess.requestData()).isPresent()){
                System.out.println(responseToJson(this.spAccess.requestData()).get());


                System.out.println(responseToJson(this.spAccess.requestData()).get().get("item").get("name").asText());
                this.initProgress(responseToJson(this.spAccess.requestData()));
                System.out.println(this.durationMS);
                System.out.println(this.progressMS);


                //System.out.println(this.spAccess.requestData());

            } else {
                System.out.println("i got nothing");
            } try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                // Handle the InterruptedException if needed
            }
        }

    }




    /* void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName(String username){
        this.username = username;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserID(Integer userID){
        this.userID = userID;
    }
    void setUserName; */


}