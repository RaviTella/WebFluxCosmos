package com.webFlux.cosmos.resilience.model;

import com.azure.cosmos.models.*;
import com.webFlux.cosmos.resilience.cosmos.CosmosDB;
import com.webFlux.cosmos.resilience.util.Timer;
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


    public Mono<Book> upsertBook(Book book) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        cosmosItemRequestOptions.setContentResponseOnWriteEnabled(true);
        StringBuilder diagnosticLog = new StringBuilder();
        Timer timer = new Timer();
        long acceptableLatencyMS = 1300;
        return cosmosDB
                .getContainer()
                .upsertItem(book, new PartitionKey(book.getCategory()), cosmosItemRequestOptions)
                .map(cosmosItemResponse -> {
                    diagnosticLog.append(cosmosItemResponse
                            .getDiagnostics()
                            .toString());
                    return cosmosItemResponse.getItem();
                })
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnTerminate(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }
                });
    }

    public Mono<Book> finByIdAndCategory(String id, String category) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        StringBuilder diagnosticLog = new StringBuilder();
        Timer timer = new Timer();
        long acceptableLatencyMS = 100;
        return cosmosDB
                .getContainer()
                .readItem(id, new PartitionKey(category), cosmosItemRequestOptions, Book.class)
                .map(cosmosItemResponse -> {
                    diagnosticLog.append(cosmosItemResponse
                            .getDiagnostics()
                            .toString());
                    return cosmosItemResponse.getItem();
                })
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnTerminate(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }

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
        StringBuilder diagnosticLog = new StringBuilder();
        Timer timer = new Timer();
        long acceptableLatencyMS = 100;
        return cosmosDB
                .getContainer()
                .queryItems(querySpec, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> diagnosticLog.append(feedResponse
                        .getCosmosDiagnostics()
                        .toString()))
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnComplete(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }

                });

    }

    public Flux<Book> findByIsbn(String isbn) {
        CosmosQueryRequestOptions cosmosQueryRequestOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM o WHERE o.isbn =  @isbn";
        SqlParameter parameter = new SqlParameter("@isbn", isbn);
        List<SqlParameter> sqlParameters = new ArrayList<>();
        sqlParameters.add(parameter);
        SqlQuerySpec querySpec = new SqlQuerySpec(query, sqlParameters);
        StringBuilder diagnosticLog = new StringBuilder();
        long acceptableLatencyMS = 300;
        Timer timer = new Timer();
        return cosmosDB
                .getContainer()
                .queryItems(querySpec, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> diagnosticLog.append(feedResponse
                        .getCosmosDiagnostics()
                        .toString()))
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnComplete(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }
                });
    }


    public Flux<Book> findAll() {
        CosmosQueryRequestOptions cosmosQueryRequestOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM o";
        StringBuilder diagnosticLog = new StringBuilder();
        long acceptableLatencyMS = 500;
        Timer timer = new Timer();
        return cosmosDB
                .getContainer()
                .queryItems(query, cosmosQueryRequestOptions, Book.class)
                .handle(feedResponse -> diagnosticLog.append(feedResponse
                        .getCosmosDiagnostics()
                        .toString()))
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnComplete(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }
                });
    }


    public Mono<Book> create(Book book) {
        CosmosItemRequestOptions cosmosItemRequestOptions = new CosmosItemRequestOptions();
        cosmosItemRequestOptions.setContentResponseOnWriteEnabled(true);
        StringBuilder diagnosticLog = new StringBuilder();
        Timer timer = new Timer();
        long acceptableLatencyMS = 300;
        return cosmosDB
                .getContainer()
                .createItem(book, new PartitionKey(book.getCategory()), cosmosItemRequestOptions)
                .map(cosmosItemResponse -> {
                    diagnosticLog.append(cosmosItemResponse
                            .getDiagnostics()
                            .toString());
                    return cosmosItemResponse.getItem();
                })
                .doOnSubscribe(s -> {
                    timer.start();
                })
                .doOnTerminate(() -> {
                    timer.stop();
                    if (timer.getTotalTimeMillis() > acceptableLatencyMS) {
                        logger.info("Logging detailed diagnostics as the acceptable latency threshold of {} ms has be breached. Request Latency was {} ms. " +
                                "Following are the detailed diagnostics:  {}", acceptableLatencyMS, timer.getTotalTimeMillis(), diagnosticLog);
                    } else {
                        logger.info("Request Latency was {} ms and acceptable latency threshold was {} ms ",
                                timer.getTotalTimeMillis(), acceptableLatencyMS);
                    }

                });
    }

}
