package ua.tumakha.yuriy.xmpp.light.server;

import org.apache.vysper.xml.fragment.Attribute;
import org.apache.vysper.xml.fragment.XMLElement;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.modules.core.base.handler.MessageHandler;
import org.apache.vysper.xmpp.server.ServerRuntimeContext;
import org.apache.vysper.xmpp.server.SessionContext;
import org.apache.vysper.xmpp.stanza.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.Message;
import ua.tumakha.yuriy.xmpp.light.service.MessageService;

import java.util.Date;

/**
 * @author Yuriy Tumakha.
 */
@Component
public class MyMessageHandler extends MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyMessageHandler.class);

    @Autowired
    private MessageService messageService;

    @Override
    protected Stanza executeCore(XMPPCoreStanza stanza, ServerRuntimeContext serverRuntimeContext,
                                 boolean isOutboundStanza, SessionContext sessionContext) {

        Entity from = stanza.getFrom();
        if (from == null || !from.isResourceSet()) {
            // rewrite stanza with new from
            String resource = serverRuntimeContext.getResourceRegistry()
                    .getUniqueResourceForSession(sessionContext);
            if (resource == null)
                throw new IllegalStateException("could not determine unique resource");
            from = new EntityImpl(sessionContext.getInitiatingEntity(), resource);
            StanzaBuilder stanzaBuilder = new StanzaBuilder(stanza.getName(), stanza.getNamespaceURI());
            for (Attribute attribute : stanza.getAttributes()) {
                if ("from".equals(attribute.getName()))
                    continue;
                stanzaBuilder.addAttribute(attribute);
            }
            stanzaBuilder.addAttribute("from", from.getFullQualifiedName());
            for (XMLElement preparedElement : stanza.getInnerElements()) {
                stanzaBuilder.addPreparedElement(preparedElement);
            }
            stanza = XMPPCoreStanza.getWrapper(stanzaBuilder.build());
        }

        MessageStanza messageStanza = (MessageStanza) stanza;
        MessageStanzaType messageStanzaType = messageStanza.getMessageType();
        switch (messageStanzaType) {
            case NORMAL:
            case CHAT:
            case GROUPCHAT:
                try {
                    Message message = new Message();
                    message.setFromJID(messageStanza.getFrom().getBareJID().getFullQualifiedName());
                    message.setToJID(messageStanza.getTo().getBareJID().getFullQualifiedName());
                    message.setBody(messageStanza.getBodies().values().iterator().next().getSingleInnerText().getText());
                    message.setTime(new Date().getTime());
                    messageService.saveMessage(message);
                } catch (Exception e) {
                    LOG.error("Save message failed.", e);
                }
        }
        return super.executeCore(stanza, serverRuntimeContext, isOutboundStanza, sessionContext);
    }
}
