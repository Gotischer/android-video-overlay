package com.signde.androidvideooverlay

import org.junit.Assert.*
import org.junit.Test

class OverlayShortcutTest {
    @Test fun favoritesAcceptsSeiAndOnnKeys() {
        for (key in listOf(174, 313)) {
            val shortcut = OverlayShortcut()
            assertEquals(OverlayShortcut.Result(true), shortcut.handle(key, true, 0, 0, false, 174, false))
            assertEquals(OverlayShortcut.Result(true, true), shortcut.handle(key, false, 0, 80, false, 174, false))
            assertFalse(shortcut.handle(key, false, 0, 80, false, 174, false).toggle)
        }
    }

    @Test fun heldKeyRepeatsToggleOnlyOnce() {
        val shortcut = OverlayShortcut()
        shortcut.handle(313, true, 0, 0, false, 174, true)
        assertFalse(shortcut.handle(313, true, 1, 800, false, 174, true).toggle)
        assertTrue(shortcut.handle(313, false, 0, 900, false, 174, true).toggle)
    }

    @Test fun longPressIgnoresShortPressAndCancellation() {
        val shortcut = OverlayShortcut()
        shortcut.handle(174, true, 0, 0, false, 174, true)
        assertFalse(shortcut.handle(174, false, 0, 100, false, 174, true).toggle)
        shortcut.handle(174, true, 0, 0, false, 174, true)
        assertFalse(shortcut.handle(174, false, 0, 900, true, 174, true).toggle)
    }

    @Test fun otherKeysAndDisabledShortcutPassThrough() {
        val shortcut = OverlayShortcut()
        assertFalse(shortcut.handle(23, true, 0, 0, false, 174, false).consumed)
        assertFalse(shortcut.handle(313, true, 0, 0, false, 172, false).consumed)
        assertFalse(shortcut.handle(0, true, 0, 0, false, 0, false).consumed)
    }

    @Test fun changingShortcutDropsPendingPress() {
        val shortcut = OverlayShortcut()
        shortcut.handle(174, true, 0, 0, false, 174, false)
        shortcut.reset()
        assertFalse(shortcut.handle(174, false, 0, 100, false, 174, false).toggle)
    }
}
