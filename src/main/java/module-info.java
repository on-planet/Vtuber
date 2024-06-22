module org.java_course.java_course_project_aivtuber {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires javafx.media;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires jlayer;
    requires org.jetbrains.annotations;
    requires okio;
    requires jdk.httpserver;
    requires javafx.swing;
    requires hanlp.portable;
    requires twitch4j;

    opens org.java_course.java_course_project_aivtuber to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber;
    exports org.java_course.java_course_project_aivtuber.Live2DViewerEX;
    opens org.java_course.java_course_project_aivtuber.Live2DViewerEX to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.OpenRouterAPI;
    opens org.java_course.java_course_project_aivtuber.OpenRouterAPI to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.Synchronous_callback;
    opens org.java_course.java_course_project_aivtuber.Synchronous_callback to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.GUI;
    opens org.java_course.java_course_project_aivtuber.GUI to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.GUI.Window;
    opens org.java_course.java_course_project_aivtuber.GUI.Window to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.GUI.Handler;
    opens org.java_course.java_course_project_aivtuber.GUI.Handler to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.GUI.Tool;
    opens org.java_course.java_course_project_aivtuber.GUI.Tool to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.Live2DViewerEX.API;
    opens org.java_course.java_course_project_aivtuber.Live2DViewerEX.API to javafx.fxml;
    exports org.java_course.java_course_project_aivtuber.GUI.Runnable;
    opens org.java_course.java_course_project_aivtuber.GUI.Runnable to javafx.fxml;
}