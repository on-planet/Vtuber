package org.java_course.java_course_project_aivtuber.GUI.Tool;

import com.hankcs.hanlp.HanLP;

import java.io.IOException;
import java.util.List;

import static org.java_course.java_course_project_aivtuber.Application.*;
import static org.java_course.java_course_project_aivtuber.GUI.Handler.TextFieldHandler.dialogue;

public class AutomaticSummarization {

    public static int countNewLines(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return text.split("\n", -1).length - 1;
    }

    public static String run() throws IOException {
        TextClassification textClassification = new TextClassification(CORPUS_FOLDER_TEXTCLASSIFICATION, MODEL_PATH_EXTCLASSIFICATION);
        textClassification.trainOrLoadModel();
        int count = countNewLines(dialogue);
        if (dialogue != null && ! dialogue.isEmpty()) {
            List<String> phraseList = HanLP.extractPhrase(dialogue, count);
            String classify = textClassification.predict(String.valueOf(phraseList));
            System.out.println("Classify: " + classify);
            textArea_output.appendText(">> 你今天似乎对" + classify + "这一领域的话题特别感兴趣!" + "\n");
            return classify;
        }
        return "";
    }

}
