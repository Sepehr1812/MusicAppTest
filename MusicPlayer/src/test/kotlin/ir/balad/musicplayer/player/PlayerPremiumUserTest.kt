package ir.balad.musicplayer.player

import ir.balad.musicplayer.entity.Music
import ir.balad.musicplayer.entity.Player
import ir.balad.musicplayer.entity.User
import ir.balad.musicplayer.exception.IllegalOperationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


/**
 * Tests user type depended features with a Premium User.
 *
 * @author Sepi 6/13/22
 */
internal class PlayerPremiumUserTest {

    private lateinit var player: Player
    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        player = Player()
        user = User(User.UserType.PREMIUM)
    }

    @Test
    fun replace_musicListPremiumUser_exactListReplaced() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(4) { musicList.add(Music(it.toString())) }

        player.replaceMusicQueue(musicList, user)

        assertEquals(
            musicList,
            player.getMusicQueue(),
            "Music List Not Replaced Exactly"
        )
    }

    @Test
    fun previous_playPreviousMusicPremiumUserWhenNoMusicPlaying_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        assertThrows(IllegalOperationException::class.java) {
            player.playPreviousMusic(user)
        }
    }

    @Test
    fun previous_playPreviousMusicPremiumUserWhenAMiddleQueueMusicPlaying_nowPlayingStopsAndPreviousMusicReturned() {
        // create music queue and play one of them
        val nowPlayingMusicIndex = 2
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[nowPlayingMusicIndex].apply { resume() }

        val previousMusic = player.playPreviousMusic(user)
        assertEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state,
            "Previous - current music did not stopped"
        )
        assertEquals(
            Music.MusicState.PLAYING,
            player.getMusicQueue()[nowPlayingMusicIndex.minus(1)].state,
            "Previous - previous music did not played"
        )
        assertEquals(
            Music.MusicState.PLAYING,
            previousMusic?.state,
            "Previous - returned music did not played"
        )
    }

    @Test
    fun previous_playPreviousMusicPremiumUserWhenAFirstOfQueueMusicPlaying_nowPlayingStopsAndNullReturned() {
        // create music queue and play the first of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[0].apply { resume() }

        val previousMusic = player.playPreviousMusic(user)
        assertNotEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state,
            "Previous - current music stopped"
        )
        assertNull(previousMusic, "Previous - previous music was not null")
    }

    @Test
    fun addToQueue_addToQueueMusicPremiumUser_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        assertThrows(IllegalOperationException::class.java) {
            player.addMusicToQueue(Music("6"), user)
        }
    }

    @Test
    fun addToQueue_addToQueueMusicPremiumUser_newMusicAdded() {
        // create music queue and play one of them
        val nowPlayingMusicIndex = 2
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        player.getMusicQueue()[nowPlayingMusicIndex].resume()

        val newMusic = Music("6")
        player.addMusicToQueue(newMusic, user)
        assertEquals(
            musicList.apply { add(nowPlayingMusicIndex.plus(1), newMusic) },
            player.getMusicQueue()
        )
    }
}