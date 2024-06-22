package org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.java_course.java_course_project_aivtuber.Application.textArea_output;

public class VoiceRequset {

    public static void writeResponseBodyToDisk(ResponseBody body, File outputFile) throws IOException {
        try (InputStream inputStream = body.byteStream();
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[100 * 1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // 刷新并确保所有数据都已写出
            outputStream.flush();
        }
    }

    public static void sendGetRequest(String url, File outputFile) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Connection successful");
                ResponseBody body = response.body();
                writeResponseBodyToDisk(body, outputFile);
            } else {
                System.out.println("Failed to send data. HTTP Status Code: " + response.code());
                textArea_output.appendText(">> Failed to send data. HTTP Status Code: " + response.code() + "\n");
                textArea_output.appendText(">> 确保你已经启动了GPTSoVIT!" + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
