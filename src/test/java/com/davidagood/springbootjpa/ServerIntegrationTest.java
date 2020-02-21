package com.davidagood.springbootjpa;

import com.davidagood.springbootjpa.entity.Relation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String relationUrl;

    @PostConstruct
    void init() {
        relationUrl = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("relation")
            .toUriString();
    }

    @Test
    void getAllRelations() {
        List<Relation> relations = requestAllRelations();
        assertThat(relations).isEmpty();
    }

    @Test
    void createRelation() {
        int parentId = 1;
        int childId = 2;
        Relation relation = requestCreateRelation(parentId, childId);
        validateRelation(relation, parentId, childId);
    }

    @Test
    void deleteAllRelations() {
        int parentId = 1;
        int childId = 2;
        Relation relation = requestCreateRelation(parentId, childId);
        validateRelation(relation, parentId, childId);

        List<Relation> relations = requestAllRelations();
        assertThat(relations).hasSize(1);
        Relation relationResponse = relations.get(0);
        assertThat(relationResponse.getParentId()).isEqualTo(parentId);
        assertThat(relationResponse.getChildId()).isEqualTo(childId);

        var deleteAllRelationsRequest = RequestEntity.delete(URI.create(relationUrl))
            .accept(MediaType.APPLICATION_JSON)
            .build();
        restTemplate.exchange(deleteAllRelationsRequest, Void.class);

        List<Relation> relationsAfterDeletion = requestAllRelations();
        assertThat(relationsAfterDeletion).isEmpty();
    }

    private List<Relation> requestAllRelations() {
        RequestEntity<Void> request = RequestEntity.get(URI.create(relationUrl))
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<Relation[]> response = restTemplate.exchange(request, Relation[].class);
        return Arrays.asList(response.getBody());
    }

    private void validateRelation(Relation relation, int parentId, int childId) {
        assertThat(relation.getParentId()).isEqualTo(parentId);
        assertThat(relation.getChildId()).isEqualTo(childId);
        assertThat(relation.getId()).isNotNull();
    }

    private Relation requestCreateRelation(int parentId, int childId) {
        URI uri = UriComponentsBuilder
            .fromUriString(relationUrl)
            .queryParam("parent", "{parentId}")
            .queryParam("child", "{childId}")
            .build(parentId, childId);
        var request = RequestEntity.post(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        var response = restTemplate.exchange(request, Relation.class);
        return response.getBody();
    }

}
