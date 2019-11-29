package com.grunclepug.grunclebot.bot.util.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;

/**
 * Audio Handler class
 * @author grunclepug
 */
public class AudioPlayerSendHandler implements AudioSendHandler
{
    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    /**
     * Constructor
     * @param audioPlayer AudioPlayer
     */
    public AudioPlayerSendHandler(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
    }

    @Override
    /**
     * Checks if it can provide
     * @return AudioFrame
     */
    public boolean canProvide()
    {
        if(lastFrame == null)
        {
            lastFrame = audioPlayer.provide();
        }
        return lastFrame != null;
    }

    @Override
    /**
     * Provides audio
     * @return byte data
     */
    public byte[] provide20MsAudio()
    {
        if (lastFrame == null)
        {
            lastFrame = audioPlayer.provide();
        }

        byte[] data = lastFrame != null ? lastFrame.getData() : null;
        lastFrame = null;

        return data;
    }

    @Override
    /**
     * Returns Opus as true
     * @return Opus=true
     */
    public boolean isOpus()
    {
        return true;
    }
}
