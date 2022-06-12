package ir.balad.musicplayer.entity

import ir.balad.musicplayer.exception.IllegalOperationException


/**
 * Represents Musics thar are used in MusicPlayer.
 *
 * @author Sepi 6/12/22
 */
data class Music(
    val id: String,
    /** playing state of the music  */
    var state: MusicState = MusicState.STOPPED,
    /** the time the music will be played from that in milliseconds */
    var duration: Long = 0L
) {

    enum class MusicState {
        PLAYING,
        STOPPED
    }

    /**
     * Pauses the music. Keeps the paused time in [duration].
     *
     * @throws IllegalOperationException if music was paused/stopped already.
     */
    fun pause(time: Long) {
        if (state == MusicState.STOPPED)
            throw IllegalOperationException("Music is paused/stopped already")

        state = MusicState.STOPPED
        duration = time
    }

    /**
     * Resumes the music.
     *
     * @throws IllegalOperationException if music was playing already.
     * @return the time music must be played from ([duration])
     */
    fun resume(): Long {
        if (state == MusicState.PLAYING)
            throw IllegalOperationException("Music is playing already")

        state = MusicState.PLAYING
        return duration
    }

    /**
     * Stops the music. Sets duration time to 0 in [duration] of Music to be played from the beginning next time.
     *
     * @throws IllegalOperationException if music was paused/stopped already.
     */
    fun stop() {
        pause(0)
    }
}
