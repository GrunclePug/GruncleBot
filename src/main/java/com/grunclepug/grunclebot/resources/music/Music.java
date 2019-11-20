package com.grunclepug.grunclebot.resources.music;

import com.grunclepug.grunclebot.core.Main;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Class for music
 * @author grunclepug
 */
public class Music extends ListenerAdapter
{
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    private static VoiceChannel channel;

    /**
     * Constructor
     */
    public Music()
    {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    /**
     * Acccessor for guild audio player
     * @param guild Guild to play audio in
     * @return GuildMusicManager to manage the music
     */
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild)
    {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null)
        {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

    @Override
    /**
     * Guild Message Received Method
     * @param event GuildMessageReceivedEvent
     */
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        channel = event.getMember().getVoiceState().getChannel();

        String song = null;

        if(args.length > 2)
        {
            for(int i = 1; i < args.length; i++)
            {
                song += (" " + args[i]);
            }
            song = song.substring(5);
        }
        else if(args.length > 1)
        {
            song = args[1];
        }

        if(args[0].equalsIgnoreCase(Main.prefix + "play"))
        {
            loadAndPlay(event.getTextChannel(), song);
        }
        else if(args[0].equalsIgnoreCase(Main.prefix + "skip"))
        {
            skipTrack(event.getTextChannel());
        }
        else if(args[0].equalsIgnoreCase(Main.prefix + "stop"))
        {
            stopTrack(event.getGuild(), event.getTextChannel());
        }

        super.onMessageReceived(event);
    }

    /**
     * Load and play song
     * @param channel text channel that command was issued in
     * @param trackUrl URL to the song
     */
    private void loadAndPlay(final TextChannel channel, final String trackUrl)
    {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler()
        {
            @Override
            /**
             * Load the track
             * @param track AudioTrack
             */
            public void trackLoaded(AudioTrack track)
            {
                channel.sendMessage("Adding to queue: " + track.getInfo().title).queue();
                play(channel.getGuild(), musicManager, track);
            }

            @Override
            /**
             * Takes care of the playlist
             * @param AudioPlaylist
             */
            public void playlistLoaded(AudioPlaylist playlist)
            {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null)
                {
                    firstTrack = playlist.getTracks().get(0);
                }
                channel.sendMessage("Adding to queue: " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            /**
             * Method for no song found
             */
            public void noMatches()
            {
                //channel.sendMessage("No results for: " + trackUrl).queue();
                channel.sendMessage("Search feature coming soon.").queue();
            }

            @Override
            /**
             * Method for cannot play
             */
            public void loadFailed(FriendlyException exception)
            {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    /**
     * Play method
     * @param guild Guild that the user is in
     * @param musicManager Manages the music (GuildMusicManager)
     * @param track The song
     */
    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track)
    {
        connectToVoiceChannel(guild.getAudioManager());
        musicManager.scheduler.queue(track);
    }

    /**
     * Skip method
     * @param channel text channel that user issued command
     */
    private void skipTrack(TextChannel channel)
    {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    /**
     * Stop method
     * @param guild Guild that the bot is playing in
     * @param channel text channel that user issued command
     */
    private void stopTrack(Guild guild, TextChannel channel)
    {
        guild.getAudioManager().closeAudioConnection();
        channel.sendMessage("Stopped the track.").queue();
    }

    /**
     * Connect Method
     * @param audioManager AudioManager
     */
    private static void connectToVoiceChannel(AudioManager audioManager)
    {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect())
        {
            audioManager.openAudioConnection(channel);
        }
    }
}