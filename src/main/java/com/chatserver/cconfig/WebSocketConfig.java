package com.chatserver.cconfig;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// @Configuration 어노테이션은 이 클래스가 하나 이상의 Spring Bean을 선언하고 있으며,
// Spring IoC 컨테이너에 의해 관리된다는 것을 나타냅니다.
@EnableWebSocketMessageBroker
// @EnableWebSocketMessageBroker 어노테이션은 WebSocket 메시지 처리를 활성화하고,
// STOMP를 사용한 메시지 브로커 설정을 가능하게 합니다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 브로커 설정 메서드입니다. 메시지 라우팅을 위한 설정을 수행합니다.


        // setApplicationDestinationPrefixes는 애플리케이션에서 메시지를 처리할 때 사용할
        // 목적지 접두사를 설정합니다. 클라이언트가 "/app"으로 시작하는 목적지로 메시지를 보내면,
        // 메시지는 애플리케이션의 메시지 핸들러로 라우팅됩니다.
        registry.setApplicationDestinationPrefixes("/app");
        // enableSimpleBroker는 클라이언트로 메시지를 브로드캐스팅하기 위한 간단한 브로커를 활성화합니다.
        // "/topic"과 "/queue"로 시작하는 목적지로의 메시지를 브로커가 처리
        registry.enableSimpleBroker("/chatroom", "/user");

        registry.setUserDestinationPrefix("/user"); // 유저 목적지 접두사 설정


    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 엔드포인트를 등록하는 메서드입니다.

        // addEndpoint는 클라이언트가 WebSocket을 통해 연결할 수 있는 엔드포인트를 설정합니다.
        // "/ws-stomp" 경로가 WebSocket 연결을 수립할 수 있는 엔드포인트가 됩니다.
        registry.addEndpoint("/ws")

                // setAllowedOrigins("*")는 모든 도메인에서의 요청을 허용하도록 설정
                // 보안상 실제 애플리케이션에서는 특정 도메인만 허용하도록 설정 필요
                .setAllowedOrigins("http://localhost:3000")

                // withSockJS는 SockJS 폴리필을 사용하여 WebSocket을 지원하지 않는 브라우저에서도
                // WebSocket-like 통신을 할 수 있도록 합니다.
                .withSockJS();
    }
}

