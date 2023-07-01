package collageify.collageify.controller;

import collageify.web.db.IDBAccess;
import collageify.web.db.SQLAccess;
import collageify.web.exceptions.NoSPApiException;

import java.sql.*;
import java.util.Optional;

public class SqlController extends SQLAccess implements IDBAccess  {
    private Connection connect = null;
    SqlController(){
        super();
    }
    @Override
    public void estConnection() throws SQLException {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection(System.getenv("DB_ADDRESS"), System.getenv("DB_USER"), System.getenv("DB_PW"));

        } catch (Exception e) {
            throw e;
        }
    }
    //this is like optional hell
    @Override
    public Optional<ResultSet> getAuthCredentials() throws SQLException, NoSPApiException {
        estConnection();
        try{
            PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM spotify_credentials");
            //preparedStatement.setInt(1,(int) userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.of(resultSet);
            /*if(resultSet.next()){
                System.out.println(resultSet.getInt("id"));
                *//*return  Optional.of(new Credentials((Integer) resultSet.getInt("id"),
                        resultSet.getString("refresh_token"),
                        resultSet.getString("access_token"),
                        (Integer) resultSet.getInt("user_id"),
                        resultSet.getDate("access_token_exp_date"),
                        resultSet.getTime("access_token_exp_time")));*//*
            } else {
                throw new NoSPApiException("");
            }*/


        } catch(Exception e) {
            throw e;
        } finally {
            close();
        }


    }

}