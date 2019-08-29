package com.sapl.retailerorderingmsdpharma.customView;

import junit.framework.TestCase;

import java.awt.font.TextAttribute;

/**
 * Created by ACER on 24-05-2017.
 */

public class TextAttributeTest extends TestCase {
    public void testAttributeNames() {
        assertEquals("java.awt.font.TextAttribute(kerning)",
                TextAttribute.KERNING.toString());
        assertEquals("java.awt.font.TextAttribute(ligatures)",
                TextAttribute.LIGATURES.toString());
        assertEquals("java.awt.font.TextAttribute(tracking)",
                TextAttribute.TRACKING.toString());
    }

    public void testAttributeValues() {
        assertEquals(new Integer(1), TextAttribute.KERNING_ON);
        assertEquals(new Integer(1), TextAttribute.LIGATURES_ON);
        assertEquals(new Float(0.04f), TextAttribute.TRACKING_LOOSE);
        assertEquals(new Float(-0.04f), TextAttribute.TRACKING_TIGHT);
    }
}
