package ir.balad.musicplayer.entity


/**
 * Represents the operator user of [Player].
 *
 * @author Sepi 6/13/22
 */
data class User(val type: UserType) {

    enum class UserType {
        REGULAR,
        PREMIUM
    }
}
