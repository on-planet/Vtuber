package org.java_course.java_course_project_aivtuber.Live2DViewerEX.API;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

public class EchoWebSocketListener extends WebSocketListener {
    @Override
    public void onOpen(WebSocket webSocket, @NotNull Response response) {
        // 在WebSocket连接打开时触发
        // 打印HTTP状态码
        System.out.println("Connected with HTTP status: " + response.code());
        // 打印所有的响应头
        System.out.println("Response headers: " + response.headers());
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        // 收到的文本消息将触发此回调
        System.out.println("接收到消息: " + text);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        // 收到的二进制消息将触发此回调
        System.out.println("接收到二进制消息: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, @NotNull String reason) {
        // 连接即将关闭时触发
        webSocket.close(1000, null);
        System.out.println("关闭中: " + code + " / " + reason);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        // 连接已经关闭时触发
        System.out.println("已关闭: " + code + " / " + reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, Response response) {
        // 连接失败时触发
        t.printStackTrace();
    }
}