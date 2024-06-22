package org.java_course.java_course_project_aivtuber.GUI.Tool;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.corpus.FileDataSet;
import com.hankcs.hanlp.classification.corpus.IDataSet;
import com.hankcs.hanlp.classification.corpus.MemoryDataSet;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.classification.statistics.evaluations.Evaluator;
import com.hankcs.hanlp.classification.statistics.evaluations.FMeasure;
import com.hankcs.hanlp.classification.tokenizers.HanLPTokenizer;
import com.hankcs.hanlp.corpus.io.IOUtil;

import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.Application.CORPUS_FOLDER_TEXTCLASSIFICATION;

public class TextClassification {
    //public static final String CORPUS_FOLDER_EMOTION = "E:\\code\\Java\\Java_course_project_AI-Vtuber\\src\\main\\resources" +
            //"\\data\\model\\crf\\ChnSentiCorp情感分析酒店评论";
    //public static final String MODEL_PATH_MOTION = "E:\\code\\Java\\Java_course_project_AI-Vtuber\\src\\main\\resources" +
           // "\\data\\test\\classification_emotion-2-model.ser";
    private static String ModelPath;
    private static String CorpusFolder;

    public static IClassifier classifier = new NaiveBayesClassifier(); // 创建分类器，更高级的功能请参考IClassifier的接口定义
    public TextClassification(String ModelPath, String CorpusFolder) {
        TextClassification.ModelPath = ModelPath;
        TextClassification.CorpusFolder = CorpusFolder;
    }

    public void setCorpusFolder(String corpusFolder) {
        CorpusFolder = corpusFolder;
    }

    public String getModelPath() {
        return ModelPath;
    }

    public void setModelPath(String modelPath) {
        ModelPath = modelPath;
    }

    public String getCorpusFolder() {
        return CorpusFolder;
    }

    public String predict(String text) {
        System.out.printf("《%s》 属于分类 【%s】\n", text, classifier.classify(text));
        return classifier.classify(text);
    }

    public void trainOrLoadModel() throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(ModelPath);
        if (model != null) return;
        classifier.train(CorpusFolder);                     // 训练后的模型支持持久化，下次就不必训练了
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, CorpusFolder);
    }

    public static class Trainer {
        public static void main(String[] args) throws IOException
        {
            IDataSet trainingCorpus = new FileDataSet().                          // FileDataSet省内存，可加载大规模数据集
                    setTokenizer(new HanLPTokenizer()).                               // 支持不同的ITokenizer，详见源码中的文档
                    load(CORPUS_FOLDER_TEXTCLASSIFICATION, "UTF-8", 0.9);               // 前90%作为训练集
            IClassifier classifier = new NaiveBayesClassifier();
            classifier.train(trainingCorpus);
            IDataSet testingCorpus = new MemoryDataSet(classifier.getModel()).
                    load(CORPUS_FOLDER_TEXTCLASSIFICATION, "UTF-8", -0.1);        // 后10%作为测试集
            // 计算准确率
            FMeasure result = Evaluator.evaluate(classifier, testingCorpus);
            System.out.println(result);
        }
    }
}
