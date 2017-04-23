import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 * Created by zfz on 17/4/22.
 */
public class Student {
    private final StringProperty name;
    private final StringProperty sex;
    private final StringProperty age;
    private final StringProperty tel;
    private final StringProperty comment;
    private final CheckBox cb;

    public Student(String name, String sex, String age, String tel, String comment, Boolean cb){
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.age = new SimpleStringProperty(age);
        this.tel = new SimpleStringProperty(tel);
        this.comment = new SimpleStringProperty(comment);
        this.cb = new CheckBox();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getAge() {
        return age.get();
    }

    public StringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getTel() {
        return tel.get();
    }

    public StringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public CheckBox getCb() {
        return cb;
    }
}
