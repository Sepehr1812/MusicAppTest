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
     * @return the music queue after now playing music or if there is no playing music, the whole queue.
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
     *  Does nothing if there is no next music in the queue.
     *
     * @throws IllegalOperationException if no music was playing right now.
     * @return the next [Music] in the queue, or `null` if there is no next music.
     */
    fun playNextMusic(): Music? {
        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")


        return musicQueue.getOrNull(musicQueue.indexOf(playingMusic).plus(1))?.apply {
            playingMusic.stop()
            resume()
        }
    }

    /**
     * Stops the current playing music and plays the previous one in the queue.
     *  Does nothing if there is no previous music in the queue.
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

        return musicQueue.getOrNull(musicQueue.indexOf(playingMusic).minus(1))?.apply {
            playingMusic.stop()
            resume()
        }
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

    /**
     * Pauses the now playing music.
     *
     * @param time the time user wants to pause the music.
     *
     * @throws IllegalOperationException if no music was playing right now.
     * @see Music.pause
     */
    fun pausePlayingMusic(time: Long) {
        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")

        playingMusic.pause(time)
    }

    /**
     * Resumes a music and stops now playing music in the queue if there is any.
     *
     * @param music the music user wants to resume.
     *
     * @throws IllegalOperationException if music queue did not contain the music.
     * @see Music.resume
     */
    fun resumeMusic(music: Music): Long {
        if (musicQueue.contains(music).not())
            throw IllegalOperationException("Music queue does not contain this music")

        getPlayingMusic()?.stop()
        return music.resume()
    }

    /**
     * Stops the now playing music.
     *
     * @throws IllegalOperationException if no music was playing right now.
     * @see Music.stop
     */
    fun stopPlayingMusic() {
        val playingMusic =
            getPlayingMusic() ?: throw IllegalOperationException("No music is playing")

        playingMusic.stop()
    }

    /* Test Only functions */

    @TestOnly
    fun getMusicQueue() = musicQueue

    @TestOnly
    fun setMusicQueue(queue: MutableList<Music>) {
        musicQueue = queue
    }
}