import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by zfz on 17/4/22.
 */
public class View extends Stage{
    private TableView<Student> table = new TableView<>();
    private ObservableList<Student> data;
    private TableColumn<Student, String> name;
    private TableColumn<Student, String> sex;
    private TableColumn<Student, String> age;
    private TableColumn<Student, String> tel;
    private TableColumn<Student, String> comment;
    private TableColumn<Student, Boolean> del;
    private VBox container = new VBox();
    private HBox top = new HBox();
    private VBox form = new VBox();
    private HBox fun = new HBox();
    private Settings set = new Settings();
    private Button save = new Button("Save");
    private Button add = new Button("Add");
    private Button toSearch = new Button("Search");
    private Button delete = new Button("Delete");
    private Label title = new Label("通讯录管理系统");
    private final TextField addName = new TextField();
    private final TextField addSex = new TextField();
    private final TextField addAge = new TextField();
    private final TextField addTel = new TextField();
    private final TextField addComment = new TextField();
    private final TextField search = new TextField();
    private static boolean countChange = false;
//    private LoadAndSave ls;

    public View(Stage primaryStage) throws ParserConfigurationException, IOException, SAXException, TransformerException{
        data = set.getData();
        table.setItems(data);
        table.setEditable(true);
        form.getChildren().add(table);
        top.getChildren().addAll(title, search, toSearch);
        setProperties();
        nameSetting();
        sexSetting();
        ageSetting();
        telSetting();
        commentSetting();
        delSetting();
        btAction();
        Scene scene = new Scene(container, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        close(primaryStage);
    }

    public void nameSetting(){
        name = new TableColumn<>("姓名");
        name.setMinWidth(80);
        name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        name.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });
        table.getColumns().add(name);
    }

    public void sexSetting(){
        sex = new TableColumn<>("性别");
        sex.setMinWidth(50);
        sex.setCellValueFactory(new PropertyValueFactory<Student, String>("sex"));
        sex.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        sex.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSex(t.getNewValue());
                });
        table.getColumns().add(sex);
    }

    public void ageSetting(){
        age = new TableColumn<>("年龄");
        age.setMinWidth(50);
        age.setCellValueFactory(new PropertyValueFactory<Student, String>("age"));
        age.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        age.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAge(t.getNewValue());
                });
        table.getColumns().add(age);
    }

    public void telSetting(){
        tel = new TableColumn<>("电话");
        tel.setMinWidth(150);
        tel.setCellValueFactory(new PropertyValueFactory<Student, String>("tel"));
        tel.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tel.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTel(t.getNewValue());
                });
        table.getColumns().add(tel);
    }

    public void commentSetting(){
        comment = new TableColumn<>("备注");
        comment.setMinWidth(150);
        comment.setCellValueFactory(new PropertyValueFactory<Student, String>("comment"));
        comment.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        comment.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, String> t) -> {
                    ((Student) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setComment(t.getNewValue());
                });
        table.getColumns().add(comment);
    }

    public void delSetting(){
        del = new TableColumn<>("删除");
        del.setMinWidth(50);
        del.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("cb"));
        table.getColumns().add(del);
    }

    public void btAction()throws ParserConfigurationException, IOException, SAXException, TransformerException{
        save.setOnMouseClicked(t -> {
            try {
                set.write();
                countChange = false;
            }
            catch(TransformerException e){
                System.err.println("输出文件失败..!");
            }
            catch(ParserConfigurationException e){
                System.err.println(e.getMessage());
            }
            catch(IOException e){
                System.err.println(e.getMessage());
            }
            catch(SAXException e){
                System.err.println("xml格式有误..!");
            }
        });

        add.setOnMouseClicked(e -> {
            data.add(new Student(addName.getText(), addSex.getText(),
                    addAge.getText(), addTel.getText(),
                    addComment.getText(), false));
            addName.clear();
            addSex.clear();
            addAge.clear();
            addTel.clear();
            addComment.clear();
            countChange = true;
        });

        toSearch.setOnMouseClicked(e -> {
            table.setItems(set.search(search.getText()));
            search.clear();
        });

        delete.setOnMouseClicked(e -> {
            set.select();
            table.setItems(data);
            countChange = true;
        });
    }

    public void setProperties(){
        title.setFont(new Font("Adobe Devanagari Bold", 20));
        title.setPadding(new Insets(0,250,0,0));
        form.setPadding(new Insets(10,10,10,10));
        fun.getChildren().addAll(addName, addSex, addAge, addTel, addComment, add, save, delete);
        fun.setPadding(new Insets(10,10,10,10));
        fun.setSpacing(8);
        top.setPadding(new Insets(10,10,5,10));
        top.setSpacing(8);
        search.setMaxWidth(180);
        search.setPromptText("Input Name to search...");
        container.getChildren().addAll(top, form, fun);
        addName.setMaxWidth(80);
        addSex.setMaxWidth(80);
        addAge.setMaxWidth(80);
        addTel.setMaxWidth(80);
        addComment.setMaxWidth(80);
        addName.setPromptText("Name");
        addSex.setPromptText("Sex");
        addAge.setPromptText("Age");
        addTel.setPromptText("Tel");
        addComment.setPromptText("Comment");
    }

    public void close(Stage primaryStage){
        primaryStage.setOnCloseRequest(e -> {
            if(countChange) {
                Alert saveTips = new Alert(Alert.AlertType.CONFIRMATION, "Don't save?");
                saveTips.setHeaderText("Close");
                Optional<ButtonType> res = saveTips.showAndWait();
                if (res.get() == ButtonType.OK) {
                    System.out.println(res.get());
                    primaryStage.close();
                }
                else{
                    primaryStage.show();
                }
            }
        });
    }
}
