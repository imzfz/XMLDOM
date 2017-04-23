import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by zfz on 17/4/22.
 */
public class Settings{
    private ObservableList<Student> data;
    private LoadAndSave ls;
    public Settings() throws
            ParserConfigurationException, IOException, SAXException, TransformerException {
        data = FXCollections.observableArrayList();
        ls = new LoadAndSave(data);
   //     data.add(new Student("Zhangfz","M","20", "010-12345678", "ig"));
   //     data.add(new Student("Zhangfz","M","20", "010-12345678", ""));
    }

    public ObservableList<Student> getData(){
        return data;
    }

    public void write() throws ParserConfigurationException, IOException, SAXException, TransformerException{
        ls.write(data);
    }

    public ObservableList<Student> search(String text){
        ObservableList<Student> search = FXCollections.observableArrayList();
        if(text.equals("")){
            return data;
        }

        for(Student s : data){
            if(s.getName().equals(text)){
                search.add(s);
            }
        }
        return search;
    }

    public ObservableList<Student> select(){
        for(Student s : data){
            if(s.getCb().isSelected()){
                data.removeAll(s);
            }
        }
        return data;
    }

}
