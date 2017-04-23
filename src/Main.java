import javafx.application.Application;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by zfz on 17/4/21.
 */
public class Main extends Application {
    public void start(Stage primaryStage) throws ParserConfigurationException, IOException, SAXException, TransformerException{
        new View(primaryStage);
    }
}

