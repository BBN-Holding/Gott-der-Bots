package listener;

import core.HelpMenu;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

import static util.SECRETS.VERSION;

public class Message extends ListenerAdapter {
    public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event) {
        if (!event.getUser().isBot() && event.getChannel().getMessageById(event.getMessageId()).complete().getEmbeds().get(0).getTitle().contains("Help")) {
            HelpMenu.Help(event.getReactionEmote().getName().toString());
            event.getChannel().editMessageById(event.getMessageId(), new EmbedBuilder().setDescription(HelpMenu.Message).setTitle(HelpMenu.Title).setColor(Color.GREEN).setFooter(Main.Footer, Main.Footer2).build()).queue();
            int i=1;
            while (HelpMenu.Emoji.length>i) {
                event.getChannel().addReactionById(event.getMessageId(), HelpMenu.Emoji[i]).queue();
                HelpMenu.Emoji[i]=null;
                i++;
            }
        }
    }
    public void onPrivateMessageReactionRemove(PrivateMessageReactionRemoveEvent event) {
        if (!event.getUser().isBot() && event.getChannel().getMessageById(event.getMessageId()).complete().getEmbeds().get(0).getTitle().contains("Help")) {
            HelpMenu.Help(event.getReactionEmote().getName().toString());
            event.getChannel().removeReactionById(event.getMessageId(), event.getReaction().getReactionEmote().getName()).queue();
            event.getChannel().editMessageById(event.getMessageId(), new EmbedBuilder().setDescription(HelpMenu.Message).setTitle(HelpMenu.Title).setColor(Color.GREEN).setFooter(Main.Footer, Main.Footer2).build()).queue();
            int i=1;
            while (HelpMenu.Emoji.length>i) {
                event.getChannel().addReactionById(event.getMessageId(), HelpMenu.Emoji[i]).queue();
                HelpMenu.Emoji[i]=null;
                i++;
            }
        }
    }

}
