package cn.sunline.tiny.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.sun.tools.javac.util.List;

public class IsNull {
	public static boolean isNull(Object o) {
		  if (o == null) {
		   return true;
		  }

		  if (o instanceof String) {
		   String str = o.toString();
		   
		   //暂时去掉null 和 undefined  存在直接传递null字符串的情况 Temporarily remove null and undefined. There is a case where a null string is passed directly.
		   if (str == null || "".equals(str) /*|| str == "null" || str == "undefined"*/) {
		    return true;
		   }
		  }else if (o instanceof JSONArray) {
		   return ((JSONArray)o).size() ==0;
		  }else if (o instanceof List) {
		   return ((List)o).size() ==0;
		  }

		  return false;
		 }
}
