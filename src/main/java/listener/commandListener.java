package listener;

import core.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import core.commandHandler;

import static util.SECRETS.PREFIX;

public class commandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(PREFIX) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            commandHandler.handleCommand(commandHandler.parser.parse(event.getMessage().getContentRaw().toLowerCase(), event));
        }

    }

}