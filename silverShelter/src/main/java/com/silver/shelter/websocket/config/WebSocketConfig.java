package com.silver.shelter.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.silver.shelter.websocket.handler.ChattingWebsocketHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer{

   private final HandshakeInterceptor handshakeInterceptor;
   
   private final ChattingWebsocketHandler chattingWebsocketHandler;
   
   @Override
   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      
      
      registry.addHandler(chattingWebsocketHandler, "/chattingSock")
      .addInterceptors(handshakeInterceptor)
      .setAllowedOriginPatterns("http://localhost/","http://127.0.0.1/","http://192.168.50.236/"
    		  					,"https://goldenprestige.store/","http://goldenprestige.store/",
    		  					"http://13.125.120.147","https://13.125.120.147")
      .withSockJS();
   }
   
}
