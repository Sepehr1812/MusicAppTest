# MusicAppTest
A simple Android app that contains a list with different numbers of cells on each row. It represents an implemented Music Player.

## MusicPlayer Module (Logic Only)
Three entities implement Music Player:
* **User**: Represents the user of the Music Player.
* **Music**: Represents the music that is used to play in the Music Player.
* **Player**: Implements the Music Player operations.

### Entities
**_User_** has only one property named `type` that represents the type of the user, whether REGULAR or PREMIUM.

**_Music_** has three properties:
- `id` that assigns an ID to each music and is used for identification.
- `state` that holds the playing state of the music, whether PLAYING or STOPPED.
- `duration` that holds the music playback time in milliseconds.<br>
Also, Music comprises _play_, _pause_, and _stop_ functions of itself.

**_Player_** has one property: `musicQueue` that holds the Music Queue of the player. Also, Player comprises methods for managing the Player functionalities, e.g., `getPlayingMusic()` and `addMusicToQueue()`.

### Exceptions
There are three classes of Exceptions:
* IllegalOperationException: Is thrown when the user wants to do a not-allowed operation in MusicPlayer, e.g., pausing a previously paused music.
* IllegalQueueSizeException: Is thrown when the user wants to use a queue with a not-allowed size.
* NotPremiumUserException: Is thrown when a Regular User wants to use a Premium-Only feature.

### Tests
Four classes test MusicPlayer functionalities by **_JUnit 5_**:
* PlayerRegularUserTest: Tests the features which are dependent on the type of user with a Regular User.
* PlayerPremiumUserTest: Tests the features which are dependent on the type of user with a Premium User.
* PlayerGeneralTest: It is a Test Suite that tests the general features of the player, independent from user type.
* PlayerTestSuite: Tests all these three Test classes, It covers functionalities of the Player entity.

## App Module (Presentation Layer Only)
A simple list presentation that has one cell on even index rows and two cells on odd index ones. The colors of cells are set randomly.
