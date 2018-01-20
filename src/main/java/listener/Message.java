package listener;

import core.Main;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Message extends ListenerAdapter {

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            System.out.println(event.getChannel().getMessageById(event.getMessageId()).complete().getContentStripped());
            if (event.getReaction().getReactionEmote().getName().equals("\uD83D\uDE04")) {
                try {
                    event.getChannel().getMessageById(event.getMessageId()).complete().clearReactions().queue();
                    event.getChannel().editMessageById(event.getMessageId(), Main.Embed.setColor(Color.GREEN).setTitle("Help zum CookieBot")
                            .setDescription("``-stats`` - zeigt dir deine Stats\n" +
                            "``-shop`` - damit kannst du dir sachen kaufen").build()).complete().addReaction("\uD83D\uDD19").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (event.getReaction().getReactionEmote().getName().equals("\uD83D\uDC7E")) {
                try{
                    event.getChannel().getMessageById(event.getMessageId()).complete().clearReactions().queue();
                    event.getChannel().editMessageById(event.getMessageId(), Main.Embed.setColor(Color.GREEN).setTitle("Help zum GottderBots")
                            .setDescription("``-help`` - macht das Help Menü auf\n" +
                                    "``-bank`` - Wandelt eine Bot währung in Bytes um\n" +
                                    "``-bots`` - Listet alle Bots auf\n" +
                                    "``-register`` - Registriert dich bei einem Bot").build()).complete().addReaction("\uD83D\uDD19").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (event.getReaction().getReactionEmote().getName().equals("\uD83D\uDD19")) {
                try{
                    event.getChannel().getMessageById(event.getMessageId()).complete().clearReactions().queue();
                    event.getChannel().editMessageById(event.getMessageId(), Main.Embed.setColor(Color.GREEN).setTitle("Help - Hilfe").setDescription("Hier eine Übersicht über alle Bot Commands die von Greg#4239 und " +
                            "Hax#6775 sind: \nWenn du das entsprechende Menü öffnen willst reagiere mit dem Smiley hinter dem Bot\n" +
                            "``CookieBot`` - :smile:").build()).complete().addReaction("\uD83D\uDE04").queue();
                    event.getChannel().getMessageById(event.getMessageId()).complete().addReaction("\uD83D\uDC7E").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
