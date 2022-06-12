package ir.balad.musicplayer.player

import ir.balad.musicplayer.entity.Music
import ir.balad.musicplayer.entity.Player
import ir.balad.musicplayer.entity.User
import ir.balad.musicplayer.exception.IllegalQueueSizeException
import ir.balad.musicplayer.exception.NotPremiumUserException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PlayerRegularUserTest {

    private lateinit var player: Player
    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        player = Player()
        user = User(User.UserType.REGULAR)
    }

    @Test
    fun replace_smallMusicListRegularUser_IllegalQueueSizeExceptionThrows() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(4) { musicList.add(Music(it.toString())) }

        assertThrows(IllegalQueueSizeException::class.java) {
            player.replaceMusicQueue(musicList, user)
        }
    }

    @Test
    fun replace_musicListRegularUser_shuffledListReplaced() {
        // create music queue
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }

        player.replaceMusicQueue(musicList, user)

        assertEquals(
            musicList,
            player.getMusicQueue().sortedBy { it.id },
            "Music List Not Replaced"
        )
        assertNotEquals(musicList, player.getMusicQueue(), "Music List Not Shuffled")
    }

    @Test
    fun previous_playPreviousMusicRegularUser_NotPremiumUserExceptionThrows() {
        // create music queue and play one of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        player.getMusicQueue()[2].resume()

        assertThrows(NotPremiumUserException::class.java) {
            player.playPreviousMusic(user)
        }
    }

    @Test
    fun addToQueue_addToQueueMusicRegularUser_NotPremiumUserExceptionThrows() {
        // create music queue and play one of them
        val musicList = mutableListOf<Music>()
        repeat(5) { musicList.add(Music(it.toString())) }
        player.setMusicQueue(musicList)
        player.getMusicQueue()[2].resume()

        assertThrows(NotPremiumUserException::class.java) {
            player.addMusicToQueue(Music("6"), user)
        }
    }
}