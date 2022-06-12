package ir.balad.musicplayer.entity

data class User(val type: UserType) {

    enum class UserType {
        REGULAR,
        PREMIUM
    }
}
