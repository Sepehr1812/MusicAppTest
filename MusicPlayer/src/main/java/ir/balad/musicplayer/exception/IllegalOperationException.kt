package ir.balad.musicplayer.exception


/**
 * [Exception] is thrown when user wants to do a not-allowed operation in MusicPlayer;
 *  e.g. pausing a music that is already paused.
 *
 *  @author Sepi 6/13/22
 */
class IllegalOperationException(override val message: String?) : Exception(message)