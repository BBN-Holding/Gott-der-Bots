package commands;

import core.HelpMenu;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.SECRETS;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static core.Main.urlempty;
import static util.SECRETS.VERSION;


public class help implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
                event.getMessage().delete().queue();
                event.getTextChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2).setTitle("Gesendet!").setDescription("Dir wurde das Help Menü geschickt!").setColor(Color.GREEN).build()).complete()
                        .delete().queueAfter(5, TimeUnit.SECONDS);
                HelpMenu.Help("\uD83D\uDD19");
            String id=event.getMember().getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setTitle(HelpMenu.Title).setDescription(HelpMenu.Message).build()).complete().getId();
            System.out.println(id);
            int i=1;
            while (HelpMenu.Emoji.length>i) {
                event.getAuthor().openPrivateChannel().complete().addReactionById(id, HelpMenu.Emoji[i]).queue();
                HelpMenu.Emoji[i]=null;
                i++;
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
