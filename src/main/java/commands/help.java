package commands;

import core.Main;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import util.SECRETS;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static core.Main.urlempty;


public class help implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            if (args.length < 1) {
                event.getTextChannel().sendMessage(Main.Embed.setColor(Color.CYAN).setTitle("REAGIEREN").setDescription("Für help reagiere mit :back: ").build()).complete().addReaction("\uD83D\uDD19").queue();
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
