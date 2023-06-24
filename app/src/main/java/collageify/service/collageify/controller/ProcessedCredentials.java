package collageify.service.collageify.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

public class ProcessedCredentials {
    private UUID uuid;
    private Integer id;
    private String refreshToken;
    private String accessToken;
    private Integer userID;
    private Date accessTokenExpDate;
    private Time accessTokenExpTime;

    public ProcessedCredentials(UUID uuid,Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.uuid = uuid;
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }
    //getters
    public UUID getUuid() { return uuid; }
    public Integer getId() { return id; }
    public String getRefreshToken() { return refreshToken; }
    public String getAccessToken() { return accessToken; }
    public Integer getUserID() { return userID; }
    public Date getAccessTokenExpDate() { return accessTokenExpDate; }
    public Time getAccessTokenExpTime() { return accessTokenExpTime; }

    //setters
/*    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setId(Integer id) { this.id = id; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public void setAccessTokenExpDate(Date accessTokenExpDate) { this.accessTokenExpDate = accessTokenExpDate; }
    public void setAccessTokenExpTime(Time accessTokenExpTime) { this.accessTokenExpTime = accessTokenExpTime; }*/


}
