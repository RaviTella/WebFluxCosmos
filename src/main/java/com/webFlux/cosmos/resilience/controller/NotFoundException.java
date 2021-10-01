package com.webFlux.cosmos.resilience.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="some reason")
public class NotFoundException extends RuntimeException {
}
