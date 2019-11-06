package listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import core.commandHandler;

import static util.SECRETS.PREFIX;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(PREFIX) && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            commandHandler.handleCommand(commandHandler.parser.parse(event.getMessage().getContentRaw().toLowerCase(), event));
        }
    }
}