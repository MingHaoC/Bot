package com.Main;

import com.Commands.*;
import com.Event.FilterUser;
import com.Event.GuildMemberJoin;
import com.Event.GuildMemberLeave;
import com.Event.LanuageFilter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.naming.AuthenticationException;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public final static String prefix = "~";
    public static JDA jda;


    private Main() throws LoginException {
        jda = new JDABuilder("NTY5NDU2MDUwNjUxMDA0OTQ1.XLw5Yw.h2m14OKVJssp5MFUO9LeuyOGZWw").build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("Watching Anime"));
        jda.addEventListener(new Clear());
        jda.addEventListener(new Commands());
        jda.addEventListener(new Calculator());
        jda.addEventListener(new GuildMemberJoin());
        jda.addEventListener(new GuildMemberLeave());
        jda.addEventListener(new LanuageFilter());
        jda.addEventListener(new FilterUser());
        jda.addEventListener(new SearchReddit());
        jda.addEventListener(new Summon());
    }

    public static void main(String arg[]){
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
