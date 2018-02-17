package commands;

import com.oracle.tools.packager.IOUtils;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;

import static core.Main.*;

public class miner implements Command {

    Member user;
    String useruser;
    Long withdrawn;
    Long total;
    Long Hashes;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (!args[0].equalsIgnoreCase("withdraw")) {
            Thread t = new Thread(() -> {
                try {
                    useruser = args[0].replace("<", "").replace("@", "").replace(">", "").replace("!", "");
                    user = event.getGuild().getMemberById(useruser);

                } catch (ArrayIndexOutOfBoundsException e) {
                    user = event.getMember();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {

                    JSONObject json = readJsonFromUrl("https://api.coinhive.com/user/balance?name=" + user.getUser().getId() + "&secret=M5T84quvGlAuU60zOgV0l65mpjSe3ERF");
                    Connection con = DriverManager.getConnection(urlempty + "miner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    PreparedStatement pst = con.prepareStatement("Select * FROM `ID` WHERE ID=" + user.getUser().getId());
                    ResultSet rs = pst.executeQuery();
                    if (!rs.next()) {
                        con = DriverManager.getConnection(urlempty + "miner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                        pst = con.prepareStatement("INSERT INTO `ID` (`ID`) VALUES ('" + user.getUser().getId() + "');");
                        pst.execute();
                        withdrawn = 0L;
                        total = 0L;
                    } else {
                        withdrawn = rs.getLong(2);
                        total = json.getLong("total") - withdrawn;
                    }
                    pst.close();
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().addField("ID", user.getUser().getId(), true).addField("Insgesamt Gemined", "" + json.get("total"), true)
                                    .addField("verfügbar", "" + total, true)
                                    .addField("eingelöst", "" + withdrawn, true).setTitle("Minerstats von "+ user.getAsMention())
                                    .setDescription("Alle Einhaeiten sind Hashes(außer ID)").setColor(Color.green).setFooter(Footer, Footer2).build()).queue();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.setName("miner");
            t.start();
            // new EmbedBuilder().addField("Hashes", )
        } else if (args[0].equalsIgnoreCase("withdraw")) {
            try {
                System.out.println("DEBUFG");
                try {
                    Hashes= Long.parseLong(args[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    Hashes= 1L;
                }
                user= event.getMember();

            Connection con = DriverManager.getConnection(urlempty + "miner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
            PreparedStatement pst = con.prepareStatement("Select * FROM `id` WHERE ID='" + user.getUser().getId()+"'");
            ResultSet rs = pst.executeQuery();
            JSONObject json = readJsonFromUrl("https://api.coinhive.com/user/balance?name=" + user.getUser().getId() + "&secret=M5T84quvGlAuU60zOgV0l65mpjSe3ERF");
            if (rs.next()) {
                withdrawn = rs.getLong(2);
                total = json.getLong("total") - withdrawn;
                if (total >= Hashes) {
                    con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    pst = con.prepareStatement("SELECT * FROM `user` WHERE ID=" + user.getUser().getId());
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        pst = con.prepareStatement("UPDATE `user` SET `Byte`=1 WHERE ID='" + user.getUser().getId()+"'");
                        pst.execute();
                        event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Footer, Footer2).setColor(Color.green).setTitle("Sucess").setDescription(Hashes+" Hash(es) wurden erfolgreich in "+Hashes+" Byte(s) gewechselt").build()).queue();
                    }
                    Hashes = withdrawn + Hashes;
                    con = DriverManager.getConnection(urlempty + "miner?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    pst = con.prepareStatement("UPDATE `id` SET `withdrawn`=" + Hashes + " WHERE ID=" + user.getUser().getId());
                    pst.execute();
                } else {
                    event.getTextChannel().sendMessage(new EmbedBuilder().setDescription("Du hast zu wenig Hashes Mine Welche auf https://miner.bigbotnetwork.de/").setTitle("Fehler").setColor(Color.RED).setFooter(Footer, Footer2).build()).queue();
                }
            }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
