package org.java_course.java_course_project_aivtuber.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;
import static org.java_course.java_course_project_aivtuber.Application.icon;

import java.io.File;

public class openNewWindow implements EventHandler<ActionEvent> {
    private final Stage ownerStage;

    public openNewWindow(Stage ownerStage){
        this.ownerStage = ownerStage;
    }

    // 创建带样式的标签
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 5px;");
        return label;
    }

    // 创建带样式的 ComboBox
    private ComboBox<String> createStyledComboBox(String[] items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        return comboBox;
    }

    private Button createStyledButton() {
        Button button = new Button("选择");
        // 这里可以添加样式
        button.setStyle("-fx-font-size: 12px; -fx-padding: 10px;");
        return button;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Stage newWindow = new Stage();

        newWindow.setTitle("设置");

        // 设置模态，确保新窗口是独立的
        newWindow.initModality(Modality.APPLICATION_MODAL);  // 设置为窗口模态
        newWindow.initOwner(ownerStage);  // 设置主窗口为所有者窗口

        // 创建 SettingsManager 并加载当前设置
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();

        Label ffmpegLabel = createStyledLabel("FFmpeg 路径:");
        TextField ffmpegPathTextField = new TextField();
        ffmpegPathTextField.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        ffmpegPathTextField.setText(currentSettings.getFFmpegPath());
        Button ffmpegChooseButton = createStyledButton();

        // 配置文件选择器
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("所有文件", "*.*")
        );

        ffmpegChooseButton.setOnAction(event -> {
            fileChooser.setTitle("选择 FFmpeg 可执行文件");
            File selectedFile = fileChooser.showOpenDialog(newWindow);
            if (selectedFile != null) {
                ffmpegPathTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        // GPTSoVITS 路径选择
        Label gptsovitLabel = createStyledLabel("GPTSoVITS 路径:");
        TextField gptsovitPathTextField = new TextField();
        gptsovitPathTextField.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");
        gptsovitPathTextField.setText(currentSettings.getGPTSoVITSPath());
        Button gptsovitChooseButton = createStyledButton();

        gptsovitChooseButton.setOnAction(event -> {
            fileChooser.setTitle("选择 GPTSoVITS 可执行文件");
            File selectedFile = fileChooser.showOpenDialog(newWindow);
            if (selectedFile != null) {
                gptsovitPathTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Live2DViewerEX 路径选择
        Label live2dModelLabel = createStyledLabel("Live2DModel 路径:");
        TextField live2dLabelField = new TextField();
        live2dLabelField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-min-width: 20px;");
        live2dLabelField.setText(currentSettings.getLive2DModelPath());
        Button live2dModelChooseButton = createStyledButton();
        live2dModelChooseButton.setOnAction(event -> {
            fileChooser.setTitle("选择 Live2DModel 文件");
            File selectedFile = fileChooser.showOpenDialog(newWindow);
            if (selectedFile != null) {
                live2dLabelField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Live2DViewerModel 路径选择
        Label live2dLabel = createStyledLabel("Live2DViewerEX 路径:");
        TextField live2dPathTextField = new TextField();
        live2dPathTextField.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-min-width: 20px;");
        live2dPathTextField.setText(currentSettings.getLive2DViewerEXPath());
        Button live2dChooseButton = createStyledButton();
        live2dChooseButton.setOnAction(event -> {
            fileChooser.setTitle("选择 Live2DViewerEX 可执行文件");
            File selectedFile = fileChooser.showOpenDialog(newWindow);
            if (selectedFile != null) {
                live2dPathTextField.setText(selectedFile.getAbsolutePath());
            }
        });

        // 声音类型选择
        Label soundLabel = createStyledLabel("声音类型选择:");
        ComboBox<String> soundComboBox = createStyledComboBox(new String[]{"Hutao", "Shenlilinghua", "类型3"});
        soundComboBox.setValue(currentSettings.getSoundType());  // 设置当前值

        // AI模型类型
        Label aiLabel = createStyledLabel("AI模型类型:");
        ComboBox<String> aiComboBox = createStyledComboBox(new String[]{"mistralai/mistral-7b-instruct:free", "openchat/openchat-7b:free", "gryphe/mythomist-7b:free", "meta-llama/llama-3-8b-instruct:free", "google/gemma-7b-it:free", "nousresearch/nous-capybara-7b:free"});
        aiComboBox.setValue(currentSettings.getAiModelType());  // 设置当前值

        // 语言模块选择
        Label languageLabel = createStyledLabel("语言模块选择:");
        ComboBox<String> languageComboBox = createStyledComboBox(new String[]{"auto", "zh", "en", "ja", "all_zh", "all_ja"});
        languageComboBox.setValue(currentSettings.getLanguageModule());  // 设置当前值

        // 提示词输入
        Label promptLabel = createStyledLabel("输入提示词:");
        TextField promptTextField = new TextField();
        promptTextField.setText(currentSettings.getSystemprompt());  // 设置当前值
        promptTextField.setStyle("-fx-font-size: 14px; -fx-padding: 5px;");

        Label Enble = createStyledLabel("启动桌面桌宠");
        // 新增 ComboBox
        ComboBox<String> desktopPetComboBox = createStyledComboBox(new String[]{"是", "否"});
        desktopPetComboBox.setValue(currentSettings.getEnbleLive2DViewerEX());  // 设置当前值

        Label EnbleBlive = createStyledLabel("启动直播间监听");
        // 新增 ComboBox
        ComboBox<String> BliveComboBox = createStyledComboBox(new String[]{"是", "否"});
        BliveComboBox.setValue(currentSettings.getEnbleBlive());  // 设置当前值

        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setStyle("-fx-padding: 10px;");

// Adding elements to the GridPane
        layout.addRow(0, soundLabel, soundComboBox);
        layout.addRow(1, aiLabel, aiComboBox);
        layout.addRow(2, languageLabel, languageComboBox);
        layout.addRow(3, promptLabel, promptTextField);
        layout.addRow(4, live2dModelLabel, live2dLabelField, live2dModelChooseButton);
        layout.addRow(5, gptsovitLabel, gptsovitPathTextField, gptsovitChooseButton);
        layout.addRow(6, Enble, desktopPetComboBox);  // 启动桌面桌宠选项
        layout.addRow(7, EnbleBlive, BliveComboBox);
        layout.addRow(8, live2dLabel, live2dPathTextField, live2dChooseButton);
        layout.addRow(9, ffmpegLabel, ffmpegPathTextField, ffmpegChooseButton);

// Adjusting columns to ensure labels and controls are properly aligned
        GridPane.setHgrow(gptsovitPathTextField, Priority.ALWAYS);
        GridPane.setHgrow(live2dPathTextField, Priority.ALWAYS);
        GridPane.setHgrow(ffmpegPathTextField, Priority.ALWAYS);

// Adjusting alignment for the entire GridPane
        layout.setAlignment(Pos.CENTER);

        // 为按钮设置点击事件
        newWindow.setOnCloseRequest(event ->{
            Settings settings = new Settings();
            settings.setSoundType(soundComboBox.getValue());
            settings.setAiModelType(aiComboBox.getValue());
            settings.setLanguageModule(languageComboBox.getValue());
            settings.setEnbleLive2DViewerEX(desktopPetComboBox.getValue());  // 保存启动桌面桌宠选项
            settings.setLive2DModelPath(live2dLabelField.getText());
            settings.setSystemprompt(promptTextField.getText());
            settings.setFFmpegPath(ffmpegPathTextField.getText());  // 保存 ffmpeg 路径
            settings.setGPTSoVITSPath(gptsovitPathTextField.getText());  // 保存 GPTSoVITS 路径
            settings.setLive2DViewerEXPath(live2dPathTextField.getText());  // 保存 Live2DViewerEX 路径
            settings.setEnbleBlive(BliveComboBox.getValue());
            // 将设置对象保存到文件
            settingsManager.saveSettings(settings);

            newWindow.close();  // 模拟保存并关闭窗口
        });
        Scene newScene = new Scene(layout, 600, 500);
        newWindow.setScene(newScene);
        //图标
        newWindow.getIcons().add(icon);
        newWindow.setResizable(false);  //窗口缩放选项 false || true
        newWindow.show();
    }

}
