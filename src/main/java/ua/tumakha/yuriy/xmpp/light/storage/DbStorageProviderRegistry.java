package ua.tumakha.yuriy.xmpp.light.storage;

import org.apache.vysper.storage.OpenStorageProviderRegistry;
import org.apache.vysper.xmpp.modules.extension.xep0060_pubsub.storageprovider.CollectionNodeInMemoryStorageProvider;
import org.apache.vysper.xmpp.modules.extension.xep0060_pubsub.storageprovider.LeafNodeInMemoryStorageProvider;
import org.apache.vysper.xmpp.modules.roster.persistence.MemoryRosterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Yuriy Tumakha.
 */
@Component
public class DbStorageProviderRegistry extends OpenStorageProviderRegistry {

    @Autowired
    private DbUserManagement dbUserManagement;

    @PostConstruct
    public void init() {
        add(dbUserManagement);
        add(new MemoryRosterManager());
        add(new LeafNodeInMemoryStorageProvider());
        add(new CollectionNodeInMemoryStorageProvider());
    }

}
