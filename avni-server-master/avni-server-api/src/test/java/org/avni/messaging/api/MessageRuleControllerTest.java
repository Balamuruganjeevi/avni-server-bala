package org.avni.messaging.api;

import org.avni.messaging.service.MessagingService;
import org.avni.server.service.EntityTypeRetrieverService;
import org.avni.server.service.accessControl.AccessControlService;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageRuleControllerTest {

    @Test
    public void shouldGetAllIfEntityTypeNotProvided() {
        MessagingService messagingService = mock(MessagingService.class);
        AccessControlService accessControlService = mock(AccessControlService.class);
        EntityTypeRetrieverService entityTypeRetrieverService = mock(EntityTypeRetrieverService.class);
        when(messagingService.findAll(any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MessageRuleController messageRuleController = new MessageRuleController(messagingService, accessControlService, entityTypeRetrieverService);
        messageRuleController.find(null, null, PageRequest.of(0,10));

        verify(messagingService).findAll(any());
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldThrowErrorForNonExistentEntityType() {
        AccessControlService accessControlService = mock(AccessControlService.class);
        EntityTypeRetrieverService entityTypeRetrieverService = mock(EntityTypeRetrieverService.class);
        MessageRuleController messageRuleController = new MessageRuleController(null, accessControlService, entityTypeRetrieverService);
        messageRuleController.find("Non existent entity type", 5L, PageRequest.of(0,10));
    }
}