package com.signde.androidvideooverlay

import org.junit.Assert.assertEquals
import org.junit.Test

class HdrOutputParserTest {
    @Test fun recognizesHdr10ObservedDuringPrimePlayback() {
        assertEquals("HDR10", HdrOutputParser.parse("HDR10-GAMMA_ST2084"))
        assertEquals("HDR10", HdrOutputParser.parse("HDR10-GAMMA_ST2084\n\r"))
    }

    @Test fun distinguishesOtherHdrSignalingModes() {
        assertEquals("HDR10+", HdrOutputParser.parse("HDR10Plus-VSIF"))
        assertEquals("HLG", HdrOutputParser.parse("HDR10-GAMMA_HLG"))
        assertEquals("Dolby Vision", HdrOutputParser.parse("DolbyVision-Std"))
        assertEquals("Dolby Vision (LL)", HdrOutputParser.parse("DolbyVision-Lowlatency"))
        assertEquals("HDR (other)", HdrOutputParser.parse("HDR10-others"))
    }

    @Test fun reflectsReturnToSdrWithoutRetainingHdr() {
        assertEquals("HDR10", HdrOutputParser.parse("HDR10-GAMMA_ST2084"))
        assertEquals("SDR", HdrOutputParser.parse("SDR"))
    }

    @Test fun missingUnknownAndCapabilitiesAreNotSdrOrActiveHdr() {
        for (value in listOf("", "Permission denied", "cat: I/O error", "unknown",
            "supportedHdrTypes=[2, 3, 4]", "420,10bit")) {
            assertEquals("", HdrOutputParser.parse(value))
        }
    }
}
