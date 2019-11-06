package commands;

import core.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.sql.*;
import static core.Main.urlempty;

public class CommandBots implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
        Connection con = DriverManager.getConnection(urlempty+"bank" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM `list`");
        ResultSet rs = pst.executeQuery();

        StringBuilder out = new StringBuilder();

        while (rs.next()) {
            out.append("``").append(rs.getString(6)).append("`` Invite Link: ``").append(rs.getString(5)).append("``\n");
        }
            event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Alle Bots").setDescription("Hier eine Übersicht aller Bots: \n" + out).build()).queue();
        } catch (SQLException e) {
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
