package commands;

import core.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.SECRETS;

import java.sql.*;

import static core.Main.Embed;
import static core.Main.urlempty;

public class bank implements Command{

    long TempPreis;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }



    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length<1) {
            event.getTextChannel().sendMessage(Main.Embed.setTitle("Bank").setDescription("Hier kannst du eine Bot währung in Bytes umwandeln!\n" +
                    "``" + SECRETS.PREFIX + "bank list`` du siehst welche Bot Währungen es gibt und wieviel ein Gott\n" +
                    "``" + SECRETS.PREFIX + "bank change <Währung> <Gott Währung>`` um umzuwandeln").build()).queue();
        }
        try {
            switch (args[0].toLowerCase()) {
                case "register":

                    break;
                case "change":
                    try {

                        Connection con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                        PreparedStatement pst = con.prepareStatement("SELECT * FROM `list` WHERE `Name` LIKE '" + args[1] + "'");
                        ResultSet rs = pst.executeQuery();
                        if (!rs.next()) {
                            event.getTextChannel().sendMessage(
                                    Embed.setTitle("Fehler").setDescription("Huups: du hast etwas falsch eingegeben oder diese Währung gibt es nicht!").build()
                            ).queue();
                        } else {
                            System.out.println(event.getGuild().getMemberById(rs.getLong(4)));
                            if (event.getGuild().getMemberById(rs.getLong(4)) == null) {
                                event.getChannel().sendMessage(Main.Embed.setDescription("Du musst den Bot auf deinem Server haben! Inviten kannst du ihn mit:\n" +
                                        "``"+SECRETS.PREFIX+"bots``").setTitle("Fehler").build()).queue();
                            } else {
                                TempPreis = rs.getInt(2) * Long.parseLong(args[3]);
                                con = DriverManager.getConnection(urlempty + rs.getString(3) + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "'");
                                rs = pst.executeQuery();
                                if (!rs.next()) {
                                    event.getTextChannel().sendMessage(Main.Embed.setTitle("Fehler").setDescription("Du musst dich erst bei dem Bot registrieren " +
                                            "das geht mit" + SECRETS.PREFIX + "register").build()).queue();
                                } else {
                                    if (TempPreis <= rs.getInt(2) || TempPreis == rs.getInt(2)) {
                                        long TempCookies = rs.getInt(2) - TempPreis;
                                        con = DriverManager.getConnection(urlempty + rs.getString(3) + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                        pst = con.prepareStatement("UPDATE `user` SET `Waehrung`='" + TempCookies + "' WHERE ID='" + event.getAuthor().getId() + "'");
                                        pst.executeQuery();
                                        con = DriverManager.getConnection(urlempty + "bank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                        pst = con.prepareStatement("UPDATE `user` SET `Byte`='" + args[3] + "' WHERE ID='" + event.getAuthor().getId() + "'");
                                        pst.executeQuery();
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
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
                                Main.Embed.setTitle("Liste aller Währungen").setDescription("Diese Währungen gibt es:\n" + out).build()
                        ).queue();
                    } catch (SQLException e) {}
                    break;
            }
        } catch (Exception e) {}
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
