package MyFrame;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.dlsc.pdfviewfx.PDFView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MediaPlayerController {

    private Boolean bind = true;
    private String path;
    private MediaPlayer mediaPlayer;
    BlockingQueue queue = new ArrayBlockingQueue(1);//数组型队列，长度为1

    @FXML
    private Stage stage;

    @FXML
    private Media media;

    @FXML
    ImageView imageView;
    @FXML
    private MediaView mediaView;

    @FXML
    private Button openFile;


    @FXML
    private FlowPane rightPane;

    @FXML
    private VBox vBox_bottom;

    @FXML
    private HBox hBox_bottom;
    @FXML
    VBox pdfVBox;
    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton pdfInputButton;

    @FXML
    private HBox hBox;
    @FXML
    private Slider volumeSlider;

    @FXML
    private JFXSlider progressBar;

    @FXML
    private Label label;

    @FXML
    private FlowPane informPane;
    @FXML
    private Pane informPaneSub;
    @FXML
    private AnchorPane pdfHeadPane;
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab pdfTab;

    @FXML
    private Tab formulaToImgTab;
    @FXML
    private Tab studyVideoTab;
    @FXML
    private Tab myStudyTab;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField nameText;

    @FXML
    private TextField ageText;

    @FXML
    private TextField durationText;

    @FXML
    private TextField studyTimesText;

    @FXML
    private TextField lastGradeText;

    @FXML
    private TextField TotalDurationText;


    @FXML
    private TextField formulaInputText;

    @FXML
    private TextField formulaResultText;

    @FXML
    private JFXButton formulaButton;

    @FXML
    private JFXButton formulaToImgButton;

    @FXML
    private JFXButton commonFormula1;
    @FXML
    private JFXButton commonFormula2;
    @FXML
    private JFXButton commonFormula3;
    @FXML
    private JFXButton commonFormula4;
    @FXML
    private JFXButton commonFormula5;
    @FXML
    private JFXButton commonFormula6;
    @FXML
    private JFXButton studyVideoButton;

    @FXML
    private JFXButton myStudyButton;


    @FXML
    private AnchorPane playerPane;




    private FileChooser chooser;

    private PDFView pdfView = new PDFView();


    private double borderPaneWidth ;
    private double borderPaneHeight ;
    private double stackPaneWidth ;
    private double playerPanePaneWidth ;
    private double playerPanePaneHeight ;

    private double hBoxPaneWidth ;
    private double hBoxPaneHeight;
    private double vBox_bottomPaneWidth ;

    private double vBox_bottomTranslateY;
    private double variationBoxHeight;

    private double rightPaneWidth;
    private double rightPaneHeight;

    private Double hBoxW;
    private Double hBoxH;




    //基于watch文件监控功能设置播放器
    public void setOnWatchMediaPlayer(File file) {
        path = file.toURI().toString();
        mediaPlayerCore(path);
    }

    @FXML
    private void OpenFileMethod(ActionEvent event) {
        //用来解决多次打开，前一次的资源未释放，造成卡顿
        if(mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a .mp4 file", ".mp4");
//        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();
        mediaPlayerCore(path);
    }


    @FXML
    public void openPDF(ActionEvent event) {

        MenuItem loadItem = new MenuItem("Load PDF...");
//        pdf_input_button.setAccelerator(KeyCombination.valueOf("SHORTCUT+o"));
        if (chooser == null) {
            chooser = new FileChooser();
            chooser.setTitle("Load PDF File");
            final FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
            chooser.getExtensionFilters().add(filter);
            chooser.setSelectedExtensionFilter(filter);
        }

        VBox.setVgrow(pdfView, Priority.ALWAYS);
        System.out.println(pdfView.getScene().getWindow().getHeight() + "--------------PDFWINDOW::::");
        Window win = pdfView.getScene().getWindow();
        System.out.println(win + ":::::WINWIN:::::");

        tabPane.requestFocus();



        //切换tab面板
        SkinBase skin = (SkinBase) tabPane.getSkin();
        TabPaneBehavior tabPaneBehavior = new TabPaneBehavior(tabPane);
        tabPaneBehavior.selectTab(pdfTab);
        pdfTab.getContent().requestFocus();
        System.out.println(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()) + "----TABTAB:::");

        final File file = chooser.showOpenDialog(pdfView.getScene().getWindow());
        if (file != null) {
            pdfView.load(file);

        }

    }

    //公式转图片模块
    public void formulaToImg(ActionEvent event){
        tabPane.requestFocus();

        SkinBase skin = (SkinBase) tabPane.getSkin();
        TabPaneBehavior tabPaneBehavior = new TabPaneBehavior(tabPane);
        tabPaneBehavior.selectTab(formulaToImgTab);
        pdfTab.getContent().requestFocus();
        System.out.println(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()) + "----TABTAB:::");
    }

    //保留接口
    public void studyVideo(ActionEvent event){
        tabPane.requestFocus();

        SkinBase skin = (SkinBase) tabPane.getSkin();
        TabPaneBehavior tabPaneBehavior = new TabPaneBehavior(tabPane);
        tabPaneBehavior.selectTab(studyVideoTab);
        pdfTab.getContent().requestFocus();
        System.out.println(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()) + "----TABTAB:::");
    }

    //我的学习模块
    public void myStudy(ActionEvent event){
        tabPane.requestFocus();

        SkinBase skin = (SkinBase) tabPane.getSkin();
        TabPaneBehavior tabPaneBehavior = new TabPaneBehavior(tabPane);
        tabPaneBehavior.selectTab(myStudyTab);
        pdfTab.getContent().requestFocus();
        System.out.println(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()) + "----TABTAB:::");
    }


    private boolean clickFlag = true;

    private void mediaPlayerCore(String path) {



        if (path != null) {
            System.out.println("重新加载文件path"+path);
            media = new Media(path);



                System.out.println("pw 空");
            if (mediaPlayer != null) {
                mediaPlayer.dispose();
                mediaPlayer = new MediaPlayer(media);
            }else {
                mediaPlayer = new MediaPlayer(media);
            }



            mediaView.setMediaPlayer(mediaPlayer);
//            creating bindings



            //音量初始化为60%
            volumeSlider.setValue(mediaPlayer.getVolume() * 60);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                  @Override
                  public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                      if (bind) {
                          progressBar.setValue(newValue.toSeconds());
                      }

                  }
              }
            );


            progressBar.setOnMousePressed(event1 -> bind = false);
            progressBar.setOnMouseReleased(event1 -> {
                System.out.println(Duration.seconds(progressBar.getValue()) + "Sec");
                mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                bind = true;
            });

            progressBar.setValueFactory(slider ->
                    Bindings.createStringBinding(
                            () -> ((int) slider.getValue()/60) + "分",
                            slider.valueProperty()
                    ));

            progressBar.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    System.out.println(progressBar.getValue() + "-------pr---------");
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });







            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    System.out.println(total + "dE");
                    System.out.println(total.toSeconds() + "sec");
                    progressBar.setMax(total.toSeconds());
                    System.out.println("MMMMMMMMMM::::" + media.getWidth() + "::::::" + media.getHeight());
                    System.out.println("盒子hBox::::" + hBox.getWidth() + "::::::" + hBox.getHeight());

                    double w = hBox.getWidth() / media.getWidth();
                    double h = hBox.getHeight() / media.getHeight();
                    System.out.println(playerPane.getHeight()+"大盒子宽度");
                    System.out.println("MYMYMY:::::" + (vBox_bottom.getHeight()));
                    System.out.println(w + "vv" + h + "-------------DDDD“”“”“”“”“”“”“”“”" + (hBox.getHeight() - media.getHeight() * w));

                    vBox_bottom.setTranslateY(vBox_bottom.getTranslateY()-(hBox.getHeight() - media.getHeight() * w));

                    System.out.println(mediaPlayer.getMedia().getWidth() + "YYY");
                    System.out.println(mediaPlayer.getMedia().getHeight() + "YYYH");
                    ObservableMap<String, Object> map = media.getMetadata();
                    for (String key : map.keySet()) {
                        System.out.println(key + ":::-:::" + map.get(key));
                    }


                }

            });

            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {

//                    mediaPlayer.dispose();
                }
            });
        }

    }


    public void initialize() {
        // TODO



        DoubleProperty widthProp = mediaView.fitWidthProperty();
        DoubleProperty heightProp = mediaView.fitHeightProperty();



        double width = hBox.getWidth();
        double height = hBox.getHeight();





        borderPane.heightProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if ( ((Number) oldValue).intValue() != 0) {
                    double ratio = (Double)newValue/ (Double)borderPaneHeight;
                }
            }
        });


        borderPane.widthProperty().addListener(new ChangeListener() {
            boolean flag =true ;
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (flag) {
                    //获取初始宽高只执行一行
                    //用于记录初始宽高 此值不应该更改
                    borderPaneWidth = borderPane.getWidth();
                    borderPaneHeight =borderPane.getHeight();
                    stackPaneWidth = stackPane.getPrefWidth();
                    playerPanePaneWidth = playerPane.getPrefWidth();
                    playerPanePaneHeight = playerPane.getPrefHeight();
                    hBoxPaneWidth = hBox.getPrefWidth();
                    hBoxPaneHeight = hBox.getPrefHeight();
                    vBox_bottomPaneWidth = vBox_bottom.getPrefWidth();
                    vBox_bottomTranslateY = vBox_bottom.getTranslateY();
                    rightPaneWidth = rightPane.getPrefWidth();
                    rightPaneHeight = rightPane.getPrefHeight();
                    //执行完一次后标志位设为假,此代码初始完后不再执行
                    flag = false;
                }

                System.out.println("新值"+((Number) newValue).intValue() +"旧值"+oldValue);
                if ( ((Number) oldValue).intValue() != 0) {
                    System.out.println(newValue.getClass().getName().toString());
                    double ratio = (Double)newValue/ (Double)borderPaneWidth;
//                    mediaView.setPreserveRatio(true);
                    System.out.println(stackPaneWidth*ratio+"stackPaneWidth*ratio");
                    stackPane.setPrefWidth(stackPaneWidth*ratio);
                    System.out.println(playerPanePaneWidth*ratio+"playerPanePaneWidth*ratio");
                    playerPane.setPrefWidth(playerPanePaneWidth*ratio);
                    playerPane.setPrefHeight(playerPanePaneHeight * ratio);
                    System.out.println(hBoxPaneWidth*ratio+"hBoxPaneWidth");
                    hBox.setPrefWidth(hBoxPaneWidth*ratio);
                    variationBoxHeight = (hBoxPaneHeight * ratio) - hBoxPaneHeight;
                    System.out.println(variationBoxHeight + "改变量");
                    hBox.setPrefHeight(hBoxPaneHeight * ratio);
                    System.out.println(vBox_bottomPaneWidth*ratio+"vBox_bottomPaneWidth*ratio");
                    vBox_bottom.setPrefWidth(vBox_bottomPaneWidth*ratio);
                    vBox_bottom.setTranslateY(vBox_bottomTranslateY+variationBoxHeight);


                    mediaView.setFitWidth(hBox.getPrefWidth());
                    mediaView.setSmooth(true);


                    informPane.setPrefWidth(vBox_bottomPaneWidth*ratio);
                    informPane.setTranslateY(variationBoxHeight);
                    informPaneSub.setPrefWidth(vBox_bottomPaneWidth*ratio);
                    System.out.println(oldValue + "::old::new::" + newValue);

                    System.out.println(rightPane.getPrefWidth()*ratio+"rightPane");
                    rightPane.setPrefWidth(rightPaneWidth*ratio);
                    rightPane.setPrefHeight(borderPane.getPrefHeight());
                    pdfHeadPane.setPrefWidth(rightPaneWidth*ratio);
                    tabPane.setPrefWidth(rightPaneWidth * ratio);
                    tabPane.setPrefHeight(borderPane.getPrefHeight());
                    pdfVBox.setPrefHeight(borderPane.getPrefHeight());
                    pdfView.setMinWidth(rightPaneWidth * ratio);
                    pdfView.setMinHeight(borderPane.getPrefHeight());
                }

            }
        });



        System.out.println(borderPaneWidth + "@:::::::::::::::hh@"+borderPaneHeight);

        widthProp.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double ratio = newValue.doubleValue() / borderPaneWidth;
//                mediaView.setPreserveRatio(true);
                System.out.println(ratio + "pppp");
                mediaView.setSmooth(true);
                System.out.println(oldValue + "::old::new::" + newValue);
            }
        });
        heightProp.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("我被执行了");
            }
        });



        //用于双击放大播放器或缩小播放器
        mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getClickCount() == 2 && clickFlag == true) {
                    double w = borderPane.getWidth();
                    double h = borderPane.getHeight();
                    double ratio = borderPane.getWidth()/hBox.getWidth();
                    System.out.println(w + "全屏宽"+ratio);
                    System.out.println(h + "全屏高");


                    //设置组件的层级，每个子组件只能设置与自己的直接父组件的显示层次关系，所以显示只能从最下次一个一个层次往上设置
                    //数字越小显示层级越高
                    //这个组件是显示层最下层组件 ，现设置为-1.0，显示在顶层
                    mediaView.setViewOrder(-1.0);
                    hBox.setViewOrder(-0.9);
                    playerPane.setViewOrder(-0.8);
                    //这个在组件是显示层次的最上层 ,调整层次等级为低优先级。让mediaView显示在最上面
                    stackPane.setViewOrder(-0.7);




                    System.out.println("第g 一次是否是全屏"+stage.isFullScreen());
                    System.out.println("第g 一次是否最大化"+stage.isMaximized());

//                    判断是否是全屏
                    if (stage.isMaximized()) {
                        hBoxW = hBox.getWidth();
                        hBoxH = hBox.getHeight();
                        hBox.setPrefHeight(h-vBox_bottom.getHeight()-40);
                        mediaView.setFitWidth((h-vBox_bottom.getHeight()-40)/hBox.getHeight()*hBox.getPrefWidth()-20);
                        stackPane.setPrefWidth(stackPane.getWidth());
                        playerPane.setPrefWidth(stackPane.getWidth());
                        hBox.setPrefWidth(stackPane.getWidth());

                        System.out.println("YYYS高"+(h-vBox_bottom.getHeight()));
                        vBox_bottom.setTranslateY((h-vBox_bottom.getHeight())-30);
                    }else {
                        stackPane.setPrefHeight(hBox.getPrefHeight()*ratio+vBox_bottom.getPrefHeight()+70);
                        playerPane.setPrefHeight(hBox.getPrefHeight()*ratio+vBox_bottom.getPrefHeight());
                        stage.setHeight(hBox.getPrefHeight()*ratio+vBox_bottom.getPrefHeight()+70);
                        mediaView.setFitWidth(w);

                        vBox_bottom.setTranslateY(hBox.getPrefHeight()*ratio);
                    }



                    System.out.println(hBox.getHeight()+"HH____________"+mediaView.getFitHeight());


                    System.out.println(hBoxPaneHeight*borderPaneWidth/hBoxPaneWidth+"bottem有吗");
                    System.out.println(mediaView.getFitHeight()+"b---------有吗");

                    vBox_bottom.setPrefWidth(w);
                    hBox_bottom.setAlignment(Pos.BOTTOM_LEFT);
                    System.out.println(hBox.getPrefHeight()*ratio+vBox_bottom.getPrefHeight()+"n那ud调试屏幕");




                    System.out.println(borderPane.getPrefHeight()+"PreHHHHHborderPaneHHHHHHHHH");
                    System.out.println(borderPane.getHeight()+"HHHHHHHHHHHborderPaneHHHHHHHHHH");
                    clickFlag = false;
                } else if (event.getClickCount() == 2&& clickFlag == false) {
                    double w = hBox.getWidth();
                    double h = hBox.getHeight();

                    System.out.println(w + "小盒子宽");
                    System.out.println(h + "小盒子高");
                    //设置组件的层级，每个子组件只能设置与自己的直接父组件的显示层次关系，所以显示只能从最下次一个一个层次往上设置
                    //数字越小显示层级越高
                    mediaView.setViewOrder(0);
                    hBox.setViewOrder(0);
                    playerPane.setViewOrder(0);

                    stackPane.setViewOrder(0);

                    mediaView.setFitWidth(hBox.getPrefWidth());
//                    mediaView.setFitHeight(hBoxPaneHeight);






                    vBox_bottom.setTranslateY(hBox.getPrefHeight());
                    vBox_bottom.setPrefWidth(hBox.getPrefWidth());
//                    hBox_bottom.setAlignment(Pos.BOTTOM_CENTER);
                    //                    判断是否是全屏
                    System.out.println("第二次是否是全屏"+stage.isFullScreen());
                    System.out.println("第二次是否最大化"+stage.isMaximized());

                    if (stage.isMaximized()) {
                        hBox.setPrefHeight(hBoxW);

                        mediaView.setFitWidth(hBoxW);
                        stackPane.setPrefWidth(hBoxW);
                        hBox.setPrefWidth(hBoxW);
                        hBox.setPrefHeight(hBoxH);
                        vBox_bottom.setTranslateY(hBoxH);
                    }else {
                        stage.setHeight(800);
                    }

                    clickFlag = true;
                }
            }
        });


        pdfView.setShowThumbnails(false);
        pdfView.setMinHeight(600);
        pdfVBox.getChildren().add(pdfView);

        UserRecordEntity userRecord = SaxReadXml.readXml();

        if (userRecord != null) {
            nameText.setText(userRecord.getUserName());
            ageText.setText(String.valueOf(userRecord.getAge()));
            durationText.setText(String.valueOf(userRecord.getDuration()));
            studyTimesText.setText(String.valueOf(userRecord.getStudyTimes()));
            lastGradeText.setText(String.valueOf(userRecord.getLastGrade()));
            TotalDurationText.setText(String.valueOf(userRecord.getTotalDuration()));
        }


        nameText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > 10) {
                    System.out.println("超出名字取值范围");
                }else{
                    try {
                        WriteXML.SAXcreate(new File("my_records.xml"),new UserRecordEntity(1,newValue,Integer.valueOf(ageText.getText()),userRecord.getStartStudyTime(),userRecord.getEndStudyTime(),(userRecord.getDuration()), userRecord.getStudyTimes(), 22,userRecord.getTotalDuration() ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });

        ageText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int newV=0;
                try {
                    newV= Integer.valueOf(newValue);
                }catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("警告窗口");
                    alert.setHeaderText("发生错误");
                    alert.setContentText("><><><NumberFormatException必须输入数学><><><");
                    alert.showAndWait();
                }

                if (newV > 100 && newV < 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("警告窗口");
                    alert.setHeaderText("发生错误");
                    alert.setContentText("><><><年龄不在合理范围内><><><");
                    alert.showAndWait();
                    System.out.println("超出年龄范围");
                    if (ageText.isFocused() != true) {
                        alert.setTitle("属性设置窗口");
                        alert.setHeaderText("年龄设置成功");
                        alert.setContentText("><><><");
                        alert.showAndWait();
                    }
                }else {
                    try {
                        WriteXML.SAXcreate(new File("my_records.xml"),new UserRecordEntity(1, nameText.getText(), Integer.valueOf(newValue),userRecord.getStartStudyTime(),userRecord.getEndStudyTime(),(userRecord.getDuration()), userRecord.getStudyTimes(), 22,userRecord.getTotalDuration() ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        nameText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean) {
                    System.out.println("失焦");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("属性设置窗口");
                    alert.setHeaderText("姓名设置成功");
                    alert.setContentText("><><><");
                    alert.showAndWait();
                }
                if (t1) {
                    System.out.println("聚焦");
                }
            }
        });
        ageText.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (aBoolean) {
                    System.out.println("失焦");
                }
                if (t1) {
                    System.out.println("聚焦");
                }
            }
        });


    }


    //用于启动Mp4文件变化监控功能
    public void initWatch() {
        MyThread myThread = new MyThread();
        myThread.setMediaPlayerController(this);
        myThread.start();


    }

    public void pauseVideo(ActionEvent event) {
        mediaPlayer.pause();
        System.out.println("暂停");
    }

    public void stopVideo(ActionEvent event) {
        mediaPlayer.stop();
        System.out.println("停止");
    }

    public void playVideo(ActionEvent event) {
        mediaPlayer.getCurrentTime();
        mediaPlayer.getTotalDuration();
        if ((mediaPlayer.getCurrentTime().toSeconds()-mediaPlayer.getTotalDuration().toSeconds())==0) {
            System.out.println("我是零啊");
            mediaPlayer.seek(Duration.ZERO);
        }


        mediaPlayer.play();
        mediaPlayer.setRate(1);
        System.out.println("播放");
    }

    public void skip5(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5)));
    }

    public void furtherSpeedUpVideo(ActionEvent event) {
        mediaPlayer.setRate(1.3);
    }

    public void back5(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-5)));
    }

    public void furtherSlowDownVideo(ActionEvent event) {
        mediaPlayer.setRate(0.8);
    }

    private static File file = new File("myformula.xml");

    //线程主要为了解决耗时任务使窗体卡住的问题。Java线程分为UI线程和非UI线程,
    // JavaFx不允许在非UI线程改变UI效果，如果我们在非UI线程中尝试改变UI效果，程序就会抛出下面的异常。
    //解决办法是使用其为我们提供的Platform.runLater运行UI线程，与UI任务与耗时任务分开处理
    public void submitFormula(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = formulaInputText.getText();

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());
                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }

    public void commonFormula1(ActionEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = "\\frac{1+3x}{\\sqrt{4+5x^{2}}}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    formulaResultText.setText(error);
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());
                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }
    public void commonFormula2(ActionEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = "\\frac{1}{\\sqrt{2\\pi}}e^{-\\frac{x^{2}}{2}}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());
                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }
    public void commonFormula3(ActionEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = "e^{\\arctan x}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
//            String error = JavaCallPython.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());
                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }
    public void commonFormula4(ActionEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = "x e^{-x}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());

                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }

    public void commonFormula5(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String str = "\\frac{3 x^{2}+4 x+4}{x^{2}+x+1}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());

                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");

        alert.showAndWait();
    }
    public void commonFormula6(ActionEvent event) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String str =  "-x^{4}+2x^{2}";

                try {
                    WriteXML.SAXcreate(file, str);
                    String error =JavaCallPython.StaticInnerClassSingleton.getInstance().formulaVerification();
                    if (error != "") {
                        JavaCallPython.StaticInnerClassSingleton.getInstance().startPythonPlot();
                    }
                    Platform.runLater(
                            new Runnable() {
                                @Override
                                public void run() {
                                    formulaResultText.setText(SaxReadXml.getProjectName());
                                }
                            });
                } catch (Exception e) {
                    throw new RuntimeException(e);

                }
            }
        }).start();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("动画生成弹窗");
        alert.setHeaderText("动画生成，请不要关闭窗口");
        alert.setContentText("><><><请等待，动画生成中->->->->->->->");
        alert.showAndWait();
    }
    public void highResolutionMenuItem(ActionEvent event){
        JavaCallPython.StaticInnerClassSingleton.getInstance().setRenderingQuality("-pqh");
    }
    public void middleResolutionMenuItem(ActionEvent event){
        JavaCallPython.StaticInnerClassSingleton.getInstance().setRenderingQuality("-pqm");
    }
    public void lowResolutionMenuItem(ActionEvent event) {
        JavaCallPython.StaticInnerClassSingleton.getInstance().setRenderingQuality("-pql");

    }

    public void setFormulaResult(String text){
        formulaResultText.setText(text);
    }

    //把窗口Stage传过来，以便动态控制窗口显示大小
    public void stageBind(Stage stage){
        this.stage= stage;
    }



}
