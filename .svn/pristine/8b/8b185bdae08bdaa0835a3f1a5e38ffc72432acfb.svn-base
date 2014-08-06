package com.doteyplay.game.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;

import com.doteyplay.game.config.ServerConfig;

public class ProtobufCoder
{
	public static void toProtoFile(Class clz)
	{
		File file = new File(ServerConfig.PROTOBUF_PATH);
		if (file.exists())
		{
			try
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				
				bw.newLine();
				bw.write("message " + clz.getSimpleName() + "{") ;
				bw.newLine();
				
				int idx = 1;
				for (Field f : clz.getFields())
				{
					if (f.getType().isArray())
					{
						bw.write( "	repeated ");
						if (f.getType().getComponentType() == boolean.class)
							bw.write( "bool ");
						if (f.getType().getComponentType() == int.class)
							bw.write("int32 ");
						if (f.getType().getComponentType() == long.class)
							bw.write("int64 ");
						if (f.getType().getComponentType() == String.class)
							bw.write("string ");
					} else
					{
						bw.write("	required ");

						if (f.getType() == boolean.class)
							bw.write("bool ");
						if (f.getType() == int.class)
							bw.write("int32 ");
						if (f.getType() == long.class)
							bw.write("int64 ");
						if (f.getType() == String.class)
							bw.write("string ");
					}

					bw.write(f.getName() + " = " + idx + ";");
					bw.newLine();
					idx++;
				}

				bw.write("}");
				bw.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void toEncodeBuff(Class clz)
	{
		System.out.println("MessageProBuf." + clz.getSimpleName() + ".Builder "
				+ clz.getSimpleName().toLowerCase()
				+ "builder = MessageProBuf." + clz.getSimpleName()
				+ ".newBuilder();");

		for (Field f : clz.getFields())
		{
			if (f.getType().isArray())
			{
				System.out.println("for(int i = 0 ; i < "
						+ clz.getSimpleName().toLowerCase() + ".get"
						+ getFirstUpWord(f) + "().length; i ++)");
				System.out.println("{");
				System.out.println("	" + clz.getSimpleName().toLowerCase()
						+ "builder.get" + getFirstUpWord(f) + "List().add("
						+ clz.getSimpleName().toLowerCase() + ".get"
						+ getFirstUpWord(f) + "()[i]);");
				System.out.println("}");
			} else
			{
				if (f.getType() == boolean.class)
					System.out.println("" + clz.getSimpleName().toLowerCase()
							+ "builder.set" + getFirstUpWord(f) + "("
							+ clz.getSimpleName().toLowerCase() + ".is"
							+ getFirstUpWord(f) + "());");
				else
					System.out.println("" + clz.getSimpleName().toLowerCase()
							+ "builder.set" + getFirstUpWord(f) + "("
							+ clz.getSimpleName().toLowerCase() + ".get"
							+ getFirstUpWord(f) + "());");
			}
		}
	}

	private static String getFirstUpWord(Field f)
	{
		return f.getName().substring(0, 1).toUpperCase()
				+ f.getName().substring(1);
	}

	public static void main(String[] args)
	{
//		 toEncodeBuff(Card.class);
//		toProtoFile(Card.class);
	}
}
