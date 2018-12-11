package com.its.test.util.xml;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

class User {

	private String name;
	private String age;
	private String sex;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}

public class Dom4jTest<T> {
	
	/**
	 * DMO4J写入XML
	 * @param obj 泛型对象
	 * @param entityPropertys 泛型对象的List集合
	 * @param Encode XML自定义编码类型(推荐使用GBK)
	 * @param XMLPathAndName  XML文件的路径及文件名
	 */
	public void object2Xml(T obj, List<T> entityPropertys, String Encode, String XMLPathAndName) {
		try {
			long lasting = System.currentTimeMillis();// 效率检测
			XMLWriter writer = null;// 声明写XML的对象
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(Encode);// 设置XML文件的编码格式

			String filePath = XMLPathAndName;// 获得文件地址
			File file = new File(filePath);// 获得文件

			if (file.exists()) {
				file.delete();
			}
			// 新建user.xml文件并新增内容
			Document document = DocumentHelper.createDocument();
			String rootname = obj.getClass().getSimpleName();// 获得类名
			Element root = document.addElement(rootname + "s");// 添加根节点
			Field[] properties = obj.getClass().getDeclaredFields();// 获得实体类的所有属性

			for (T t : entityPropertys) { // 递归实体
				Element secondRoot = root.addElement(rootname); // 二级节点
				for (Field field : properties) {
					// 反射get方法
					String getStr = "get" + field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					Method meth = t.getClass().getMethod(getStr);
					// 为二级节点添加属性，属性值为对应属性的值
					secondRoot.addElement(field.getName()).setText(meth.invoke(t).toString());
				}
			}
			// 生成XML文件
			writer = new XMLWriter(new FileWriter(file), format);
			writer.write(document);
			writer.close();
			long lasting2 = System.currentTimeMillis();
			System.out.println("写入XML文件结束,用时" + (lasting2 - lasting) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("XML文件写入失败");
		}

	}
	
	/**
	 * DMO4J XML To Object
	 * @param XMLPathAndName XML文件的路径和地址
	 * @param t 泛型对象
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> xml2Object(String XMLPathAndName, T t) {
		long lasting = System.currentTimeMillis();// 效率检测
		List<T> list = new ArrayList<T>();// 创建list集合
		try {
			File f = new File(XMLPathAndName);// 读取文件
			SAXReader reader = new SAXReader();
			Document doc = reader.read(f);// dom4j读取
			Element root = doc.getRootElement();// 获得根节点
			Element foo;// 二级节点
			Field[] properties = t.getClass().getDeclaredFields();// 获得实例的属性
			// 实例的set方法
			Method setmeth;
			// 遍历 t.getClass().getSimpleName()节点
			for (Iterator i = root.elementIterator(t.getClass().getSimpleName()); i.hasNext();) {
				foo = (Element) i.next();// 下一个二级节点
				t = (T) t.getClass().newInstance();// 获得对象的新的实例

				for (Field field : properties) {// 遍历所有节点

					// 实例的set方法
					String setStr = "set" + field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					Class<?> type = field.getType();
					setmeth = t.getClass().getMethod(setStr, type);
					// properties[j].getType()为set方法入口参数的参数类型(Class类型)
					String name = field.getName();
					String value = foo.elementText(name);
					setmeth.invoke(t, value);
					// 将对应节点的值存入
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long lasting2 = System.currentTimeMillis();
		System.out.println("读取XML文件结束,用时" + (lasting2 - lasting) + "ms");
		return list;
	}

	public static void main(String[] args) {
		String path = "D:/user.xml";
		Dom4jTest<User> d = new Dom4jTest<User>();
		User bean = new User();
		bean.setName("Test");
		bean.setAge("20");
		bean.setSex("1");
		List<User> users = new ArrayList<User>();
		users.add(bean);
		users.add(bean);
		d.object2Xml(bean, users, "GBK", path);

		User user = new User();
		List<User> list = d.xml2Object(path, user);
		System.out.println("XML文件读取结果");
		for (int i = 0; i < list.size(); i++) {
			User usename = (User) list.get(i);
			System.out.println("name" + usename.getName());
			System.out.println("age" + usename.getAge());

		}
	}
}