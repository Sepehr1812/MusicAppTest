package ir.balad.musicplayer.exception


/**
 * [Exception] is thrown when a Regular User wants to use a Premium-Only feature.
 *
 *  @author Sepi 6/13/22
 */
class NotPremiumUserException(override val message: String?) : Exception(message)