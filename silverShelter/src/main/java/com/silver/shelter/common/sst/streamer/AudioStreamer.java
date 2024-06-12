package com.silver.shelter.common.sst.streamer;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AudioStreamer {

	
    public static void streamAudio(WebSocket webSocket, String filePath) throws Exception {
    	
        AudioInputStream in;
        
        try {
        	// 주어진 파일 경로를 통해 파일 객체를 생성 
            File file = new File(filePath);
            
            // AudioSystem을 사용하여 파일에서 AudioInputStream을 얻습니다.
            in = AudioSystem.getAudioInputStream(file);
            
        } catch (FileNotFoundException e) {
        	
        	// 파일이 존재하지 않으면 예외가 발생하며, 스택 트레이스를 출력하고 메서드를 종료합니다.
            e.printStackTrace();
            
            return;
        }
        // 버퍼를 1024바이트 크기로 생성합니다.
        byte[] buffer = new byte[1024];
        int readBytes;
        
        // 오디오 데이터를 읽어서 버퍼에 저장합니다.
        while ((readBytes = in.read(buffer)) != -1) {
        	
        	// 읽은 데이터를 WebSocket을 통해 바이너리 형식으로 보냅니다.
            WebSocket sent = webSocket.sendBinary(ByteBuffer.wrap(buffer, 0, readBytes), true).join();
            if (sent != null) {
            	// WebSocket이 데이터를 보내지 못하면 경고 로그를 남기고 메서드를 종료합니다.
            	log.warn("Send buffer is full. Cannot complete request. Increase sleep interval.");
                return;
            }
            // 전송 사이에 잠깐의 지연을 줍니다.
            Thread.sleep(0, 50);
        }
        // 오디오 입력 스트림을 닫습니다.
        in.close();
        // WebSocket을 통해 "EOS" (End Of Stream) 메시지를 전송합니다.
        webSocket.sendText("EOS", true).join();
    }
}
