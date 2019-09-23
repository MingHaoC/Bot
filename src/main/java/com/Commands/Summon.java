package com.Commands;

import com.Main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Summon extends ListenerAdapter {

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "summon")) {
            // Checks if the bot has permissions .
            if(!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.VOICE_CONNECT)) {
                // The bot does not have permission to join any voice channel. Don't forget the .queue()!
                event.getChannel().sendMessage("I do not have permissions to join a voice channel!").queue();
                return;
            }
            // Creates a variable equal to the channel that the user is in.
            VoiceChannel connectedChannel = Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            // Checks if they are in a channel -- not being in a channel means that the variable = null.
            if(connectedChannel == null) {
                // Don't forget to .queue()!
                event.getChannel().sendMessage("You are not connected to a voice channel!").queue();
                return;
            }

            // Gets the audio manager.
            AudioManager audioManager = event.getGuild().getAudioManager();

            // When somebody really needs to chill.
            if(audioManager.isAttemptingToConnect()) {
                event.getChannel().sendMessage("The bot is already trying to connect! Enter the chill zone!").queue();
                return;
            }

            // Connects to the channel.
            audioManager.openAudioConnection(connectedChannel);

            // Obviously people do not notice someone/something connecting.
            event.getChannel().sendMessage("Connected to the voice channel!").queue();
        } else if (args[0].equalsIgnoreCase(Main.prefix + "leave")){
            // Gets the channel in which the bot is currently connected.
            VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
            // Checks if the bot is connected to a voice channel.
            if(connectedChannel == null) {
                // Get slightly fed up at the user.
                event.getChannel().sendMessage("I am not connected to a voice channel!").queue();
                return;
            }
            // Disconnect from the channel.
            event.getGuild().getAudioManager().closeAudioConnection();
            // Notify the user.
            event.getChannel().sendMessage("Disconnected from the voice channel!").queue();
        }
    }
}
