package core;

import commands.*;
import listener.MentionListener;
import listener.MessageListener;
import listener.CommandListener;
import net.dv8tion.jda.api.*;
import org.json.JSONException;
import org.json.JSONObject;
import util.SECRETS;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static util.SECRETS.VERSION;

public class Main {

    public static String user = "CENSORED :P";
    public static String password = "CENSORED :P";
    public static String urlempty = "jdbc:mysql://localhost/";

    static JDABuilder builder;
    public static String Footer = "© Gott der Bots v." + VERSION;
    public static String Footer2 = "http://www.baggerstation.de/testseite/bots/Gott.png";

    public static void main(String[] Args) {
        builder = new JDABuilder(AccountType.BOT).setToken(SECRETS.Token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new CommandListener(), new MessageListener(), new MentionListener());

        commandHandler.commands.put("help", new CommandHelp());
        commandHandler.commands.put("bots", new CommandBots());
        commandHandler.commands.put("register", new CommandRegister());
        commandHandler.commands.put("profile", new CommandProfile());
        commandHandler.commands.put("miner", new CommandMiner());

        GameAnimator.start();

        try {
             builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
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
