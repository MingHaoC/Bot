package Event;

import Main.main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LanuageFilter extends ListenerAdapter {

    private List<String> words = new ArrayList<>();

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        try
        {
            File swearWords = new File("words.txt");
            if(!swearWords.exists()) {
                swearWords.createNewFile();
                event.getChannel().sendMessage("Oh you guy didn't set up filter words. Tell the bot owner to go to this directory and set up the filter words: " + swearWords.getAbsolutePath() ).queue();
            }
            FileInputStream filestream = new FileInputStream("words.txt");
            DataInputStream input = new DataInputStream(filestream);
            BufferedReader re = new BufferedReader(new InputStreamReader(input));
            String newLine;

            while ((newLine = re.readLine()) != null)
            {
                newLine = newLine.trim();
                if ((newLine.length()!=0))
                {
                    words.add(newLine);
                }
            }

        } catch (IOException e){
            System.out.println(e.toString());
        }

        boolean swear = false, add = true;
        StringBuilder temp = new StringBuilder();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (!args[0].equalsIgnoreCase(main.prefix) && !event.getAuthor().isBot() && !Objects.requireNonNull(event.getMessage().getMember()).hasPermission(Permission.ADMINISTRATOR)) {

            for (String arg : args) {
                for (String word : words) {
                    add = true;
                    if (arg.contains(word)) {
                        swear = true;
                        for (int i = 0; i < arg.length(); i++) {
                            temp.append("✱");
                        }
                        temp.append(" ");
                        add = false;
                        break;
                    }
                }
                if (add)
                    temp.append(arg).append(" ");
            }
        }
        if (swear) {
            event.getChannel().sendMessage("This bot is so fast that you won't even see this").queue();
            List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
            event.getChannel().deleteMessages(messages).queue();

            event.getChannel().sendMessage( "No swearing in my Christan Minecraft server, btw " + Objects.requireNonNull(event.getMember()).getAsMention() + " said the following").queue();
            event.getChannel().sendMessage(event.getMember().getNickname() + "\n" + temp).queue();
        }
    }
}
