/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marginafterdecomplie;

/**
 *
 * @author huabing li
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import static marginafterdecomplie.MainRun.filePath;
import org.dom4j.Attribute;
//import javax.swing.text.Element;
import org.dom4j.Document;
import org.dom4j.ElementHandler;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLParse {
    

    
    
    static void XMLread(String filepath, JComboBox PONum, JComboBox SKU) throws DocumentException {       
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filepath));
        Element root = (Element) document.getRootElement();
        List<Node> listb = root.selectNodes("//OrderLines/OrderLine");
        //System.out.println(rootb.getText());     

            listb.forEach((E2) -> {
                PONum.addItem(E2.valueOf("@PONumber"));
            });
       

        List<Node> listbb = root.selectNodes("//OrderLines/OrderLine/SkuLines/SkuLine");
        //System.out.println(rootb.getText());
        listbb.forEach((E3) -> {
            SKU.addItem(E3.valueOf("@lookupKey"));
        });
    }

    static void XMLUpdate(String filepath, JComboBox PONum, JComboBox SKU, String EPC) throws DocumentException {
        EPC = "00B07A13C43EBA8808000001";
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filepath));
        Element root = (Element) document.getRootElement();
        String SkuKey = SKU.getSelectedItem().toString();
        List<Node> listbb = root.selectNodes("//OrderLines/OrderLine/SkuLines/SkuLine[@lookupKey='" + SkuKey + "']"
                + "/EPCList/EPC[text()='" + EPC + "']");
        listbb.forEach((E1) -> {
            Element epcElement = (Element) E1;
            // 修改encodeStatus
            Attribute encodeStatus = epcElement.attribute("encodeStatus");
            encodeStatus.setValue("BING");
            SKU.addItem(E1.getText());
        });

        try {
            /**
             * 将document中的内容写入文件中
             */
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filepath)));
            writer.write(document);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    static void XMLBuilder() throws Exception{
Document document = DocumentHelper.createDocument();
Element root = document.addElement("root");
for(int i=0;i<10;i++){
Element element1 = root.addElement("user")
.addAttribute("name","Alex"+i)
.addAttribute("id", "id"+i)
.addText("我是信息");
}

XMLWriter writer = new XMLWriter(new FileOutputStream(filePath+"\\output.xml"));
writer.write(document);
writer.close();
}
        public static void main(String[] args){
        try {
            XMLBuilder();
        } catch (Exception ex) {
            Logger.getLogger(XMLParse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
 

}


