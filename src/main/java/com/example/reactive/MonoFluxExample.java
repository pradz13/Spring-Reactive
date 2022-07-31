package com.example.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxExample {

    @Test
    public void monoTest() {
        Mono<String> monoString = Mono.just("Pradipta").log();
        monoString.subscribe(System.out::println);
    }

    @Test
    public void monoTestWithException() {
        Mono<Object> monoRef = Mono.just("Pradipta").then(Mono.error(new RuntimeException("Exception"))).log();
        monoRef.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void fluxTest() {
        Flux<String> fluxString = Flux.just("Java", "C++", "Python").concatWithValues("AWS").log();
        fluxString.subscribe(System.out::println);
    }

    @Test
    public void fluxTestWithException() {
        Flux<String> fluxString = Flux.just("Java", "C++", "Python").concatWithValues("AWS").concatWith(Flux.error(new RuntimeException("Exception"))).log();
        fluxString.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }
}
