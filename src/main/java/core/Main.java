package core;

import commands.*;
import listener.Mention;
import listener.Message;
import listener.commandListener;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONException;
import org.json.JSONObject;
import util.SECRETS;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import static util.SECRETS.VERSION;

public class Main {

    public static int Timer=0;
<<<<<<< HEAD
    public static String user="CENSORED :P";
    public static String password="CENSORED :P";
    public static String urlempty="jdbc:mysql://localhost/";

    public static JDABuilder builder;
    public static String Footer = "© Gott der Bots v." + VERSION;
    public static String Footer2 = "http://www.baggerstation.de/testseite/bots/Gott.png";
=======
    public static String user="SQL_username";
    public static String password="SQL_password";
    public static String urlempty="SQL_url";

    public static JDABuilder builder;
    public static String Footer = "© Gott Bot v." + VERSION;
    public static String Footer2 = "http://bigbotnetwork.de/logo.png";
    public static Connection con;
    public static PreparedStatement pst;
    public static ResultSet rs;
>>>>>>> c2de5faff3a99543f366808fc4c076150bd22182

    public static void main(String[] Args) {
        builder = new JDABuilder(AccountType.BOT).setToken(SECRETS.Token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new commandListener());
        builder.addEventListener(new Message());
        builder.addEventListener(new Mention());



        commandHandler.commands.put("help", new help());
        commandHandler.commands.put("bots", new bots());
        commandHandler.commands.put("register", new register());
        commandHandler.commands.put("profile", new profile());
        commandHandler.commands.put("miner", new miner());

        GameAnimator.start();

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
