package com.webFlux.cosmos.resilience.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason="some reason")
public class ServiceException extends RuntimeException {
}
