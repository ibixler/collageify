package collageify.web.controller;

import collageify.web.db.SQLAccess;
import collageify.web.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("/callback")
public class CallbackController {
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback/get-refresh-token/");
    private String code = "";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getenv("SP_CID"))
            .setClientSecret(System.getenv("SP_S"))
            .setRedirectUri(redirectUri)
            .build();
    @Autowired
    private UserRepository usrRepo;

    /**
     * This function does not consume anything and is a resd endpoint for the client
     *
     * @return produces a uri that allows a user to grant collageify access to their data
     */
    @GetMapping("/loading")
    @ResponseBody
    public String spotifyLogin() {
        AuthorizationCodeUriRequest spotifyAuthCodeRequest = spotifyApi.authorizationCodeUri()
                .scope("user-read-playback-state, user-read-currently-playing")
                .show_dialog(true)
                .build();
        final URI uri = spotifyAuthCodeRequest.execute();
        return uri.toString();

    }
    @GetMapping("/get-refresh-token/")
    public String getSpotifyRefreshToken(@RequestParam("code") String usercode, HttpServletResponse response) throws IOException{
        code = usercode;
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("expires in:" + authorizationCodeCredentials.getExpiresIn());
            /*
              the code below this is realy bad
              and  needs to be (refactored?) whatever
              just make this a private class that takes the arguments
              and gives them to the playing side of the server
             */
            SQLAccess sql = new SQLAccess();
            sql.estConnection();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException | SQLException e){
            System.out.println("error:" + e.getMessage());
        }


        response.sendRedirect("http://localhost:4200/");
        String token = spotifyApi.getRefreshToken();
        System.out.println(token);
        return spotifyApi.getAccessToken();
    }






}