package com.systems.finance.service;

import com.systems.finance.model.User;
import com.systems.finance.repository.UserRepository;
import org.dataloader.BatchLoaderEnvironment;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserDataLoader {

    private final UserRepository userRepository;

    public UserDataLoader(UserRepository userRepository, BatchLoaderRegistry registry) {
        this.userRepository = userRepository;
        registry.forName("userLoader").registerBatchLoader(this::loadUsers);
    }

    private Flux<Object> loadUsers(List<Object> userIds, BatchLoaderEnvironment env) {
        List<Long> ids = userIds.stream()
                .map(id -> (Long) id)
                .collect(Collectors.toList());
        return Flux.defer(() -> {
            List<User> users = userRepository.findAllById(ids);
            Map<Long, User> userMap = users.stream()
                    .collect(Collectors.toMap(User::getId, user -> user));
            return Flux.fromIterable(ids)
                    .map(userMap::get)
                    .map(user -> (Object) user);
        });
    }
}
