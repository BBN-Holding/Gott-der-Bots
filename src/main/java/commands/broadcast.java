package commands;

import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class broadcast implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getAuthor().getId().equals("261083609148948488") || event.getAuthor().getId().equals("362270177712275491")) {
           String Inhalt=event.getMessage().getContentDisplay().replace("-broadcast", "");
            int i=0;
            System.out.println("Broadcast:");
            while (event.getJDA().getGuilds().size()>i) {
                event.getJDA().getGuilds().get(i).getOwner().getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder().setTitle("News from the Developers!").setDescription(Inhalt).setColor(Color.RED).setFooter(Main.Footer, Main.Footer2).build()).queue();
                System.out.println("#"+i+" "+event.getJDA().getGuilds().get(i).getOwner().getUser().getName()+" on " + event.getJDA().getGuilds().get(i).getName());
                i++;
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
