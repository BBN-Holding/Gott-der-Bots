package commands;

import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.SECRETS;
import static core.Main.pst;
import static core.Main.rs;
import static core.Main.con;
import java.awt.*;
import java.sql.*;

import static core.Main.urlempty;
import static util.SECRETS.VERSION;

public class bank implements Command{

    long TempPreis;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }



    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length<1) {
            event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Bank").setDescription("Hier kannst du eine Bot währung in Bytes umwandeln!\n" +
                    "``" + SECRETS.PREFIX + "bank list`` du siehst welche Bot Währungen es gibt und wieviel ein Gott\n" +
                    "``" + SECRETS.PREFIX + "bank change <Währung> <Gott Währung>`` um umzuwandeln").setThumbnail("").build()).queue();
        }
        try {
            switch (args[0].toLowerCase()) {
                case "register":

                    break;
                case "change":
                    try {

                        con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                        pst = con.prepareStatement("SELECT * FROM `list` WHERE `Name` LIKE '" + args[1] + "'");
                        rs = pst.executeQuery();
                        if (!rs.next()) {
                            event.getTextChannel().sendMessage(
                                    new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Fehler").setThumbnail("").setDescription("Huups: du hast etwas falsch eingegeben oder diese Währung gibt es nicht!").build()
                            ).queue();
                        } else {
                            if (event.getGuild().getMemberById(rs.getLong(4)) == null) {
                                event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setThumbnail("").setDescription("Du musst den Bot auf deinem Server haben! Inviten kannst du ihn mit:\n" +
                                        "``"+SECRETS.PREFIX+"bots``").setTitle("Fehler").build()).queue();
                            } else {
                                TempPreis = rs.getInt(2) * Long.parseLong(args[2]);
                                con = DriverManager.getConnection(urlempty + rs.getString(3) + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "'");
                                rs = pst.executeQuery();
                                if (!rs.next()) {
                                    event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Fehler").setDescription("Du musst dich erst bei dem Bot registrieren " +
                                            "das geht mit" + SECRETS.PREFIX + "register").setThumbnail("").build()).queue();
                                } else {
                                    if (TempPreis <= rs.getInt(2) || TempPreis == rs.getInt(2)) {
                                        long TempCookies = rs.getInt(2) - TempPreis;
                                        con = DriverManager.getConnection(urlempty + rs.getString(3) + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                        pst = con.prepareStatement("UPDATE `user` SET `Waehrung`='" + TempCookies + "' WHERE ID='" + event.getAuthor().getId() + "'");
                                        pst.executeQuery();
                                        con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                        pst = con.prepareStatement("UPDATE `user` SET `Byte`='" + args[3] + "' WHERE ID='" + event.getAuthor().getId() + "'");
                                        pst.executeQuery();
                                        event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.GREEN).setTitle("Nice!").setDescription("Die Währung wurde erfolgreich getauscht").setThumbnail("").build()).queue();
                                    } else {
                                        event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.RED).setDescription("Zu wenig der Währung").setThumbnail("").setTitle("Fehler").build()).queue();
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "list":
                    try {


                        Connection con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                        PreparedStatement pst = con.prepareStatement("SELECT * FROM `list`");
                        ResultSet rs = pst.executeQuery();

                        String out = "";

                        while (rs.next()) {
                            out += rs.getString(1) + " - " + rs.getString(2) + " " + rs.getString(1) + "/Byte\n";
                        }

                        event.getTextChannel().sendMessage(
                                new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Liste aller Währungen").setDescription("Diese Währungen gibt es:\n" + out).setThumbnail("").build()
                        ).queue();
                    } catch (SQLException e) {}
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
