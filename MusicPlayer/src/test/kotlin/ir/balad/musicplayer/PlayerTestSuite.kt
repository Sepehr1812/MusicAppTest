package ir.balad.musicplayer

import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite


/**
 * Tests all Test classes in [ir.balad.musicplayer.player] package.
 * It covers functionalities of Player entity.
 *
 * @author Sepi 6/13/22
 */
@SelectPackages("ir.balad.musicplayer.player")
@Suite
internal class PlayerTestSuite