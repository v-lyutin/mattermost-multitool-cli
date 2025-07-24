package com.amit.multitool.service;

import java.util.Optional;
import java.util.function.Supplier;

public interface RequestExecutorService {

    <T> Optional<T> executeRequest(Supplier<T> apiCall);

}
