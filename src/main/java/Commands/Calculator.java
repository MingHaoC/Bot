package Commands;

import Main.main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Calculator extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(main.prefix + "calculator")){
            if(args.length < 4){
                // Error
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Needs 4 arguments").queue();
            }else{
                    System.out.println("    A");
                    switch(args[1]) {
                        case "+":
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("The sum is " + (Float.parseFloat(args[2]) + Float.parseFloat(args[3]))).queue();
                            break;
                        case "-":
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("The difference is " + (Float.parseFloat(args[2]) - Float.parseFloat(args[3]))).queue();
                            break;
                        case "/":
                            event.getChannel().sendTyping().queue();
                            try {
                                event.getChannel().sendMessage("The quotients is " + (Float.parseFloat(args[2]) / Float.parseFloat(args[3]))).queue();
                            }catch(ArithmeticException e){
                                EmbedBuilder error = new EmbedBuilder();
                                error.setColor(0xff3923);
                                error.setTitle("Division by 0");
                                error.setDescription("Modern computer aren't not completent enough to divides by 0 sorry!");
                                event.getChannel().sendTyping().queue();
                                event.getChannel().sendMessage(error.build()).queue();
                                error.clear();
                            }
                            break;
                        case "*":
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage("The product is " + (Float.parseFloat(args[2]) * Float.parseFloat(args[3]))).queue();
                    }
            }
        }
    }
}