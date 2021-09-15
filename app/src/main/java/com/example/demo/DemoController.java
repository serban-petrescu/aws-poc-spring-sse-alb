package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RestController
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/sse")
    public SseEmitter streamSseMvc(Principal principal) {
        final String name = principal.getName();
        final SseEmitter emitter = new SseEmitter();
        Consumer<String> consumer = new MessageConsumer(emitter);
        demoService.register(name, consumer);
        emitter.onCompletion(() -> demoService.unregister(name, consumer));
        emitter.onTimeout(() -> demoService.unregister(name, consumer));
        return emitter;
    }

    private static class MessageConsumer implements Consumer<String> {
        private final SseEmitter emitter;

        private MessageConsumer(SseEmitter emitter) {
            this.emitter = emitter;
        }

        @Override
        public void accept(String message) {
            try {
                emitter.send(SseEmitter.event()
                        .data(message)
                        .id(message.hashCode() + "")
                        .name("demo"));
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }
    }
}
