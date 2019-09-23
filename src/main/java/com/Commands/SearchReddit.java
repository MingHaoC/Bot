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
        if (args[0].equalsIgnoreCase("~" + "reddit") && args.length == 5) {

            // Authenticating to Reddit OAuth 2.0
            Credentials credentials = Credentials.script("shinobu-oshino", "snivy11",
                    "e5_j-DBouU54WQ", "XVHExFj2KIQZZ7xC5A6R-BFF6ag");

            // This is what really sends HTTP requests
            NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);

            // Authenticate and get a RedditClient instance
            RedditClient reddit = OAuthHelper.automatic(adapter, credentials);

            if(Integer.parseInt(args[4]) > 15){
                event.getChannel().sendMessage("You can't get more than 15 posts per request, please try again");
            }

            else {
            try{
               System.out.println(reddit.subreddit(" ").getSubreddit().isEmpty());
            } catch(IllegalArgumentException e){
                e.printStackTrace();
            }
                // frontPage() returns a Paginator.Builder
                DefaultPaginator<Submission> frontPage = reddit.subreddit(args[1]).posts()
                        .sorting(SubredditSort.valueOf(args[2].toUpperCase()))
                        .timePeriod(TimePeriod.valueOf(args[3].toUpperCase()))
                        .limit(Integer.parseInt(args[4]))
                        .build();

                // Get previous message(s) and delete them

                if (event.getChannel().getHistory().isEmpty()) {
                    List<Message> messages = event.getChannel().getHistory().retrievePast(15).complete();
                    event.getChannel().deleteMessages(messages).queue();
                }

                // Get all the new post with "Episode" as their LinkFlairText
                Listing<Submission> submissions = frontPage.next();
                for (Submission s : submissions) {
                    //if (s.getLinkFlairText() != null && s.getLinkFlairText().contains("Episode")) {
                    // Outputting the information(s) to discord
                    event.getChannel().sendMessage("Title: " + s.getTitle() + " Link: https://redd.it/" + s.getId()).queue();
                    //}

                }
            }

        }
    }
}
