package com.webFlux.cosmos.resilience.util;

import com.azure.cosmos.CosmosException;
import org.slf4j.Logger;
import reactor.util.retry.Retry;

import java.util.concurrent.TimeoutException;

public class RetryConfig {

    public static Retry operationFailedRetryConfig(Logger logger,long maxRetryAttempts) {
        return Retry
                .max(maxRetryAttempts)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 500)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    public static Retry serviceUnavailableRetryConfig(Logger logger,long maxRetryAttempts) {
        return Retry
                .max(maxRetryAttempts)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 503)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    public static Retry requestTimeOutRetryConfig(Logger logger,long maxRetryAttempts) {
        return Retry
                .max(maxRetryAttempts)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 408)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    public static Retry tooManyConcurrentWritesRetryConfig(Logger logger,long maxRetryAttempts) {
        return Retry
                .max(maxRetryAttempts)
                .filter(error -> error instanceof CosmosException && ((CosmosException) error).getStatusCode() == 449)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }

    public static Retry failFastRetryConfig(Logger logger,long maxRetryAttempts) {
        return Retry
                .max(maxRetryAttempts)
                .filter(error -> error instanceof TimeoutException)
                .doBeforeRetry(retrySignal -> logger.info("{} re-try attempt after the error {} ", retrySignal.totalRetries() + 1, retrySignal
                        .failure()
                        .toString()));
    }
}
