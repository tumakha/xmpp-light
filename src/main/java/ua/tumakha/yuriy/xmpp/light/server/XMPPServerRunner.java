package ua.tumakha.yuriy.xmpp.light.server;

import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.Anonymous;
import org.apache.vysper.xmpp.authorization.SASLMechanism;
import org.apache.vysper.xmpp.modules.core.base.handler.MessageHandler;
import org.apache.vysper.xmpp.modules.extension.xep0045_muc.MUCModule;
import org.apache.vysper.xmpp.modules.extension.xep0045_muc.model.Conference;
import org.apache.vysper.xmpp.modules.extension.xep0045_muc.model.RoomType;
import org.apache.vysper.xmpp.modules.extension.xep0060_pubsub.PublishSubscribeModule;
import org.apache.vysper.xmpp.modules.extension.xep0077_inbandreg.InBandRegistrationModule;
import org.apache.vysper.xmpp.modules.extension.xep0092_software_version.SoftwareVersionModule;
import org.apache.vysper.xmpp.modules.extension.xep0119_xmppping.XmppPingModule;
import org.apache.vysper.xmpp.modules.extension.xep0202_entity_time.EntityTimeModule;
import org.apache.vysper.xmpp.protocol.DefaultHandlerDictionary;
import org.apache.vysper.xmpp.protocol.HandlerDictionary;
import org.apache.vysper.xmpp.protocol.StanzaHandler;
import org.apache.vysper.xmpp.server.DefaultServerRuntimeContext;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.apache.vysper.xmpp.stanza.MessageStanza;
import org.apache.vysper.xmpp.stanza.Stanza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Yuriy Tumakha
 */
@Component
public class XMPPServerRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(XMPPServerRunner.class);

    @Autowired
    private Environment env;

    @Autowired
    private StorageProviderRegistry storageProviderRegistry;

    @Autowired
    private MyMessageHandler myMessageHandler;

    private String domain;
    private int xmppPort;
    private String keystore;
    private String keystorePassword;

    @PostConstruct
    public void init() {
        domain = env.getProperty("xmpp.domain");
        xmppPort = env.getProperty("xmpp.clients.port", Integer.class, 5222);
        keystore = env.getProperty("xmpp.keystore.path");
        keystorePassword = env.getProperty("xmpp.keystore.password");
    }

    @Override
    public void run(String... args) throws Exception {

        XMPPServer xmppServer = new XMPPServer(domain);
        xmppServer.setStorageProviderRegistry(storageProviderRegistry);

        final TCPEndpoint endpoint = new TCPEndpoint();
        endpoint.setPort(xmppPort);
        xmppServer.addEndpoint(endpoint);

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(keystore);
        xmppServer.setTLSCertificateInfo(is, keystorePassword);

        // allow anonymous authentication
        xmppServer.setSASLMechanisms(Arrays.asList(new SASLMechanism[]{new Anonymous()}));

        xmppServer.start();

        // add the multi-user chat module and create a room
        Conference conference = new Conference("Conference");
        conference.createRoom(EntityImpl.parseUnchecked("public@" + domain), "Public Room", RoomType.Public);

        xmppServer.addModule(new MUCModule("conference", conference));
        xmppServer.addModule(new InBandRegistrationModule());
        xmppServer.addModule(new XmppPingModule());
        xmppServer.addModule(new PublishSubscribeModule());
        xmppServer.addModule(new SoftwareVersionModule());

        // add MessageHandler
        HandlerDictionary handlerDictionary = new DefaultHandlerDictionary(myMessageHandler);
        ((DefaultServerRuntimeContext) xmppServer.getServerRuntimeContext()).addDictionary(handlerDictionary);

        LOG.info("XMPP Server is running on port {}", xmppPort);
    }

}
