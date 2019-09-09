package Main;

import Commands.*;
import Event.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

    public final static String prefix = "~";

    private Main() throws Exception{
        JDA jda = new JDABuilder("NTY5NDU2MDUwNjUxMDA0OTQ1.XLw5Yw.h2m14OKVJssp5MFUO9LeuyOGZWw").build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("Watching Anime"));
        jda.addEventListener(new Clear());
        jda.addEventListener(new Commands());
        jda.addEventListener(new Calculator());
        jda.addEventListener(new GuildMemberJoin());
        jda.addEventListener(new GuildMemberLeave());
        jda.addEventListener(new LanuageFilter());
        jda.addEventListener(new FilterUser());
        jda.addEventListener(new GetLatestAnime());
    }

    public static void main(String arg[]){
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
