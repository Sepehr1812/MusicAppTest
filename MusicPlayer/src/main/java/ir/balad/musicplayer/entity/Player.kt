package ir.balad.musicplayer.entity

import ir.balad.musicplayer.exception.IllegalOperationException
import ir.balad.musicplayer.exception.IllegalQueueSizeException
import ir.balad.musicplayer.exception.NotPremiumUserException
import org.jetbrains.annotations.TestOnly


/**
 * Represents the music player operations.
 *
 * @author Sepi 6/12/22
 */
class Player {

    private var musicQueue = mutableListOf<Music>()

    /**
     * @return the music that is playing right now. `null` if no music was playing.
     */
    fun getPlayingMusic() = musicQueue.find { it.state == Music.MusicState.PLAYING }

    /**
     * @return the music queue after now playing music.
     *  Empty list if the playing music is the last music in the queue.
     */
    fun getNextMusicQueue() = try {
        musicQueue.subList(musicQueue.indexOf(getPlayingMusic()).plus(1), musicQueue.size)
    } catch (e: Exception) {
        mutableListOf()
    }

    /**
     * Replaces the music queue.
     *
     * @param queue the new music queue that user wants to replace.
     * @param user the operator user.
     *
     * @throws IllegalQueueSizeException if user type was [User.UserType.REGULAR]
     *  and size of `queue` was less than 5.
     */
    fun replaceMusicQueue(queue: List<Music>, user: User) {
        musicQueue = if (user.type == User.UserType.PREMIUM) queue.toMutableList()
        else {  // when user type is REGULAR
            if (queue.size < 5)
                throw IllegalQueueSizeException("Music queue size can not be less than 5 for Regular users")

            queue.shuffled().toMutableList()
        }
    }

    /**
     * Stops the current playing music and plays the next one in the queue.
     *
     * @throws IllegalOperationException if no music was playing right now.
     * @return the next [Music] in the queue, or `null` if there is no next music.
     */
    fun playNextMusic(): Music? {
        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")

        playingMusic.stop()

        return musicQueue.getOrNull(musicQueue.indexOf(playingMusic).plus(1))?.apply { resume() }
    }

    /**
     * Stops the current playing music and plays the previous one in the queue.
     *
     * @param user the operator user.
     *
     * @throws NotPremiumUserException if `user` type is not Premium.
     * @throws IllegalOperationException if no music was playing right now.
     * @return the previous [Music] in the queue, or `null` if there is no previous music.
     */
    fun playPreviousMusic(user: User): Music? {
        if (user.type == User.UserType.REGULAR)
            throw NotPremiumUserException("This feature is only provided for Premium users.")

        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")

        playingMusic.stop()

        return musicQueue.getOrNull(musicQueue.indexOf(playingMusic).minus(1))?.apply { resume() }
    }

    /**
     * Adds a new music to the music queue after the position of now playing music.
     *
     * @param music the new music user wants to add to the queue.
     * @param user the operator user.
     *
     * @throws NotPremiumUserException if `user` type is not Premium.
     * @throws IllegalOperationException if no music was playing right now.
     */
    fun addMusicToQueue(music: Music, user: User) {
        if (user.type == User.UserType.REGULAR)
            throw NotPremiumUserException("This feature is only provided for Premium users.")

        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")

        musicQueue.add(musicQueue.indexOf(playingMusic).plus(1), music)
    }

    /* Test Only functions */

    @TestOnly
    fun getMusicQueue() = musicQueue

    @TestOnly
    fun setMusicQueue(queue: MutableList<Music>) {
        musicQueue = queue
    }
}