package org.java_course.java_course_project_aivtuber;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.java_course.java_course_project_aivtuber.GUI.Handler.*;
import org.java_course.java_course_project_aivtuber.GUI.Runnable.OpenMusicFileRunnable;
import org.java_course.java_course_project_aivtuber.GUI.Runnable.OpenMusicFolderRunnable;
import org.java_course.java_course_project_aivtuber.GUI.Runnable.PlayNextRunnable;
import org.java_course.java_course_project_aivtuber.GUI.Tool.Port;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;
import org.java_course.java_course_project_aivtuber.GUI.openNewWindow;
import org.java_course.java_course_project_aivtuber.OpenRouterAPI.MyThread_APIConnection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.MyThread_GPTSoVITS_Inference.GPTSoVITSProcess;
import static org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.MyThread_GPTSoVITS_Inference.GPTSoVITSTask;
import static org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_StatrLive2DViewerEX.Live2DViewerEX;
import static org.java_course.java_course_project_aivtuber.Live2DViewerEX.StartLive2DViewerEX.Live2DViewerEXProcess;

public class Application extends javafx.application.Application {
    private Socket echoSocket;

    private final BorderPane pane = new BorderPane();
    private final Scene scene = new Scene(pane);      //初始化
    private final Label time = new Label();        //设置标签

    private final static Image image_left_jump = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon/left_jump.png")));
    private final static Image background_image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon" +
            "/wallhaven-2882wm.jpg")));
    private final static Image image_right_jump = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon/right_jump" +
            ".png")));
    private final static File file = new File("src/main/resources/music/init.mp3");  //初始化
    private final static Media media = new Media(file.toURI().toString());  //流媒体内容转换  初始化
    private final static MediaPlayer mediaPlayer = new MediaPlayer(media);  //播放窗口
    private final static Image image_play = new Image(Objects.requireNonNull(Application.class.getResourceAsStream(
            "/icon/Play.png")));
    private final static Image image_stop = new Image(Objects.requireNonNull(Application.class.getResourceAsStream(
            "/icon/pause.png")));
    private final static Image music_image = new Image((Objects.requireNonNull(Application.class.getResourceAsStream(
            "/icon/null.png"))));

    public static boolean flag = false;
    public static Double current_VideoTime = (double) 0; //现在的视频时间 初始化
    public static Double endTime = (double) 0;  //结束的音视频时间 初始化
    public static Slider slTime = new Slider();  //时间线
    public static int currentPlayerIndex = 0;  //全局播放列表序列
    public static boolean close = false;

    public static final String CORPUS_FOLDER_EMOTION = String.valueOf(Application.class.getResourceAsStream("\\data\\model\\crf\\ChnSentiCorp情感分析酒店评论"));
    public static final String CORPUS_FOLDER_TEXTCLASSIFICATION = String.valueOf(Application.class.getResourceAsStream("\\data\\model\\crf\\复旦大学谭松波中文文本分类"));
    public static final String MODEL_PATH_EMOTION = String.valueOf(Application.class.getResourceAsStream("\\data\\test\\classification_emotion-model.ser"));
    public static final String MODEL_PATH_EXTCLASSIFICATION = String.valueOf(Application.class.getResourceAsStream("\\data\\test\\classification-model.ser"));

    public final static Image icon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon/icon.png")));
    public final static ArrayList<File> files = new ArrayList<>();
    public final static ObservableList<MediaPlayer> players = FXCollections.observableArrayList(mediaPlayer);
    public final static Image file_image = new Image((Objects.requireNonNull(Application.class.getResourceAsStream(
            "/icon/file.png"))));
    public final static ContextMenu contextMenu = new ContextMenu();
    public final static ObservableList<File> fileList = FXCollections.observableArrayList();
    public final static ListView<File> listView = new ListView<>();
    public final static List<String> SUPPORTED_FILE_EXTENSIONS = Arrays.asList(".mp3", ".m4a");
    public final static Label lbCurrentTime = new Label();
    public final static Button btn_rightJump = new Button();
    public final static Button btn_leftJump = new Button();
    public final static Label artist = new Label();
    public final static Label music_name = new Label();
    public final static Label album = new Label();
    public final static ImageView imageView_music_image = new ImageView(music_image);
    public final static ImageView imageView_stop = new ImageView(image_stop);
    public final static ImageView imageView_play = new ImageView(image_play);
    public final static Button btnPlay = new Button();
    public final static TextField textField_input = new TextField("");
    public static TextArea textArea_output = new TextArea();
    public static TextArea textArea_BliveMessage = new TextArea();
    public final static Image image_btn_sound =
            new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon/sound.png")));
    public final static ImageView imageView_btn_sound = new ImageView(image_btn_sound);
    public final static Image image_btn_nosound =
            new Image(Objects.requireNonNull(Application.class.getResourceAsStream("/icon/mute.png")));
    public final static ImageView imageView_btn_nosound = new ImageView(image_btn_nosound);
    public final static Button btn_sound = new Button();
    public final static Slider slVolume = new Slider(); // 音量初始化
    public static String blivefile = new String("E:\\code\\Java\\Java_course_project_AI-Vtuber\\src\\main\\resources\\text_data\\blive.txt");

    public static void main(String[] args) throws IOException {
        try (PrintWriter writer = new PrintWriter(blivefile)) {
            writer.print("");
            System.out.println("文件内容已清空");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "E:\\code\\Java\\Java_course_project_AI-Vtuber\\blivedm\\start.bat");
        processBuilder.start();
        MyThread_APIConnection myThreadApiConnection = new MyThread_APIConnection();
        myThreadApiConnection.start();
        launch();
    }  //启动

    @Override
    public void start(Stage stage) {

        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();

        double Width = 1000;  //默认长度
        double Height = 650;  //默认高度

        // 创建 SettingsManager 并加载当前设置

        //上侧框架
        // 创建 “文件”菜单
        Menu firstMenu = new Menu("文件");
        MenuItem openMenuItem = new MenuItem("打开Vtuber模型");
        MenuItem saveMenuItem = new MenuItem("保存对话");
        MenuItem setting = new Menu("设置");

        firstMenu.getItems().addAll(openMenuItem, saveMenuItem, setting);

        // 绑定菜单栏
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(firstMenu);
        menuBar.getStyleClass().add("menuBar");

        //右侧框架
        //音乐播放器界面
        VBox box_right = new VBox();
        box_right.setPrefHeight(400);
        box_right.setPrefWidth(750);

        HBox hBox_right_in = new HBox();
        hBox_right_in.setPrefWidth(750);
        hBox_right_in.setPrefHeight(300);

        //右侧下方矩形
        Rectangle rectangle_2 = new Rectangle(750, 250, Color.valueOf("#DDE6ED"));
        rectangle_2.setOpacity(0.88);

        //创建时间标签
        lbCurrentTime.setText("00:00:00 / 00:00:00");
        lbCurrentTime.setPrefWidth(200);
        lbCurrentTime.setTranslateX(255);
        lbCurrentTime.setTranslateY(208);

        //创建时间轴
        slTime.getStyleClass().add("custom-slTime");
        // 绑定样式事件
        slTime.setPrefWidth(420);
        slTime.setPrefHeight(100);
        slTime.setTranslateX(100);
        slTime.setTranslateY(150);

        //右跳过键设置
        btn_rightJump.setStyle("-fx-background-color: null");
        ImageView imageView_btn_rightJump = new ImageView(image_right_jump);
        btn_rightJump.setGraphic(imageView_btn_rightJump);
        imageView_btn_rightJump.setFitHeight(20);
        imageView_btn_rightJump.setFitWidth(35);
        btn_rightJump.setTranslateX(525);
        btn_rightJump.setTranslateY(185);

        //左跳过键设置
        btn_leftJump.setStyle("-fx-background-color: null");
        ImageView imageView_btn_leftJump = new ImageView(image_left_jump);
        btn_leftJump.setGraphic(imageView_btn_leftJump);
        imageView_btn_leftJump.setFitHeight(20);
        imageView_btn_leftJump.setFitWidth(35);
        btn_leftJump.setTranslateX(25);
        btn_leftJump.setTranslateY(185);

        //播放键设置
        btnPlay.setStyle("-fx-background-color: null");
        imageView_play.setFitWidth(15);
        imageView_play.setFitHeight(20);
        imageView_stop.setFitWidth(15);
        imageView_stop.setFitHeight(20);
        btnPlay.setGraphic(imageView_play);
        btnPlay.setTranslateX(75);
        btnPlay.setTranslateY(185);

        //创建音量键
        slVolume.getStyleClass().add("custom-slVolume");
        slVolume.setPrefWidth(120);
        slVolume.setValue(50);
        slVolume.setShowTickLabels(false);  //不显示竖线
        slVolume.setShowTickMarks(false);  //不显示数字标记
        slVolume.setTranslateX(610);
        slVolume.setTranslateY(192);

        //音量键样式
        VolumeSliderStyler.applyStyle();

        //创建音量图标
        btn_sound.setStyle("-fx-background-color: null");
        btn_sound.setGraphic(imageView_btn_sound);
        imageView_btn_sound.setFitHeight(15);
        imageView_btn_sound.setFitWidth(18);
        imageView_btn_nosound.setFitHeight(15);
        imageView_btn_nosound.setFitWidth(18);
        btn_sound.setTranslateX(575);
        btn_sound.setTranslateY(188);

        //作者名称标签
        artist.setPrefHeight(20);
        artist.setTranslateX(300);
        artist.setTranslateY(90);
        artist.setPrefWidth(300);

        //音乐专辑标签
        album.setPrefWidth(300);
        album.setPrefHeight(20);
        album.setTranslateX(300);
        album.setTranslateY(120);

        //音乐名称标签
        music_name.setPrefWidth(300);
        music_name.setPrefHeight(20);
        music_name.setTranslateX(300);
        music_name.setTranslateY(60);

        //音乐专辑封面
        imageView_music_image.setFitWidth(150);
        imageView_music_image.setFitHeight(150);
        imageView_music_image.setTranslateX(120);
        imageView_music_image.setTranslateY(23);

        //播放判定动作
        Group Play = new Group();
        Play.getChildren().addAll(rectangle_2, btnPlay, slTime, lbCurrentTime, slVolume, album,
                btn_rightJump, btn_leftJump, btn_sound, artist, music_name, imageView_music_image);
        hBox_right_in.getChildren().addAll(Play);

        //输出窗口
        textArea_output.setOpacity(0.66);  //透明度
        textArea_output.setEditable(false);
        textArea_output.setScrollLeft(20);
        textArea_output.setPrefSize(375, 350);
        textArea_output.setStyle(  "-fx-background-color: #F9F7F7;");
        textArea_output.setWrapText(true); // 设置自动换行

        textArea_BliveMessage.setOpacity(0.66);  //透明度
        textArea_BliveMessage.setEditable(false);
        textArea_BliveMessage.setScrollLeft(20);
        textArea_BliveMessage.setPrefSize(360, 350);
        textArea_BliveMessage.setStyle( "-fx-background-color: #F9F7F7;");
        textArea_BliveMessage.setWrapText(true); // 设置自动换行

        HBox box_output = new HBox(textArea_output, textArea_BliveMessage);

        //提交窗口
        textField_input.setOpacity(0.75);
        textField_input.setPrefWidth(750);
        textField_input.setPrefHeight(30);
        box_right.setStyle(
                        "-fx-background-radius: 5px;" +
                        "-fx-cursor: pointer;"
        );
        box_right.getChildren().addAll(box_output, textField_input, hBox_right_in);

        //左侧框架
        VBox box_left = new VBox();
        Rectangle rectangle = new Rectangle(250, 650, Color.valueOf("#DBE2EF"));
        rectangle.setOpacity(0.77);

        //音乐列表
        listView.setPrefSize(250, 500);
        listView.setOpacity(0.75);

        //音乐列表鼠标右击选项
        MenuItem item1 = new MenuItem("移除该文件");
        MenuItem item2 = new MenuItem("添加音乐文件");
        MenuItem item3 = new MenuItem("从文件夹中添加音乐序列");
        contextMenu.getItems().addAll(item1, item2, item3);
        listView.setContextMenu(contextMenu);

        //左侧"启动"按钮
        Button left_start = new Button("启动");
        left_start.setPrefWidth(100);
        left_start.setPrefHeight(30);
        left_start.setFont(Font.font(20));
        left_start.setTranslateY(510);
        left_start.setTranslateX(13);

        Button music_recommend = new Button("话题分析");
        music_recommend.setPrefWidth(110);
        music_recommend.setPrefHeight(30);
        music_recommend.setFont(Font.font(20));
        music_recommend.setTranslateY(510);
        music_recommend.setTranslateX(126);

        //时间文本
        time.setFont(new Font(15));        //设置标签字体
        time.setTranslateX(65);
        time.setTranslateY(560);
        time.setTextFill(Paint.valueOf("#ffffff"));
        DateFormat currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm");    //设置时间格式  "HH:mm" 24小时   “hh:mm” 12小时

        //组合按钮和矩形
        Group rect_box_in = new Group(rectangle, left_start, music_recommend ,time, listView);
        box_left.getChildren().addAll(rect_box_in);

        Task<Void> task_blive = new Task<>() {

            protected Void call() throws IOException {
                textAreaHandler.FilePollingWatcher fileWatcher = new textAreaHandler.FilePollingWatcher(blivefile);
                fileWatcher.startWatching();
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "E:\\code\\Java\\Java_course_project_AI-Vtuber\\blivedm\\start.bat");
                processBuilder.start();
                // 保持程序运行
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task_blive).start(); //创建线程

        //音乐推荐 点击事件
        music_recommend.setOnAction(new TopicAnalysis());

        //音量点击事件
        btn_sound.setOnAction(new SoundButtonHandler());

        //列表触发器
        listView.setOnMouseClicked(new ListViewHandler(new PlayNextRunnable(), new OpenMusicFileRunnable(stage),
                new OpenMusicFolderRunnable(stage)));

        //添加事件触发器
        openMenuItem.setOnAction(new openLive2DModelHandler(stage));
        saveMenuItem.setOnAction(new saveDialogueHandler(stage));
        setting.setOnAction(new openNewWindow(stage));

        EventHandler<ActionEvent> eventHandler = _ -> time.setText(currentTime.format(new Date()));
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));  //一秒刷新一次
        animation.setCycleCount(Timeline.INDEFINITE);  //不断运行
        animation.play();

        //鼠标点击动作
        left_start.setOnMouseClicked(new LeftStarterHandler());

        //输入框触发\n 监听事件
        textField_input.setOnAction(new TextFieldHandler(echoSocket));

        //总布局设置
        pane.setRight(box_right);
        pane.setTop(menuBar);
        pane.setLeft(box_left);

        //图标
        stage.getIcons().add(icon);

        //背景
        BackgroundSize size = new BackgroundSize(Width, Height, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(background_image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, size);
        Background background = new Background(backgroundImage);
        pane.setBackground(background);

        //窗口全局设置
        stage.setTitle("AI-Vtuber");  //窗口名
        stage.setWidth(Width);  //窗口长
        stage.setHeight(Height); //窗口高
        stage.setResizable(false);  //窗口缩放选项 false || true
        //stage.setOpacity(0.94);  //窗口透明度

        //加载css面板
        scene.getStylesheets().add((Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm()));
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM lw.exe");
        Runtime.getRuntime().exec("taskkill /F /IM lwui.exe");
        Runtime.getRuntime().exec("taskkill /F /IM Python.exe");
        if (GPTSoVITSTask) {
            GPTSoVITSProcess.destroy();
        }
        if (Live2DViewerEX) {
            Live2DViewerEXProcess.destroy();
            close = true;
        }
        System.exit(0);
    }

}
