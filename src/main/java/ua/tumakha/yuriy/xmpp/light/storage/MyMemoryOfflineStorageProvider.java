package ua.tumakha.yuriy.xmpp.light.storage;

import org.apache.vysper.xmpp.modules.extension.xep0045_muc.stanzas.Delay;
import org.apache.vysper.xmpp.modules.extension.xep0160_offline_storage.MemoryOfflineStorageProvider;
import org.apache.vysper.xmpp.stanza.Stanza;
import org.apache.vysper.xmpp.stanza.StanzaBuilder;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Yuriy Tumakha.
 */
public class MyMemoryOfflineStorageProvider extends MemoryOfflineStorageProvider {

    @Override
    protected void storeStanza(Stanza stanza) {
        StanzaBuilder stanzaBuilder = StanzaBuilder.createClone(stanza, true, null);
        Delay delay = new Delay(stanza.getFrom().getBareJID(), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        Stanza delayedStanza = stanzaBuilder.addPreparedElement(delay).build();
        super.storeStanza(delayedStanza);
    }

}
