package pome.waila.bcaddon.reflection;

import java.lang.reflect.Method;

public class ReflectionHelper
{
	public static Method getMethod(Class clazz,String devName,String obfName,Class... paramTypes)
	{
		Method m = null;
		try
		{
			m = clazz.getDeclaredMethod(devName, paramTypes);
		}
		catch(Exception e)
		{
			try
			{
				m = clazz.getMethod(obfName, paramTypes);
			}
			catch(Exception ex){}
		}
		return m;
	}
}
