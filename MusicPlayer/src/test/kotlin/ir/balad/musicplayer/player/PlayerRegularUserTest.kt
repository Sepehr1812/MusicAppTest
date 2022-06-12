package ir.balad.musicplayer.player

import ir.balad.musicplayer.entity.Music
import ir.balad.musicplayer.entity.Player
import ir.balad.musicplayer.entity.User
import ir.balad.musicplayer.exception.IllegalQueueSizeException
import org.junit.jupiter.api.Assertions.assertThrows
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
        val musicList = mutableListOf<Music>()
        repeat(4) { musicList.add(Music(it.toString())) }

        assertThrows(IllegalQueueSizeException::class.java) {
            player.replaceMusicQueue(musicList, user)
        }
    }
}