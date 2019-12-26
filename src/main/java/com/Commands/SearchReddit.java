package com.Commands;

//import Main;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.*;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SearchReddit extends ListenerAdapter {

    private UserAgent userAgent = new UserAgent("bot", "com.example.usefulbot", "v0.1", "shinobu");

    /**
     * Scrap data off r/anime on the newest episode of the day
     *
     * @param event Message receiving event
     */
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {


        // Check if user wanted to use this function
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        try {
            if (args[0].equalsIgnoreCase("~" + "reddit") && args.length == 5) {
                BufferedReader in = new BufferedReader(new FileReader("RedditInformation.txt"));
                // Authenticating to Reddit OAuth 2.0
                Credentials credentials = Credentials.script(in.readLine(), in.readLine(), in.readLine(), in.readLine());

                // This is what really sends HTTP requests
                NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);

                // Authenticate and get a RedditClient instance
                RedditClient reddit = OAuthHelper.automatic(adapter, credentials);

                if (Integer.parseInt(args[4]) > 15) {
                    event.getChannel().sendMessage("You can't get more than 15 posts per request, please try again");
                } else {
                    try {
                        System.out.println(reddit.subreddit(" ").getSubreddit().isEmpty());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Integer.parseInt(args[4]));
                    // frontPage() returns a Paginator.Builder
                    DefaultPaginator<Submission> frontPage = reddit.subreddit(args[1]).posts()
                            .sorting(SubredditSort.valueOf(args[2].toUpperCase()))
                            .timePeriod(TimePeriod.valueOf(args[3].toUpperCase()))
                            .limit(Integer.parseInt(args[4]))
                            .build();

                    // Get previous message(s) and delete them
                    try {
                        if (event.getChannel().getHistory().isEmpty()) {
                            List<Message> messages = event.getChannel().getHistory().retrievePast(15).complete();
                            event.getChannel().deleteMessages(messages).queue();
                        }
                    } catch(IllegalArgumentException e){
                        System.out.println("Message over 2 weeks old can't delete");
                    }

                    Listing<Submission> submissions = frontPage.next();
                    for (int i = 0; i < Integer.parseInt(args[4]); i++) {
                        //if (s.getLinkFlairText() != null && s.getLinkFlairText().contains("Episode")) {
                        // Outputting the information(s) to discord
                        event.getChannel().sendMessage("Title: " + submissions.get(i).getTitle() + " Link: https://redd.it/" + submissions.get(i).getId()).queue();
                        //}
                    }
                }

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
