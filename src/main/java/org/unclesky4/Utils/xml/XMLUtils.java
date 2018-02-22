package org.unclesky4.Utils.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.experimental.theories.Theories;


/**
* 操作XML文件
* @author unclesky4
* @date Feb 21, 2018 8:19:05 PM
*

DOM4J定义了几个Java类。以下是最常见的类：

    Document - 表示整个XML文档。文档Document对象是通常被称为DOM树。
    Element - 表示一个XML元素。 Element对象有方法来操作其子元素，它的文本，属性和名称空间。
    Attribute - 表示元素的属性。属性有方法来获取和设置属性的值。它有父节点和属性类型。
    Node - 代表元素，属性或处理指令

常见DOM4J的方法

当使用DOM4J，还有经常用到的几种方法：

    SAXReader.read(xmlSource)() - 构建XML源的DOM4J文档。
    Document.getRootElement() - 得到的XML的根元素。
    Element.node(index) - 获得在元素特定索引XML节点。
    Element.attributes() - 获取一个元素的所有属性。
    Node.valueOf(@Name) - 得到元件的给定名称的属性的值。

*/

public class XMLUtils {

	/**
	 * 构建XML源的DOM4J文档
	 * @param url
	 * @return
	 * @throws DocumentException
	 */
	public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }
	
	/**
	 * 构建XML源的DOM4J文档
	 * @param file
	 * @return
	 * @throws DocumentException
	 */
	public Document parse(File file) throws DocumentException {
		SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
	}
	
	/**
	 * 获取某个节点的迭代器
	 * @param file
	 * @param nodeName
	 * @return
	 * @throws DocumentException
	 */
	public Iterator<Element> nodeElement(File file, String nodeName) throws DocumentException {
		SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Iterator<Element> iterator = document.getRootElement().elementIterator(nodeName);
        return iterator;
	}
	
	/**
	 * 使用标准Java迭代器导航文档
	 * @param document
	 * @throws DocumentException
	 */
	/*
	public void bar(Document document) throws DocumentException {

	    Element root = document.getRootElement();  //根结点
	    //System.out.println(root.elementIterator().next().getName()+"  "+root.content().size());

	    // 获取XML根结点
	    for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
	        Element element = it.next();  //第二层节点
	        
	        // 获取属性名以及属性值  --- 属性名：id  属性值：002
	        List<Attribute> attributes = element.attributes();
	        for (Attribute attr : attributes) {
                System.out.println("属性名：" + attr.getName() + "  属性值："
                        + attr.getValue());
            }
	        
	        //获取第三层的节点和节点值
	        List<Element> elements = element.elements();
	        for (Element element2 : elements) {
	        	//System.out.println("element2.content().size():"+element2.content().size());
	        	if (element2.content().size()<2) {
	        		System.out.println("节点名：" + element2.getName() + "  节点值：" 
		                    + element2.getStringValue());
				}
			}
	    }
	 }
	 */
	
	/** 
     * 遍历当前节点元素下面的所有(元素的)子节点 
     *  
     * @param node 
     */  
    public void listNodes(Element node) {  
        System.out.println("当前节点的名称：：" + node.getName());  
        // 获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        // 遍历属性节点  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  

        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("文本内容：：：：" + node.getText());  
        }  

        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            listNodes(e);  
        }  
    }
    
   /**
    * 把document对象写入新的文件 
    * @param document
    * @param file - 写入的xml文件路径
    * @throws Exception
    */
    public void writer(Document document,File file) throws Exception {  
        // 紧凑的格式  
        // OutputFormat format = OutputFormat.createCompactFormat();  
        // 排版缩进的格式  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(file), "UTF-8"), format);  
        // 写入  
        writer.write(document);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
    }  
    
    /**
     * 创建一个写xml实例
     * @throws Exception
     */
    public void createXMLDemo() throws Exception {  
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "cars" );
        Element supercarElement= root.addElement("supercars")
           .addAttribute("company", "Ferrai");

        supercarElement.addElement("carname")
           .addAttribute("type", "Ferrari 101")
           .addText("Ferrari 101");

        supercarElement.addElement("carname")
           .addAttribute("type", "sports")
           .addText("Ferrari 202");
        // 写入到一个新的文件中 
        writer(document, new File("/home/uncle/Desktop/aaaaaaaaaaaaaa.xml"));
   }

	
	public static void main(String[] args) throws Exception {
		String path = "/home/uncle/Desktop/a.xml";
		File file = new File(path);
		if (!file.exists()) {
			Logger.getLogger("a").error("xml文件不存在："+path);
		}
		
		XMLUtils xmlUtils = new XMLUtils();
		Document document = xmlUtils.parse(file);
		
		//xmlUtils.bar(document);
		xmlUtils.listNodes(document.getRootElement());
		xmlUtils.createXMLDemo();
	}
}
