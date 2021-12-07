// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.newdawn.slick.tests.xml;

import org.newdawn.slick.util.xml.*;

public class ObjectParserTest
{
    public static void main(final String[] argv) throws SlickXMLException {
        final ObjectTreeParser parser = new ObjectTreeParser("org.newdawn.slick.tests.xml");
        parser.addElementMapping("Bag", ItemContainer.class);
        final GameData parsedData = (GameData)parser.parse("testdata/objxmltest.xml");
        parsedData.dump("");
    }
}
