package com.webFlux.cosmos.resilience.controller;

import com.azure.cosmos.CosmosException;
import com.webFlux.cosmos.resilience.model.Book;
import com.webFlux.cosmos.resilience.model.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@RestController
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/books/{id}/category/{category}", method = RequestMethod.GET)
    Mono<Book> bookByIDAndCategory(@PathVariable String category, @PathVariable String id) {
        return bookRepository
                .finByIdAndCategory(id, category)
                .retryWhen(requestTimeOutRetryConfig())
                .retryWhen(serviceUnavailableRetryConfig())
                .retryWhen(operationFailedRetryConfig())
                .doOnError(error -> logger.info("Error encountered ", error))
                .onErrorMap(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 404, error -> new NotFoundException())
                .onErrorMap(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() != 404, error -> new ServiceException());
    }

    @RequestMapping(value = "books/category/{category}", method = RequestMethod.GET)
    Flux<Book> bookByCategory(@PathVariable String category) {
        return bookRepository
                .findByCategory(category)
                .retryWhen(requestTimeOutRetryConfig())
                .retryWhen(serviceUnavailableRetryConfig())
                .retryWhen(operationFailedRetryConfig())
                .doOnError(error -> logger.info("Error encountered ", error))
                .onErrorMap(error -> new ServiceException());
    }

    @RequestMapping(value = "books/isbn/{isbn}", method = RequestMethod.GET)
    Flux<Book> bookByIsbn(@PathVariable String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .retryWhen(requestTimeOutRetryConfig())
                .retryWhen(serviceUnavailableRetryConfig())
                .retryWhen(operationFailedRetryConfig())
                .doOnError(error -> logger.info("Error encountered", error))
                .onErrorMap(error -> new ServiceException());
    }

    @RequestMapping(value = "books", method = RequestMethod.GET)
    Flux<Book> books() {
        return bookRepository
                .findAll()
                .retryWhen(requestTimeOutRetryConfig())
                .retryWhen(serviceUnavailableRetryConfig())
                .retryWhen(operationFailedRetryConfig())
                .doOnError(error -> logger.info("Error encountered", error))
                .onErrorMap(error -> new ServiceException());
    }


    @RequestMapping(value = "books", method = RequestMethod.POST)
    Mono<Book> createBook(@RequestBody Book book) {
        return bookRepository
                .create(book)
                .retryWhen(tooManyConcurrentWritesRetryConfig())
                .retryWhen(requestTimeOutRetryConfig())
                .retryWhen(serviceUnavailableRetryConfig())
                .retryWhen(operationFailedRetryConfig())
                .doOnError(error -> logger.info("Error encountered", error))
                .onErrorReturn(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 409, book)
                .onErrorMap(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() != 409, error -> new ServiceException());
    }


    private static Retry operationFailedRetryConfig() {
        return Retry
                .max(3)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 500)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    private static Retry serviceUnavailableRetryConfig() {
        return Retry
                .max(3)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 503)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    private static Retry requestTimeOutRetryConfig() {
        return Retry
                .max(3)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 408)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    private static Retry tooManyConcurrentWritesRetryConfig() {
        return Retry
                .max(3)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 449)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    private static Retry failFastRetryConfig() {
        return Retry
                .max(3)
                .filter(error -> error instanceof TimeoutException)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }


}
