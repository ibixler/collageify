package collageify;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.transport.http.*;

import collageify.api.TestClass;
import collageify.db.SQLTime;
import collageify.musicService.Playing;
import collageify.user.User;
import collageify.api.CorsFilter;
import collageify.musicService.SpClientCredentials;



public class App {
    public static void main(String[] args) throws Exception {
        SQLTime time = new SQLTime();
        System.out.println(time.getDate());
        System.out.println(time.getTime());
        Playing song = new Playing(0, "ibutts", 30000, "https://open.spotify.com/track/6lrDckuosGpwEHtm1hHBcf?si=c8286ba2785f4196", "Gorillaz", "Demon Days", "November Has Come",55, 165000);
        System.out.println(time.getTime());
        song.UpdateProgress(165000);
        song.UpdateDB();
        SpClientCredentials.clientCredentials_Sync("1DXD0wVXXHwUYo9AXbcMMI");


        /* User user = new User("ldoggs","lawhitley@gmail.com", "somethingEmbarassing", "Laurel", "Whitley");
        user.UpdateDB();
         */
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(TestClass.class);


        factoryBean.setResourceProvider(TestClass.class, new SingletonResourceProvider(new TestClass()));
        factoryBean.setProvider(new CorsFilter());

        factoryBean.setAddress("http://localhost:8080/");

        factoryBean.create();
        System.out.println("Server started at http://localhost:80/");

    }

}
