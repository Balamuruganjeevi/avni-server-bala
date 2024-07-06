package org.avni.messaging.repository;

import org.avni.messaging.contract.glific.GlificContactResponse;
import org.avni.messaging.contract.glific.GlificOptinContactResponse;
import org.avni.messaging.contract.glific.GlificGetContactsResponse;
import org.avni.messaging.contract.glific.GlificOptinContactWithErrorsResponse;
import org.avni.messaging.domain.exception.GlificNotConfiguredException;
import org.avni.messaging.external.GlificRestClient;
import org.avni.messaging.service.PhoneNumberNotAvailableOrIncorrectException;
import org.avni.server.domain.Account;
import org.avni.server.domain.Organisation;
import org.avni.server.domain.UserContext;
import org.avni.server.domain.factory.TestAccountBuilder;
import org.avni.server.domain.factory.TestOrganisationBuilder;
import org.avni.server.domain.factory.UserContextBuilder;
import org.avni.server.framework.security.UserContextHolder;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GlificContactRepositoryTest {
    @Test
    public void shouldCreateContactIfNotAvailable() throws PhoneNumberNotAvailableOrIncorrectException, GlificNotConfiguredException {
        Account account = new TestAccountBuilder().withRegion("IN").build();
        Organisation organisation = new TestOrganisationBuilder().withAccount(account).build();
        UserContext userContext = new UserContextBuilder().withOrganisation(organisation).build();
        UserContextHolder.create(userContext);

        GlificRestClient glificRestClient = mock(GlificRestClient.class);
        GlificContactRepository repository = new GlificContactRepository(glificRestClient);
        GlificGetContactsResponse getContactsResponse = new GlificGetContactsResponse();
        getContactsResponse.setContacts(new ArrayList<>());
        GlificOptinContactResponse optinContactResponse = new GlificOptinContactResponse();
        GlificOptinContactWithErrorsResponse optinContactWithErrorsResponse = new GlificOptinContactWithErrorsResponse();
        GlificContactResponse contact = new GlificContactResponse();
        contact.setId("1234");
        optinContactWithErrorsResponse.setContact(contact);
        optinContactResponse.setOptinContact(optinContactWithErrorsResponse);
        when(glificRestClient
                .callAPI(any(), any()))
                .thenReturn(getContactsResponse)
                .thenReturn(optinContactResponse);

        String responseId = repository.getOrCreateContact("9182738475", "sam ram");

        assertThat(responseId).isEqualTo("1234");
        verify(glificRestClient).callAPI(eq("{\"query\":\"query contacts($filter: ContactFilter, $opts: Opts) { contacts(filter: $filter, opts:$opts) { id name optinTime optoutTime optinMethod optoutMethod phone maskedPhone bspStatus status tags { id label } groups { id label } }}\",\"variables\":{\"filter\":{\"phone\":\"919182738475\"},\"opts\":{\"order\":\"ASC\",\"limit\":10,\"offset\":0}}}"), any());
        verify(glificRestClient).callAPI(eq("{\"query\":\"mutation optinContact($phone: String!, $name: String) {optinContact(phone: $phone, name: $name) {contact {id phone name lastMessageAt optinTime bspStatus} errors {key message}}}\",\"variables\":{\"phone\":\"919182738475\",\"name\":\"sam ram\"}}"), any());
    }
}
