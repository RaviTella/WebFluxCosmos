package com.webFlux.cosmos.resilience.model;

import com.azure.cosmos.models.*;
import com.webFlux.cosmos.resilience.cosmos.CosmosDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CosmosDB cosmosDB;

    @Autowired
    public BookRepository(CosmosDB cosmosDB) {
        this.cosmosDB = cosmosDB;
    }


    public Mono<CosmosItemResponse<Book>> upsertBook(Book book) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        return cosmosDB
                .getContainer()
                .upsertItem(book, new PartitionKey(book.getCategory()), cosmosItemRequestOptions)
                .handle(((itemResponse, bookSynchronousSink) -> logger.info(itemResponse
                        .getDiagnostics()
                        .toString())));
    }

    public Mono<Book> finByIdAndCategory(String id, String category) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        return cosmosDB
                .getContainer()
                .readItem(id, new PartitionKey(category), cosmosItemRequestOptions, Book.class)
                .map(cosmosItemResponse -> {
                    logger.info(cosmosItemResponse
                            .getDiagnostics()
                            .toString());
                    return cosmosItemResponse.getItem();
                });
    }

    public Flux<Book> findByCategory(String category) {
        CosmosQueryRequestOptions cosmosQueryRequestOptions = new CosmosQueryRequestOptions();
        cosmosQueryRequestOptions.setPartitionKey(new PartitionKey(category));
        String query = "SELECT * FROM o WHERE o.category =  'Databases'";
        SqlParameter parameter = new SqlParameter("@category", category);
        List<SqlParameter> sqlParameters = new ArrayList<>();
        sqlParameters.add(parameter);
        SqlQuerySpec querySpec = new SqlQuerySpec(query, sqlParameters);
        return cosmosDB
                .getContainer()
                .queryItems(querySpec, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> logger.info(feedResponse
                        .getCosmosDiagnostics()
                        .toString()));
    }

    public Flux<Book> findByIsbn(String isbn) {
        CosmosQueryRequestOptions cosmosQueryRequestOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM o WHERE o.isbn =  @isbn";
        SqlParameter parameter = new SqlParameter("@isbn", isbn);
        List<SqlParameter> sqlParameters = new ArrayList<>();
        sqlParameters.add(parameter);
        SqlQuerySpec querySpec = new SqlQuerySpec(query, sqlParameters);
        return cosmosDB
                .getContainer()
                .queryItems(querySpec, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> logger.info(feedResponse
                        .getCosmosDiagnostics()
                        .toString()));

    }

    public Flux<Book> findAll() {
        CosmosQueryRequestOptions cosmosQueryRequestOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM o";
        return cosmosDB
                .getContainer()
                .queryItems(query, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> logger.info(feedResponse
                        .getCosmosDiagnostics()
                        .toString()));
    }

    public Mono<Book> create(Book book) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        return cosmosDB
                .getContainer()
                .createItem(book, new PartitionKey(book.getCategory()), cosmosItemRequestOptions)
                .handle(((itemResponse, bookSynchronousSink) -> logger.info(itemResponse
                        .getDiagnostics()
                        .toString())));
    }


    public Flux<Book> findAllByCategor1y(String category) {
        Flux<Book> books = cosmosDB
                .getContainer()
                .readAllItems(new PartitionKey(category), new CosmosQueryRequestOptions(), Book.class)
                .handle(feedResponse -> logger.info(feedResponse
                        .getCosmosDiagnostics()
                        .toString()));
        return books;
    }


}
