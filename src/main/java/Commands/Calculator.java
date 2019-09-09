package Commands;

import Main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Calculator extends ListenerAdapter {

    /**
     * This method calculates the sum/difference/product or quotient depending on user input
     *
     * @param event Message receiving event
     */
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // Get user input and stores as a string array
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        // Check if user is trying to use the calculator function
        if (args[0].equalsIgnoreCase(Main.prefix + "calculator")) {
            if (args.length < 4) {
                // User inputs are less than 4
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage("Needs 4 arguments").queue();
            } else {
                // Different case for different operation
                switch (args[1]) {
                    case "+":
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("The sum is " + (Float.parseFloat(args[2]) + Float.parseFloat(args[3]))).queue();
                        break;
                    case "-":
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("The difference is " + (Float.parseFloat(args[2]) - Float.parseFloat(args[3]))).queue();
                        break;
                    case "*":
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("The product is " + (Float.parseFloat(args[2]) * Float.parseFloat(args[3]))).queue();
                    case "/":
                        event.getChannel().sendTyping().queue();
                        try {
                            event.getChannel().sendMessage("The quotients is " + (Float.parseFloat(args[2]) / Float.parseFloat(args[3]))).queue();
                        } catch (ArithmeticException e) {
                            // Division by zero
                            EmbedBuilder error = new EmbedBuilder();
                            error.setColor(0xff3923);
                            error.setTitle("Division by 0");
                            error.setDescription("Modern computer aren't not completent enough to divides by 0 sorry!");
                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(error.build()).queue();
                            error.clear();
                        }
                        break;
                }
            }
        }
    }
}