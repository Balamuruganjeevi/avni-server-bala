package org.avni.server.service;

import org.avni.server.domain.Concept;
import org.avni.server.domain.ConceptDataType;
import org.avni.server.domain.ObservationCollection;
import org.avni.server.domain.factory.metadata.ConceptBuilder;
import org.avni.server.domain.factory.txData.ObservationCollectionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MediaObservationServiceTest {
    @Mock
    private ObservationService observationService;
    @Mock
    private S3Service s3Service;
    private MediaObservationService mediaObservationService;

    @Before
    public void before() {
        initMocks(this);
        mediaObservationService = new MediaObservationService(observationService, s3Service);
    }

    @Test
    public void processMediaObservations() throws IOException {
        ObservationCollection observationCollection = new ObservationCollectionBuilder()
                .addObservation("m1", "url1")
                .addObservation("m2", Arrays.asList("url2", "url3"))
                .addObservation("other", "foo")
                .build();
        Concept concept1 = new ConceptBuilder()
                .withId(1)
                .withDataType(ConceptDataType.Audio)
                .withUuid("m1")
                .build();
        Concept concept2 = new ConceptBuilder()
                .withId(2)
                .withDataType(ConceptDataType.Audio)
                .withUuid("m2")
                .build();
        Map<Concept, Object> mediaObservationsMap = new HashMap<Concept, Object>() {{
            put(concept1, "url1");
            put(concept2, Arrays.asList("url2", "url3"));
        }};

        when(observationService.filterObservationsByDataType(any(), any())).thenReturn(mediaObservationsMap);
        when(s3Service.uploadFileToS3(any())).thenReturn("new-value");
        when(s3Service.isInternalUrl("url1")).thenReturn(false);
        when(s3Service.isInternalUrl("url2")).thenReturn(false);
        when(s3Service.isInternalUrl("url3")).thenReturn(true);
        mediaObservationService.processMediaObservations(observationCollection);
        assertEquals("new-value", observationCollection.get("m1"));
        List<String> m2Values = (List<String>) observationCollection.get("m2");
        assertEquals("new-value", m2Values.get(0));
        assertEquals("url3", m2Values.get(1));
    }
}
