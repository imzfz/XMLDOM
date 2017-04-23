/**
 * Created by zfz on 17/4/22.
 */


import javafx.collections.ObservableList;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;


public class LoadAndSave {
    private Document document;
    private Element root;
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder = factory.newDocumentBuilder();
    static int count = 0;


    public LoadAndSave(ObservableList<Student> data) throws
            ParserConfigurationException, IOException, SAXException, TransformerException{
        load(data);
    }

    public void load(ObservableList<Student> data)
            throws ParserConfigurationException, IOException, SAXException, TransformerException{
        try {
            File file = new File("src/b.xml");
            if(!file.exists()){
                create();
            }
            document = builder.parse(new File("src/b.xml"));
            read(data);
        }
        catch (SAXException e){
            System.err.println("XML格式错误..!");
        }
    }

    public void create() throws TransformerException{
        try {
            System.out.println("文件不存在..正在创建");
            document = builder.newDocument();
            Element students = document.createElement("Students");
            Element student = document.createElement("student");
            Element name = document.createElement("name");
            Element sex = document.createElement("sex");
            Element age = document.createElement("age");
            Element tel = document.createElement("tel");
            Element comment = document.createElement("comment");
            document.appendChild(students);
            students.appendChild(student);
            student.setAttribute("id", "1");
            student.appendChild(name);
            name.appendChild(document.createTextNode("NameExample"));
            student.appendChild(sex);
            sex.appendChild(document.createTextNode("SexExample"));
            student.appendChild(age);
            age.appendChild(document.createTextNode("AgeExample"));
            student.appendChild(tel);
            tel.appendChild(document.createTextNode("TelExample"));
            student.appendChild(comment);
            comment.appendChild(document.createTextNode("CommentExample"));

            output(students);
        }
        catch(TransformerException e){
            System.err.println("输出文件失败..!");
        }
    }

    public void read(ObservableList<Student> data){
        System.out.println("正在加载文件....");
        String info[] = {"name", "sex", "age", "tel", "comment"};
        String value[] = {"", "", "", "", ""};
        root = document.getDocumentElement();
        NodeList node = root.getElementsByTagName("student");
        for (int i = 0; i < node.getLength(); i++) {
            Element ele = (Element) node.item(i);
            for (int j = 0; j < 5; j++) {
                NodeList node1 = ele.getElementsByTagName(info[j]);
                value[j] = node1.item(0).getChildNodes().item(0).getNodeValue();
            }
            data.add(new Student(value[0],
                    value[1], value[2], value[3], value[4], false));
 //           System.out.println(data.get(0).getComment());
        }
        System.out.println("加载成功..!");
    }

    public void write(ObservableList<Student> data) throws
            ParserConfigurationException, IOException, SAXException, TransformerException{
        del();
        document = builder.parse(new File("src/b.xml"));
        Element students = document.createElement("Students");
        Element student = document.createElement("student");
        Element name = document.createElement("name");
        Element sex = document.createElement("sex");
        Element age = document.createElement("age");
        Element tel = document.createElement("tel");
        Element comment = document.createElement("comment");
        document.appendChild(students);

        for(Student s : data){
            if(s.getName().equals("")){
                s.setName(" ");
            }
            if(s.getSex().equals("")){
                s.setSex(" ");
            }
            if(s.getAge().equals("")){
                s.setAge(" ");
            }
            if(s.getTel().equals("")){
                s.setTel(" ");
            }
            if(s.getComment().equals("")){
                s.setComment(" ");
            }
        }

//        System.out.println(document.hasChildNodes());
        if(document.hasChildNodes()) {
//            System.out.println("aaa");
            if (document.getChildNodes().item(0).getNodeName().equals("Students")) {
//                System.out.println("bbb");
                root = document.getDocumentElement();
                for (int i = 0; i < data.size(); i++) {
//                    System.out.println("ccc");
                    student = document.createElement("student");
                    root.appendChild(student);
                    name = document.createElement("name");
                    sex = document.createElement("sex");
                    age = document.createElement("age");
                    tel = document.createElement("tel");
                    comment = document.createElement("comment");
                    student.setAttribute("id", String.valueOf(count + 1));
                    student.appendChild(name);
                    name.appendChild(document.createTextNode(data.get(i).getName()));
                    student.appendChild(sex);
                    sex.appendChild(document.createTextNode(data.get(i).getSex()));
                    student.appendChild(age);
                    age.appendChild(document.createTextNode(data.get(i).getAge()));
                    student.appendChild(tel);
                    tel.appendChild(document.createTextNode(data.get(i).getTel()));
                    student.appendChild(comment);
                    comment.appendChild(document.createTextNode(data.get(i).getComment()));
                    count++;
                }
                output(root);
            }
        }
    }

    public void output(Element students) throws TransformerException {
        try {
            System.out.println("正在输出文件....");
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(students), new StreamResult("src/b.xml"));
            count = 0;
            System.out.println("输出成功..!");
        }
        catch(TransformerException e){
            System.err.println("输出失败..!");
        }
    }

    public void del() throws
            ParserConfigurationException, IOException, SAXException, TransformerException{
        document = builder.parse(new File("src/b.xml"));
        root = document.getDocumentElement();
        NodeList node = root.getElementsByTagName("student");
        for(int i = 0; i < node.getLength(); i++){
//            System.out.println("mmm   " + node.item(i).getNodeName());
            root.removeChild(node.item(i));
        }
        output(root);
    }
}
