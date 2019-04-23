package Commands;

import Main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(main.prefix)){
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("Bot Commands");
            info.setDescription("You're too dumb to use this bot?  Don't worry I got you!");
            info.addField("Command clear", "~clear #ofmessage(s) (# have to be between 1-100 and the message have to be less then 2 week old)", false);
            info.addField("Command better calculator", "~calculator operator value1 value2.  Operator(+ | - | / | *)", false);
            info.setColor(0x33FFFF);
            info.setFooter("Created by the legend Shinobu Oshino", event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(info.build()).queue();
            info.clear();
        }
    }

}
