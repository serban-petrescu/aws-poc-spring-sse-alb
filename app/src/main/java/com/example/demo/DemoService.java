package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class DemoService {
    private final Map<String, Set<Consumer<String>>> emitters = new ConcurrentHashMap<>();

    public void register(String user, Consumer<String> emitter) {
        emitters.merge(user, single(emitter), (a, b) -> {
            a.addAll(b);
            return a;
        });
    }

    public void unregister(String user, Consumer<String> emitter) {
        emitters.getOrDefault(user, new HashSet<>()).remove(emitter);
    }

    public void publish(String user, String message) {
        emitters.getOrDefault(user, Collections.emptySet()).forEach(c -> c.accept(message));
    }

    private static Set<Consumer<String>> single(Consumer<String> emitter) {
        Set<Consumer<String>> set = Collections.newSetFromMap(new ConcurrentHashMap<>());
        set.add(emitter);
        return set;
    }
}
