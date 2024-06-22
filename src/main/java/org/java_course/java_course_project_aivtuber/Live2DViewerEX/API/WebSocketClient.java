package org.java_course.java_course_project_aivtuber.Live2DViewerEX.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.java_course.java_course_project_aivtuber.GUI.Tool.TextClassification;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.Data;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.Live2DModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.java_course.java_course_project_aivtuber.Application.CORPUS_FOLDER_EMOTION;
import static org.java_course.java_course_project_aivtuber.Application.MODEL_PATH_EMOTION;

public class WebSocketClient {

    // 创建 SettingsManager 并加载当前设置
    private static final SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
    private static final Settings currentSettings = settingsManager.loadSettings();
    private static final int port = 9090;
    private static final String file = currentSettings.getLive2DModelPath();
    private static final ArrayList<String> choices = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static String json;
    private static WebSocket webSocket;

    public WebSocketClient() {
    }

    //监听模型
    private static void listenModel(Live2DModel live2DModel, WebSocket ws) throws JsonProcessingException {
        live2DModel.setMsg(10000);
        String json = mapper.writeValueAsString(live2DModel);
        ws.send(json);
    }

    //移除模型
    private static void removeModel(int msgID, int id, WebSocket ws) {
        String message = STR."{\"msg\": 13100,\"msgID\": \{msgID}, \"data\": \{id}}";
        ws.send(message);
    }

    //设置模型json
    private static void setModel(Live2DModel live2DModel, String location, WebSocket ws) throws JsonProcessingException {
        live2DModel.setMsg(13000);
        live2DModel.getData().setFile(location);
        json = mapper.writeValueAsString(live2DModel);
        ws.send(json);
    }

    //显示文字气泡
    private static void showMessage(String message, Live2DModel live2DModel, WebSocket ws) throws IOException {
        int Duration = (int) (message.length() * 3.33 * 0.9 * 100);
        System.out.println(Duration);
        live2DModel.setMsg(11000);
        live2DModel.getData().setText(message);
        live2DModel.getData().setDuration(Duration);
        //setEmotion(live2DModel);
        json = mapper.writeValueAsString(live2DModel);
        System.out.println(json);
        ws.send(json);
    }

    private static String checkPostive(String message) throws IOException {
        TextClassification textClassification = new TextClassification(CORPUS_FOLDER_EMOTION, MODEL_PATH_EMOTION);
        textClassification.trainOrLoadModel();
        return textClassification.predict(message);
    }

    private static void setEmotion(Live2DModel live2DModel) throws IOException {
        String Score = checkPostive(live2DModel.getData().getText());
        live2DModel.setMsg(13300);
        if (Objects.equals(Score, "正面")) {
            live2DModel.getData().setExpId(0);
        } else {
            live2DModel.getData().setExpId(1);
        }
    }

    private static void removeEmtion(Live2DModel live2DModel, WebSocket ws) throws IOException {
        live2DModel.setMsg(13302);
        json = mapper.writeValueAsString(live2DModel);
        ws.send(json);
    }

    public static void main(String[] args) throws IOException {
        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("ws://127.0.0.1:10086/api")
                .build();

        // 建立WebSocket监听
        EchoWebSocketListener listener = new EchoWebSocketListener();

        // 创建WebSocket
        WebSocket ws = client.newWebSocket(request, listener);

        System.out.println("ws: " + args[0]);
        Live2DModel live2DModel = new Live2DModel(10000, 0);
        Data data = new Data(0, file);
        live2DModel.setData(data);

        listenModel(live2DModel, ws);
        //removeModel(0, 0, ws);
        //setModel(live2DModel, data, file, ws);
        showMessage(args[0], live2DModel, ws);
        //moveEmtion(live2DModel, ws);
        ws.close(1000, "Server closed");
    }
}




