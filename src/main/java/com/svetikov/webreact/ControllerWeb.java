package com.svetikov.webreact;

import com.svetikov.webreact.model.Events;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;


import static org.springframework.http.MediaType.ALL;
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
public class ControllerWeb {

    List<String> program = Arrays.asList("test", " spring", " java", " react js", " angular", " react nativ");
    List<String> program_t = new ArrayList<>();
private Events events;
    public ControllerWeb() {
        events=new Events();
    }

    @GetMapping("/webmono")
    public Mono<String> web() {
        return Mono.just("web test");
    }

    @GetMapping("/webadd/{prog}")
    public String webadd(@PathVariable(value = "prog") String prog) {
        program_t.add(prog);
        return prog + " success";
    }

    @GetMapping(value = "/webflux")
    public Flux<String> webFlux() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<String> range = Flux.fromIterable(program_t).map(val -> "<div style='padding:10px; " +
                "color:white;" +
                " display:flex;" +
                " margin:10px;" +
                "background:green;'>"
                + val +
                "</div></br>");

        return Flux.zip(range, interval).map(Tuple2::getT1);
    }

    @GetMapping("/webflux_1")
    private Flux<String> webFlux_1() {
        return Flux.fromIterable(program_t)
                .map(val -> val + "</br>")
                .delayElements(Duration.ofMillis(1000));
    }


    @GetMapping("/events/{id}/{name}")
    public Mono<Events> eventsAdd(@PathVariable(value = "id")int id,@PathVariable(value = "name")String name){
      this.events = new Events(id, name, new Date());
        return Mono.just(events);
    }

    @GetMapping(value = "/events",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Events> eventsFlux(){
        Flux<Events> eventsFlux = Flux.fromStream(Stream.generate(()->
               this.events
                ));
        Flux<Long> duration=Flux.interval(Duration.ofMillis(5000));
        return Flux.zip(eventsFlux,duration).map(Tuple2::getT1);
    }

    @GetMapping("/events1")
    public ResponseEntity<Events> eventsRequestEntity(){
        log.info("test1");
        return ResponseEntity.ok(new Events(1,"test1",new Date()));
    }




}
