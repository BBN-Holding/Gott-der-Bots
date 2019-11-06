package commands;

import core.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.SECRETS;

import java.awt.*;
import java.sql.*;

import static core.Main.urlempty;

public class CommandRegister implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length<1) {
            event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Register").setDescription("Damit kannst du dich bei verschiedenen Bots registrieren. Folgende Commands kannst du benutzen: \n``" +
                    SECRETS.PREFIX + "register list`` Zeigt dir eine Liste bei welchen Bots du dich registrieren kannst!\n``" +
                    SECRETS.PREFIX + "register <NamedesBots>`` Registriert dich bei dem Bot.").build()).queue();
        }
        try {
            if ("list".equals(args[0].toLowerCase())) {
                try {
                    Connection con = DriverManager.getConnection(urlempty + "bank" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM `list`");
                    ResultSet rs = pst.executeQuery();

                    StringBuilder out = new StringBuilder();

                    while (rs.next()) {
                        out.append("●► ``").append(rs.getString(6)).append("``\n");
                    }
                    event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.GREEN)
                            .setDescription("Hier eine Liste aller Bots bei denen du dich registrieren kannst: \n" + out)
                            .setTitle("Liste")
                            .build()
                    ).queue();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (!args[0].toLowerCase().equals("list")) {
                    Connection con = DriverManager.getConnection(urlempty + "bank" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM `list` WHERE `NameRichtig` LIKE '" + args[0] + "'");
                    ResultSet rs = pst.executeQuery();
                    if (!rs.next()) {
                        event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.RED).setTitle("Fehler!").setDescription("Huups: du hast etwas falsch eingegeben oder den" +
                                " Bot gibt es nicht!").build()).queue();
                    } else {
                        if (event.getGuild().getMemberById(rs.getLong(4)) == null) {
                            event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setDescription("Du musst den Bot auf deinem Server haben! Inviten kannst du ihn mit:\n" +
                                    "``" + SECRETS.PREFIX + "bots``").setTitle("Fehler").setColor(Color.RED).build()).queue();
                        }else {
                            String TempData=rs.getString(3);
                            con = DriverManager.getConnection(urlempty + TempData + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                            pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "'");
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setDescription("Anscheinend existierst du schon in unserer Datanbank.").setTitle("Fehler").setColor(Color.RED).build()).queue();
                            } else {
                                if (args[0].toLowerCase().equals("cookiebot")) {
                                    con = DriverManager.getConnection(urlempty + TempData + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                                    pst = con.prepareStatement("INSERT INTO `user` (`ID`) VALUES ('" + event.getAuthor().getId() + "')");
                                    pst.execute();
                                    event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setDescription("Du wurdest erfolgreich bei dem gewünschten Bot registriert!").setTitle("Nice!!").setColor(Color.GREEN).build()).queue();
                                } else {
                                    event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setColor(Color.RED).setTitle("Fehler").setDescription("Dieser Bot muss noch eingestellt werden...").build()).queue();
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e ) {
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
