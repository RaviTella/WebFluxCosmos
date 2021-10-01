package com.webFlux.cosmos.resilience.cosmos;

import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.ThroughputProperties;
import com.webFlux.cosmos.resilience.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CosmosDB {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String endpoint;
    private String key;
    private String databaseName;
    private String containerName;
    private List<String> locations;
    private CosmosAsyncClient client;
    private CosmosAsyncDatabase database;
    private CosmosAsyncContainer container;

    public CosmosDB(@Value("${database.endpoint}") final String endpoint, @Value("${database.key}") final String key,
                    @Value("${database.databaseName}") final String databaseName, @Value("${database.containerName.book}") final String containerName,
                    @Value("#{'${database.locations}'.split(',')}") final List<String> locations) {
        this.endpoint = endpoint;
        this.key = key;
        this.databaseName = databaseName;
        this.containerName = containerName;
        this.locations = locations;
        cosmosSetup();
    }


    private void cosmosSetup() {
        CosmosContainerProperties containerProperties = new CosmosContainerProperties(containerName, "/category");
        buildAndGetClient()
                .createDatabaseIfNotExists(databaseName)
                .flatMap(databaseResponse -> {
                    database = client.getDatabase(databaseResponse
                            .getProperties()
                            .getId());
                    return database
                            .createContainerIfNotExists(containerProperties, ThroughputProperties.createManualThroughput(400));
                })
                .flatMap(containerResponse -> {
                    container = database.getContainer(containerResponse
                            .getProperties()
                            .getId());
                    return Mono.empty();
                })
                .block();
    }


    private CosmosAsyncClient buildAndGetClient() {
        DirectConnectionConfig directConnectionConfig = new DirectConnectionConfig();
        directConnectionConfig.setConnectionEndpointRediscoveryEnabled(true);
        if (client == null) {
            logger.info(endpoint);
            client = new CosmosClientBuilder()
                    .endpoint(endpoint)
                    .key(key)
                    .userAgentSuffix("WebFluxCosmos")
                    .preferredRegions(locations)
                    .consistencyLevel(ConsistencyLevel.SESSION)
                    .directMode(directConnectionConfig)
                    .buildAsyncClient();
            return client;
        }
        return client;
    }

    public CosmosAsyncContainer getContainer() {
        return container;
    }

}
