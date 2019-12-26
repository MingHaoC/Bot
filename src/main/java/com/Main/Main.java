package com.Main;

import com.Commands.*;
import com.Event.FilterUser;
import com.Event.GuildMemberJoin;
import com.Event.GuildMemberLeave;
import com.Event.LanuageFilter;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.*;

public class Main {

    public final static String prefix = "~";
    public static JDA jda;


    private Main() throws LoginException {
        try {
            BufferedReader in = new BufferedReader(new FileReader("Token.txt"));
            jda = new JDABuilder(AccountType.BOT).setToken(in.readLine()).build();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String arg[]){
        try {
            new Main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
