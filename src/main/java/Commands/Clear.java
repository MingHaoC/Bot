package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import Main.main;

import java.util.List;

public class Clear extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(main.prefix + "clear")){
            if(args.length < 2){
                // Error
                System.out.println("A");
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Need more than one argument").queue();
            }else{
                try{
                    List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    event.getChannel().deleteMessages(messages).queue();
                    event.getChannel().sendMessage("Successfully deleted " + Integer.parseInt(args[1])+ " Message").queue();
                }catch(IllegalArgumentException e){
                    if(e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")){
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("Too many / too little message selected");
                        error.setDescription("1-100 messages");
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(error.build()).queue();
                        error.clear();
                    }else{
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(0xff3923);
                        error.setTitle("Trying to delete message older than 2 week");
                        error.setDescription("1-100 messages");
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(error.build()).queue();
                        error.clear();
                    }
                }
            }
        }
    }
}
