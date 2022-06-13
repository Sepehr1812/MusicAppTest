package ir.balad.musicplayer.exception


/**
 * [Exception] is thrown when user wants to use a queue with a not-allowed size.
 *
 *  @author Sepi 6/13/22
 */
class IllegalQueueSizeException(override val message: String?) : Exception(message)