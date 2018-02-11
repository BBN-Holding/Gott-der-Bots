package core;

import commands.*;
import listener.Mention;
import listener.Message;
import listener.commandListener;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import util.SECRETS;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static util.SECRETS.VERSION;

public class Main {

    public static int Timer=0;
    public static String user="SQL_username";
    public static String password="SQL_password";
    public static String urlempty="SQL_url";

    public static JDABuilder builder;
    public static String Footer = "© Gott Bot v." + VERSION;
    public static String Footer2 = "http://bigbotnetwork.de/logo.png";
    public static Connection con;
    public static PreparedStatement pst;
    public static ResultSet rs;

    public static void main(String[] Args) {
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        builder = new JDABuilder(AccountType.BOT).setToken(SECRETS.Token).setAutoReconnect(true).setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new commandListener());
        builder.addEventListener(new Message());
        builder.addEventListener(new Mention());

        commandHandler.commands.put("help", new help());
        commandHandler.commands.put("bank", new bank());
        commandHandler.commands.put("bots", new bots());
        commandHandler.commands.put("register", new register());
        commandHandler.commands.put("profile", new profile());
        commandHandler.commands.put("broadcast", new broadcast());

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

}
