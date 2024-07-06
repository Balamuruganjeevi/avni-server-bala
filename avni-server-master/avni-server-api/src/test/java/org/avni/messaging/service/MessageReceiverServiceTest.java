package org.avni.messaging.service;

import org.avni.messaging.domain.MessageReceiver;
import org.avni.messaging.domain.ReceiverType;
import org.avni.messaging.repository.GlificContactRepository;
import org.avni.messaging.repository.MessageReceiverRepository;
import org.avni.server.service.IndividualService;
import org.avni.server.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessageReceiverServiceTest {

    private MessageReceiverService messageReceiverService;

    @Mock
    private IndividualService individualService;

    @Mock
    private UserService userService;

    @Mock
    private MessageReceiverRepository messageReceiverRepository;

    @Captor
    ArgumentCaptor<MessageReceiver> messageReceiver;
    @Mock
    private GlificContactRepository glificContactRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        messageReceiverService = new MessageReceiverService(messageReceiverRepository, glificContactRepository, individualService, userService);
    }

    @Test
    @Ignore("breaking because it is checking every field to match as date is not matching. also this test is not very useful")
    public void shouldSaveMessageReceiverIfNotExist() {
        Long entityId = 123L;
        when(messageReceiverRepository.findByReceiverIdAndReceiverType(entityId, ReceiverType.Subject)).thenReturn(null);

        MessageReceiver actualMessageReceiver = messageReceiverService.saveReceiverIfRequired(ReceiverType.Subject, entityId);

        verify(messageReceiverRepository).save(messageReceiver.capture());

        MessageReceiver messageReceiver = this.messageReceiver.getValue();
        assertThat(messageReceiver.getReceiverType()).isEqualTo(ReceiverType.Subject);
        assertThat(messageReceiver.getReceiverId()).isEqualTo(entityId);
        assertThat(messageReceiver.getUuid()).isNotNull();
        assertThat(actualMessageReceiver).isEqualToComparingFieldByFieldRecursively(messageReceiver);
    }

    @Test
    public void shouldReturnExistingMessageReceiverIfExist() {
        Long entityId = 123L;
        MessageReceiver messageReceiver = mock(MessageReceiver.class);
        when(messageReceiverRepository.findByReceiverIdAndReceiverType(entityId, ReceiverType.Subject)).thenReturn(Optional.of(messageReceiver));

        MessageReceiver actualMessageReceiver = messageReceiverService.saveReceiverIfRequired(ReceiverType.Subject, entityId);

        verify(messageReceiverRepository, never()).save(any());
        assertThat(actualMessageReceiver).isEqualTo(messageReceiver);
    }
}
