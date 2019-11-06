package listener;

import core.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import util.SECRETS;

import java.awt.*;

public class MentionListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().contains("<@"+event.getJDA().getSelfUser().getId()+">")) {
            event.getChannel().sendMessage(new EmbedBuilder().setFooter(Main.Footer, Main.Footer2)
                    .setColor(Color.GREEN)
                    .setTitle("Huch bin ich dran?")
                    .setDescription("Ehm ja ich bin da um alles für meine kleinen Bots zu verwalten... " +
                    "Die sind noch nicht so groß dass sie das können. Aber sie hören supergut auf dich also probier doch mal unser Netzwerk aus: " + SECRETS.PREFIX + "bots\nMehr Hilfe zu mir " + SECRETS.PREFIX + "help")
                    .build()).queue();

        }

    }

}
