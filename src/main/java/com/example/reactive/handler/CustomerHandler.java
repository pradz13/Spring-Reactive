package com.example.reactive.handler;

import com.example.reactive.dao.CustomerDao;
import com.example.reactive.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest) {
        Flux<Customer> customersList = customerDao.getCustomersList();
        return ServerResponse.ok().body(customersList, Customer.class);
    }

    public Mono<ServerResponse> loadCustomersStream(ServerRequest serverRequest) {
        Flux<Customer> customersList = customerDao.getCustomersStream();
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(customersList, Customer.class);
    }

    public Mono<ServerResponse> loadCustomerById(ServerRequest serverRequest) {
        int custId = Integer.parseInt(serverRequest.pathVariable("custId"));
        Mono<Customer> customerMono = customerDao.getCustomersList().filter(c -> c.getId() == custId).next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest) {
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> stringMono = customerMono.map(m -> m.getId() + ":" + m.getName());
        return ServerResponse.ok().body(stringMono, String.class);
    }
}
