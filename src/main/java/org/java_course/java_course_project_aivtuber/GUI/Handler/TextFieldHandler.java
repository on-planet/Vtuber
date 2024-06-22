package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.java_course.java_course_project_aivtuber.GUI.Tool.DynamicAudioPlayer;
import org.java_course.java_course_project_aivtuber.GUI.Tool.Port;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.API.WebSocketClient;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_WebSocket;
import org.java_course.java_course_project_aivtuber.Synchronous_callback.Worker;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static org.java_course.java_course_project_aivtuber.Application.*;
import static org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.MyThread_GPTSoVITS_Inference.GPTSoVITSTask;
import static org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.VoiceRequset.sendGetRequest;
import static org.java_course.java_course_project_aivtuber.GUI.Tool.Filer.createFileSeq;
import static org.java_course.java_course_project_aivtuber.GUI.Tool.Port.findPID;
import static org.java_course.java_course_project_aivtuber.GUI.Tool.Port.killProcess;

public class TextFieldHandler implements EventHandler<ActionEvent> {
    private Socket echoSocket;
    public TextFieldHandler(Socket echoSocket) {
        this.echoSocket = echoSocket;
    }
    public static String dialogue = "";

    // 处理服务器响应
    private String handleServerResponse(BufferedReader in) throws IOException, InterruptedException {
        // 创建 SettingsManager 并加载当前设置
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();

        File outputFile;
        String response;
        try {
            //同步阻塞任务，读取服务器响应
            response = in.readLine();
            String encoded = URLEncoder.encode(response, StandardCharsets.UTF_8);
            outputFile = createFileSeq("src\\main\\resources\\voice_data\\", "voice", ".wmv");
            //get方法   tts:任务类型   character：角色   text：文本内容  speed：语音速度  text_language:合成语言类型   （中 英 日 中英 英日 多语种混合 中日）
            String urlStr = STR."http://127.0.0.1:5000/tts?character=\{currentSettings.getSoundType()}&text=\{encoded}&speed=0.9&text_language=\{currentSettings.getLanguageModule()}";
            sendGetRequest(urlStr, outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //同步回调方法
        Worker worker = new Worker();
        File file = worker.executeWork(outputFile, System.out::println);
        //文本框输出文字
        textArea_output.appendText(">> " + response + "\n");
        WebSocketClient.main(new String[] {response});
        //播放wmv文件
        DynamicAudioPlayer.addToAudioQueue(file);
        DynamicAudioPlayer.processAudioQueue();
        return response;
    }

    // 异步监听用户输入
    private void listenForUserInput(BufferedReader stdIn, PrintWriter out) {
        String userInput;
        try {
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // 发送用户输入的数据到服务器
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        private void ConnectToWebSocket(String response, Boolean close) throws Exception {
        try (
                Socket echoSocket = new Socket("127.0.0.1", 9090);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true) // 输出流，用于向服务器发送数据
        ){
            if(close){
                out.println("close");
            }
            out.println(response);
        }catch (IOException exception){
            exception.getCause();
        }finally {
            String pid = findPID(9090);
            if (pid != null) {
                //  Kill the process with the found PID
                killProcess(pid);
            } else {
                System.out.println("No process is using port 9090.");
            }
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (echoSocket == null || echoSocket.isClosed()) {
            try {
                echoSocket = new Socket("127.0.0.1", 8000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Task<Void> task = new Task<>() {
            protected Void call() {
                String text = textField_input.getText(); // 获取TextField中的文本内容
                dialogue = dialogue + text + "\n";
                System.out.println(dialogue);
                textArea_output.appendText(">> " + text + "\n");
                textField_input.clear();
                if(!GPTSoVITSTask){
                    Port.isPortInUse(5000);
                    Port.isPortInUse(10086);
                    if(!GPTSoVITSTask){
                        textArea_output.appendText(">> " + "GPTSoVITS还未加载" + "\n");
                        return null;
                    }
                    textArea_output.appendText(">> " + "GPTSoVITS加载完毕" + "\n");
                }
                byte[] bytes = text.getBytes();  // 将文本内容转换为字节数组
                InputStream inputStream = new ByteArrayInputStream(bytes);  // 创建一个ByteArrayInputStream，将字节数组作为输入流
                try (
                        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); // 输出流，用于向服务器发送数据
                        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); // 输入流，用于接收服务器发回的数据
                        BufferedReader stdIn = new BufferedReader(new InputStreamReader(inputStream)) // 标准输入流，用于读取用户的输入
                ) {
                    // 异步处理用户输入的任务
                    CompletableFuture.runAsync(() -> {
                        listenForUserInput(stdIn, out);
                    });
                    String response = handleServerResponse(in);
                    try{
                        ConnectToWebSocket(response, close);
                    }catch (IOException e){
                        e.getCause();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to connect to 127.0.0.1 on port 8000");
                    System.out.println(e.getMessage());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> System.out.println("运行成功"));
                return null;
            }
        };
        new Thread(task).start(); //创建线程
    }
}


