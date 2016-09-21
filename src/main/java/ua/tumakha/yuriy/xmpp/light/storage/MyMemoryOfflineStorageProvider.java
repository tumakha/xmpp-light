package ua.tumakha.yuriy.xmpp.light.storage;

import org.apache.vysper.xml.fragment.Attribute;
import org.apache.vysper.xml.fragment.XMLElement;
import org.apache.vysper.xmpp.modules.extension.xep0160_offline_storage.MemoryOfflineStorageProvider;
import org.apache.vysper.xmpp.stanza.Stanza;
import org.apache.vysper.xmpp.stanza.StanzaBuilder;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;

/**
 * @author Yuriy Tumakha.
 */
public class MyMemoryOfflineStorageProvider extends MemoryOfflineStorageProvider {

    private static final DateTimeFormatter ZONED_DATE_TIME = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final Clock clock = Clock.systemDefaultZone();

    private String domain;

    public MyMemoryOfflineStorageProvider(String domain) {
        this.domain = domain;
    }

    @Override
    protected void storeStanza(Stanza stanza) {
        StanzaBuilder stanzaBuilder = StanzaBuilder.createClone(stanza, true, null);
        XMLElement delay = new XMLElement("urn:xmpp:delay", "delay", null,
                asList(new Attribute("from", domain),
                        new Attribute("stamp", ZonedDateTime.now(clock).format(ZONED_DATE_TIME))), null);
        Stanza delayedStanza = stanzaBuilder.addPreparedElement(delay).build();
        super.storeStanza(delayedStanza);
    }

}
