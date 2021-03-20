package top.b0x0.demo.reflect.main;
 
import java.lang.reflect.Method;
 
/**
 * 获取成员方法并调用：
 * 
 * 1.批量的：
 * 		public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
 * 		public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
 * 2.获取单个的：
 * 		public Method getMethod(String name,Class<?>... parameterTypes):
 * 					参数：
 * 						name : 方法名；
 * 						Class ... : 形参的Class类型对象
 * 		public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
 * 
 * 	 调用方法：
 * 		Method --> public Object invoke(Object obj,Object... args):
 * 					参数说明：
 * 					obj : 要调用方法的对象；
 * 					args:调用方式时所传递的实参；
 * @author musui
 */
public class ReflectToGetMethod {
 
	public static void main(String[] args) throws Exception {
		//1.获取Class对象
		Class stuClass = Class.forName("top.b0x0.demo.reflect.domain.Student");
		//2.获取所有公有方法
		System.out.println("***************获取所有的”公有“方法*******************");
		stuClass.getMethods();
		Method[] methodArray = stuClass.getMethods();
		for(Method m : methodArray){
			System.out.println(m);
		}

		System.out.println("\n***************获取所有的方法，包括私有的*******************");
		methodArray = stuClass.getDeclaredMethods();
		for(Method m : methodArray){
			System.out.println(m);
		}

		System.out.println("\n***************获取公有的publicShow()方法*******************");
		Method method = stuClass.getMethod("publicShow", String.class);
		System.out.println(method);
		//实例化一个Student对象
		Object obj = stuClass.getConstructor().newInstance();
		method.invoke(obj, "刘德华");
		
		System.out.println("\n***************获取私有的privateShow()方法******************");
		method = stuClass.getDeclaredMethod("privateShow", int.class);
		System.out.println(method);
		method.setAccessible(true);
		//调用方法: 需要两个参数，一个是要调用的对象（获取有反射），一个是实参
		Object result = method.invoke(obj, 20);
		System.out.println("返回值：" + result);
		
		
	}
}