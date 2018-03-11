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
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
//import javax.swing.text.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import javax.xml.xpath.XPathFactory;
import static marginafterdecomplie.MainRun.marginW;
public class XMLParse  {
    
    static void XMLread(String filepath,JComboBox PONum,JComboBox  SKU) throws DocumentException {
        //amend value of XML element     
    //element.element("menuId").setText(menu.getMenuId());  
    //E1.selectNodes("//SkuLines/SkuLine[@lookupKey='2000113642355']");
    // MarginTest MarginW=MainRun.marginW;
    SAXReader reader = new SAXReader();
    Document document = reader.read(new File(filepath));
   Element root = (Element) document.getRootElement();  
   List<Element>  listbb = root.selectNodes("//OrderLines/OrderLine");
    //System.out.println(rootb.getText());
    for (Element E1 :listbb)
    {
         PONum.addItem(E1.attributeValue("PONumber"));
     List<Element>  listb = E1.selectNodes("//SkuLines/SkuLine");
       for (Element E2:listb)
       {
           SKU.addItem(E2.attributeValue("lookupKey"));
       }
     
    }
     
   // List ecList = root1.selectNodes("//SkuLine"); //查找文档中所有的 c 节点    
   // List<Element> list= root
   List<Element> list = root.elements("SkuLines");
    for(Element element:list){
        String qname = element.getName();
        String name = element.getText();
        System.out.println(qname+"--"+name);
              List<Element> list1 = element.elements("OrderLine");
    for(Element element1:list1){
        String qname1 = element1.getName();
        String name1 = element1.getText();
        System.out.println(qname1+"--"+name1);
        
                   List<Element> list2 = element1.elements();
    for(Element element2:list2){
        String qname2 = element2.getName();
        String name2 = element2.getText();
        System.out.println(qname2+"--"+name2);
        
                List<Element> list3 = element2.elements();
    for(Element element3:list3){
        String qname3 = element3.getName();
        String name3 = element3.attributeValue("originalProdQuantity");
        System.out.println(qname3+"--"+name3);
    }
    }
        
        
        
    }
    
    //List<org.dom4j.Node> list1 = document.selectNodes("OrderLines/SkuLines");

}
    
}
}
