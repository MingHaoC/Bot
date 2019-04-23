package Main;

import Commands.*;
import Event.GuildMemberJoin;
import Event.GuildMemberLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class main {

    public static String prefix = "~";

    main() throws Exception{
        JDA jda = new JDABuilder("NTY5NDU2MDUwNjUxMDA0OTQ1.XLw5Yw.h2m14OKVJssp5MFUO9LeuyOGZWw").build();
        String a = "with mayo dicks";
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing(a));
        jda.addEventListener(new Clear());
        jda.addEventListener(new Commands());
        jda.addEventListener(new Calculator());
        jda.addEventListener(new GuildMemberJoin());
        jda.addEventListener(new GuildMemberLeave());
    }

    public static void main(String arg[]){
        try {
            new main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
