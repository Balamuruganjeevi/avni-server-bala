package org.avni.messaging.external;

import org.avni.messaging.contract.glific.*;
import org.avni.messaging.domain.GlificSystemConfig;
import org.avni.messaging.domain.exception.GlificConnectException;
import org.avni.messaging.domain.exception.GlificNotConfiguredException;
import org.avni.server.dao.externalSystem.ExternalSystemConfigRepository;
import org.avni.server.domain.extenalSystem.ExternalSystemConfig;
import org.avni.server.domain.extenalSystem.SystemName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class GlificRestClient {
    public static final String AUTH_URL = "/api/v1/session";
    private final String REQUEST_URL = "/api";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    private final ExternalSystemConfigRepository externalSystemConfigRepository;

    @Autowired
    public GlificRestClient(RestTemplateBuilder builder, ExternalSystemConfigRepository externalSystemConfigRepository) {
        this.restTemplate = builder.build();
        this.externalSystemConfigRepository = externalSystemConfigRepository;
    }

    public GlificAuth authenticate() throws GlificNotConfiguredException {
        HttpEntity<Object> request = new RequestObjectBuilder()
                .withRequestObject(new GlificAuthRequest(new GlificUser(getSystemConfig())))
                .withJsonContent()
                .withAccept()
                .build();

        return makeCall(AUTH_URL, request,
                new ParameterizedTypeReference<GlificResponse<GlificAuth>>() {
                });
    }

    public <T> T callAPI(Object requestObject, ParameterizedTypeReference<GlificResponse<T>> responseType) throws GlificNotConfiguredException {
        return callAPI(requestObject, responseType, this.authenticate());
    }

    public <T> T callAPI(Object requestObject, ParameterizedTypeReference<GlificResponse<T>> responseType, GlificAuth auth) throws GlificNotConfiguredException {
        HttpEntity<Object> request = new RequestObjectBuilder()
                .withRequestObject(requestObject)
                .withAuth(auth)
                .withJsonContent()
                .withAccept()
                .build();

        return makeCall(REQUEST_URL, request, responseType);
    }

    private <T> T makeCall(String url, HttpEntity<Object> request, ParameterizedTypeReference<GlificResponse<T>> responseType) throws GlificNotConfiguredException {
        String fullUrl = getSystemConfig().getBaseUrl() + (StringUtils.isEmpty(url) ? "/" : url);
        logger.info("Calling glific - {}", fullUrl);
        ResponseEntity<GlificResponse<T>> responseEntity = restTemplate.exchange(fullUrl, HttpMethod.POST, request, responseType);

        GlificResponse<T> response = responseEntity.getBody();
        if (response == null) { //Guard clause against null type responses from Glific
            return null;
        }
        if (response.hasErrors()) {
            logger.error("Error while calling Glific API: {}", url);
            logger.error("Request is {}", request.getBody());
            logger.error("Response is {}", response);
            throw new GlificConnectException(response.getErrors().stream().map(GlificError::getMessage).collect(Collectors.joining(", ")));
        }
        return response.getData();
    }

    private GlificSystemConfig getSystemConfig() throws GlificNotConfiguredException {
        ExternalSystemConfig externalSystemConfig = externalSystemConfigRepository.findBySystemName(SystemName.Glific);
        if (externalSystemConfig == null)
            throw new GlificNotConfiguredException("External system config not set up for organisation");
        return new GlificSystemConfig(externalSystemConfig);
    }

    private class RequestObjectBuilder {
        private final HttpHeaders headers;
        private Object requestObject;

        public RequestObjectBuilder() {
            this.headers = new HttpHeaders();
        }

        public RequestObjectBuilder withJsonContent() {
            headers.add("content-type", "application/json");
            return this;
        }

        public RequestObjectBuilder withAccept() {
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            return this;
        }

        public RequestObjectBuilder withAuth(GlificAuth glificAuth) {
            headers.set(HttpHeaders.AUTHORIZATION, glificAuth.getAccessToken());
            return this;
        }

        public HttpEntity<Object> build() {
            return new HttpEntity<>(requestObject, headers);
        }

        public RequestObjectBuilder withRequestObject(Object requestObject) {
            this.requestObject = requestObject;
            return this;
        }
    }
}

