package com.mateusfrz.arystaaddons.utils;

public class StringUtils {

	public static String join(Object[] objects, int indexToStart, String separator) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = indexToStart; i < objects.length; i++) {
			if(i != indexToStart) {
				builder.append(separator);
			}
			builder.append(objects[i]);
		}
		
		return builder.toString();
	}
	
}
