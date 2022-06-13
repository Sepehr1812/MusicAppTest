package ir.balad.musicplayer.player

import ir.balad.musicplayer.entity.Music
import ir.balad.musicplayer.entity.Player
import ir.balad.musicplayer.exception.IllegalOperationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.random.Random


/**
 * Tests general features of th Player, independent from user type.
 *
 * @author Sepi 6/13/22
 */
internal class PlayerGeneralTest {

    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
        player = Player()
    }

    @Test
    fun pause_pauseWhenNoPlayingMusic_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        val time = Random.nextLong(10000, 60000) // 10 seconds to 1 minute
        assertThrows(IllegalOperationException::class.java) {
            player.pausePlayingMusic(time)
        }
    }

    @Test
    fun pause_pauseNowPlayingMusic_musicPaused() {
        // create music queue and play one of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[2].apply { resume() }

        val time = Random.nextLong(10000, 60000) // 10 seconds to 1 minute
        player.pausePlayingMusic(time)

        assertEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state
        )
        assertEquals(
            time,
            nowPlayingMusic.duration
        )
    }

    @Test
    fun resume_resumeWhenNoArgMusicInQueue_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        assertThrows(IllegalOperationException::class.java) {
            player.resumeMusic(Music("6"))
        }
    }

    @Test
    fun resume_resumeWhenAMusicInQueue_musicResumedAndOthersInQueueStoppedAndDurationTimeReturned() {
        // create music queue and play one of them
        val nextPlayingMusicIndex = 3
        val musicList = mutableListOf<Music>()
        val time = Random.nextLong(10000, 60000) // 10 seconds to 1 minute
        repeat(5) {
            musicList.add(
                Music(
                    it.toString(),
                    duration = if (it == nextPlayingMusicIndex) time /* 30 seconds */ else 0
                )
            )
        }
        player.setMusicQueue(musicList)
        player.getMusicQueue()[2].apply { resume() }

        val duration = player.resumeMusic(Music(nextPlayingMusicIndex.toString(), duration = time))

        assertFalse(
            player.getMusicQueue().apply { removeAt(nextPlayingMusicIndex) }.map { it.state }
                .contains(Music.MusicState.PLAYING),
            "Resume - at least one wrong music is not stopped"
        )
        assertEquals(time, duration, "Resume - returned time is different from paused time")
    }

    @Test
    fun stop_stopWhenNoPlayingMusic_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        assertThrows(IllegalOperationException::class.java) {
            player.stopPlayingMusic()
        }
    }

    @Test
    fun stop_stopNowPlayingMusic_musicStopped() {
        // create music queue and play one of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[2].apply { resume() }

        player.stopPlayingMusic()

        assertEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state
        )
        assertEquals(
            0,
            nowPlayingMusic.duration
        )
    }

    @Test
    fun next_playNextMusicWhenNoMusicPlaying_IllegalOperationExceptionThrew() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)

        assertThrows(IllegalOperationException::class.java) {
            player.playNextMusic()
        }
    }

    @Test
    fun next_playNextMusicWhenAMiddleQueueMusicPlaying_nowPlayingStopsAndNextMusicReturned() {
        // create music queue and play one of them
        val nowPlayingMusicIndex = 2
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[nowPlayingMusicIndex].apply { resume() }

        val nextMusic = player.playNextMusic()
        assertEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state,
            "Next - current music did not stopped"
        )
        assertEquals(
            Music.MusicState.PLAYING,
            player.getMusicQueue()[nowPlayingMusicIndex.plus(1)].state,
            "Next - next music did not played"
        )
        assertEquals(
            Music.MusicState.PLAYING,
            nextMusic?.state,
            "Next - returned music did not played"
        )
    }

    @Test
    fun next_playNextMusicWhenALastOfQueueMusicPlaying_nowPlayingStopsAndNullReturned() {
        // create music queue and play the last of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        val nowPlayingMusic = player.getMusicQueue()[4].apply { resume() }

        val nextMusic = player.playNextMusic()
        assertNotEquals(
            Music.MusicState.STOPPED,
            nowPlayingMusic.state,
            "Next - current music stopped"
        )
        assertNull(nextMusic, "Next - next music was not null")
    }
}