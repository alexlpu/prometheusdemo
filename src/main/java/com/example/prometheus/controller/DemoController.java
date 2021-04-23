package com.example.prometheus.controller;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    static final Counter totalRequests = Counter.build()
            .name("total_requests").help("Total requests.").register();
    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests").help("Inprogress requests.").register();

    static final Histogram requestLatency = Histogram.build()
            .name("requests_latency_seconds").help("Request latency in seconds.").register();

    @GetMapping(path="/testCall", produces="application/json")
    public String testCall() {
        inprogressRequests.inc();
        totalRequests.inc();
        Histogram.Timer requestTimer = requestLatency.startTimer();

        String response = "{\"test1\":\"1000\", \"test2\":2000}";
        try {
            int pause = Math.toIntExact(Math.round(Math.random() * 10000));
            Thread.sleep(pause);
            response = "{\"pause\":\"" + pause + "\", \"status\":\"OK\"}";
        } catch (InterruptedException e) {
            e.printStackTrace();
            response = "{\"error\":\"true\"}";
        }
        inprogressRequests.dec();
        requestTimer.observeDuration();
        return response;
    }

}
