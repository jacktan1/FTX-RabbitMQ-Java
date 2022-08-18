package com.jacktan.ftxrabbitmqjava;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class Publisher {
    public static final List<String> tickerList = Arrays.asList("BTC-PERP", "ETH-PERP", "AVAX-PERP", "BNB-PERP",
            "FTT-PERP", "SOL-PERP", "ADA-PERP", "ATOM-PERP", "LINK-PERP", "MATIC-PERP", "XMR-PERP", "FTM-PERP",
            "NEAR-PERP", "SAND-PERP", "DOT-PERP", "OP-PERP", "AXS-PERP", "APE-PERP", "WAVES-PERP", "CEL-PERP",
            "CRV-PERP", "GRT-PERP", "AAVE-PERP", "SNX-PERP");

    public static final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public static final String QUEUE = "ftx_queue_java";

    public static final String EXCHANGE = "ftx_exchange_java";

    public static final String ROUTING_KEY = "ftx";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Autowired
    private RabbitTemplate template;

    @Scheduled(fixedDelay = 5000)
    public void send() {
        for (String ticker : tickerList) {
            String ticker_uri = "https://ftx.com/api/markets/" + ticker;
            ResponseEntity<String> response = restTemplate.getForEntity(ticker_uri, String.class);
            if (response.getStatusCodeValue() == 200) {
                String resp_body = response.getBody();
                assert resp_body != null;
                this.template.convertAndSend(EXCHANGE, ROUTING_KEY, resp_body);
                System.out.println("Sent '" + resp_body + "'");
            }
        }
        System.out.println("Finished run! Sleeping for " + 5 + " seconds...");
    }
}
