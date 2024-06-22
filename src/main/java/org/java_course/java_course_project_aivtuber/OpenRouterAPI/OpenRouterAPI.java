package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;

import static org.java_course.java_course_project_aivtuber.GUI.Tool.RandomTimerMessage.*;

public class OpenRouterAPI {
    //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
    private static final String OPENROUTER_API_KEY = "sk-or-v1" + "-8651b12754d9bf70c4b67f882817a0c8e423cb3e410ab4a9cb76a8383f2987e2";
    public static int port = 8000;
    ServerSocket serverSocket;

    public static void main(String[] args) throws SocketException {
        while (true) {
            Timer timer = new Timer();
            scheduleNext(timer);
            OkHttpClient client = new OkHttpClient();
            SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
            Settings currentSettings = settingsManager.loadSettings();
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setReuseAddress(true);
                try (
                        ServerSocket resourceServerSocket = serverSocket; // 创建服务器套接字
                        Socket clientSocket = resourceServerSocket.accept(); // 等待客户端连接
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // 输出流，用于向客户端发送数据
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 输入流，用于接收客户端发送的数据
                ) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) { // 循环读取客户端发送的数据
//                        hasNewInput = true;
//                        // 使用用户输入的内容或者随机生成的文字替换inputLine
//                        String messageContent = randomMessage.isEmpty() ? inputLine : randomMessage;
//                        randomMessage = ""; // 清空随机消息，确保下次可以重新生成
                        String init = STR."{\"model\": \"\{currentSettings.getAiModelType()}\", \"messages\": [{\"role\": \"system\", \"content\": \"\{currentSettings.getSystemprompt()}\"},{\"role\": \"user\", \"content\": \"\{inputLine}\"}]}";
                        RequestBody body = RequestBody.create(init, MediaType.get("application/json; charset=utf-8"));
                        Request request = new Request.Builder()
                                .url("https://openrouter.ai/api/v1/chat/completions")
                                .addHeader("Authorization", String.format("Bearer %s", OPENROUTER_API_KEY))
                                .post(body)
                                .build();
                        try (Response response = client.newCall(request).execute()) {
                            if (! response.isSuccessful()) throw new IOException("Unexpected code " + response);
                            ObjectMapper mapper = new ObjectMapper();
                            AIModel AIModel = mapper.readValue(response.body().string(), AIModel.class);
                            System.out.println(AIModel.getChoices().getFirst());
                            String text = AIModel.getChoices().getFirst().toString().replaceAll("\n", "");
                            //System.out.println(mistral7B.getChoices().get(1));
                            if (text.startsWith("assistant")) {
                                text = text.replace("assistant ", "");
                            }
                            out.println(text); // 将接收到的数据回发给客户端
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " + port + " or listening for " +
                            "a connection");
                    System.out.println(e.getMessage());
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            } finally {
                if (serverSocket != null && ! serverSocket.isClosed()) {
                    try {
                        serverSocket.close(); // 在 finally 中确保 serverSocket 被关闭
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
