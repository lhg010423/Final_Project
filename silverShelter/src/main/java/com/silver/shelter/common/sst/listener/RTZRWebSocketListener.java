package com.silver.shelter.common.sst.listener;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class RTZRWebSocketListener implements WebSocket.Listener {

    // WebSocket이 닫힐 때까지 기다리기 위해 사용되는 CountDownLatch
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void onOpen(WebSocket webSocket) {
        // WebSocket 연결이 열릴 때 호출됩니다.
        // 다음 메시지를 요청합니다.
        webSocket.request(1);
    }
    
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        // 텍스트 메시지를 수신할 때 호출됩니다.
        // 다음 메시지를 요청합니다.
        webSocket.request(1);
        return null;
    }
    
    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        // 바이너리 메시지를 수신할 때 호출됩니다.
        // 다음 메시지를 요청합니다.
        webSocket.request(1);
        return null;
    }
    
    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        // WebSocket 연결이 닫힐 때 호출됩니다.
        // CountDownLatch의 카운트를 감소시켜 대기 중인 스레드를 깨웁니다.
        latch.countDown();
        return Listener.super.onClose(webSocket, statusCode, reason);
    }
    
    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        // WebSocket에 오류가 발생했을 때 호출됩니다.
        // 오류 스택 트레이스를 출력하고, CountDownLatch의 카운트를 감소시켜 대기 중인 스레드를 깨웁니다.
        error.printStackTrace();
        latch.countDown();
    }

    // WebSocket이 닫힐 때까지 대기합니다.
    public void waitClose() throws InterruptedException {
        latch.await();
    }
}