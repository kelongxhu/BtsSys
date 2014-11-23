package com.scttsc.common.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil
{
  public static final String COLON = ":";
  public static final String COMMA = ",";
  public static final String EMPTY = "";
  public static final String UNDER_LINE = "_";
  public static final String ENDL = "\n";
  public static final String SLASH = "/";
  public static final String BLANK = " ";
  public static final String DOT = ".";
  public static final String FILE_SEPARATOR = File.separator;

  public static String encodePassword(String paramString)
  {
    String str = null;
    try
    {
      str = encodePassword(paramString, "MD5");
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return str;
  }

  public static String changeList2String(List<String> paramList)
  {
    String str = paramList.toString();
    return str.substring(1, str.length() - 1);
  }

  public static boolean isBlank(String paramString)
  {
    int i = 0;
    if ((paramString == null) || ("".equals(paramString.trim())))
      i = 1;
    if(i==1)
    {
    	return true;
    }else return false;
  }

	public static boolean isNotBlank(String paramString) {
		if (paramString != null && !"".equals(paramString)) {
			return true;
		}
		return false;
	}

  public static String encodePassword(String paramString1, String paramString2)
    throws NoSuchAlgorithmException
  {
    byte[] arrayOfByte1 = paramString1.getBytes();
    MessageDigest localMessageDigest = null;
    localMessageDigest = MessageDigest.getInstance(paramString2);
    localMessageDigest.reset();
    localMessageDigest.update(arrayOfByte1);
    byte[] arrayOfByte2 = localMessageDigest.digest();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfByte2.length; ++i)
    {
      if ((arrayOfByte2[i] & 0xFF) < 16)
        localStringBuffer.append("0");
      localStringBuffer.append(Long.toString(arrayOfByte2[i] & 0xFF, 16));
    }
    return localStringBuffer.toString();
  }

    public static boolean isEmpty(Object s){

		return s == null || s.toString().trim().length() == 0 || s.toString().trim().equalsIgnoreCase("null");

	}

  public static String base64Encode(String paramString)
  {
    BASE64Encoder localBASE64Encoder = new BASE64Encoder();
    return localBASE64Encoder.encodeBuffer(paramString.getBytes()).trim();
  }

  public static String base64Decode(String paramString)
  {
    BASE64Decoder localBASE64Decoder = new BASE64Decoder();
    try
    {
      return new String(localBASE64Decoder.decodeBuffer(paramString));
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.getMessage(), localIOException.getCause());
    }
  }

  public static String getRandomString(int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Random localRandom = new Random();
    for (int i = 0; i < paramInt; ++i)
    {
      int j;
      do
        if ((j = localRandom.nextInt(90)) > 64)
          break;
      while ((j = localRandom.nextInt(122)) <= 97);
      localStringBuffer.append((char)j);
    }
    return localStringBuffer.toString();
  }

  public static boolean contains(String[] paramArrayOfString, String paramString)
  {
    if (paramArrayOfString != null)
      for (int i = 0; i < paramArrayOfString.length; ++i)
        if (paramString.equals(paramArrayOfString[i]))
          return true;
    return false;
  }

  public static String ljustZero(String paramString, int paramInt)
  {
    String str = "";
    for (int i = 0; i < paramInt - paramString.length(); ++i)
      str = str + "0";
    str = str + paramString;
    return str;
  }

  public static int getWordLength(String paramString)
  {
    int i = 0;
    if (isBlank(paramString))
      return i;
    char[] arrayOfChar = paramString.toCharArray();
    for (int j = 0; j < arrayOfChar.length; ++j)
      if (isChinese(arrayOfChar[j]))
        i += 2;
      else
        i += 1;
    return i;
  }

  public static String getWord(String paramString, int paramInt)
  {
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = 0; j < arrayOfChar.length; ++j)
    {
      if (isChinese(arrayOfChar[j]))
        i += 2;
      else
        i += 1;
      if (i > paramInt)
        break;
      localStringBuffer.append(arrayOfChar[j]);
    }
    return localStringBuffer.toString();
  }

  public static boolean hasChinese(String paramString)
  {
    if (paramString == null)
      return true;
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; ++i)
      if (isChinese(arrayOfChar[i]))
        return true;
    return false;
  }

  public static boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    return ((localUnicodeBlock != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) && (localUnicodeBlock != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) && (localUnicodeBlock != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) && (localUnicodeBlock != Character.UnicodeBlock.GENERAL_PUNCTUATION) && (localUnicodeBlock != Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) && (localUnicodeBlock != Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS));
  }

  public static String[] splitIntoArr(String paramString)
  {
    if (isEmpty(paramString))
      return new String[0];
    return paramString.split(",");
  }

  public static String join(String[] paramArrayOfString)
  {
    return join(paramArrayOfString, ",", "'");
  }

  public static String join(String[] paramArrayOfString, String paramString)
  {
    return join(paramArrayOfString, ",", "");
  }

  public static String join(String[] paramArrayOfString, String paramString1, String paramString2)
  {
    String str = "";
    for (int i = 0; i < paramArrayOfString.length; ++i)
    {
      str = str + paramString2 + paramArrayOfString[i] + paramString2;
      if (i == paramArrayOfString.length - 1)
        continue;
      str = str + paramString1;
    }
    return str;
  }

  public static String convertCode(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      if (paramString1 == null)
        return null;
      if (paramString1.equals(""))
        return "";
      return new String(paramString1.getBytes(paramString2), paramString3);
    }
    catch (Exception localException)
    {
    	return localException.toString();
    }
    
  }

  public static boolean isUpperCase(String paramString)
  {
    int i = 1;
    for (int k = 0; k < paramString.length(); ++k)
    {
      int j = paramString.charAt(k);
      if ((j >= 65) && (j <= 90))
        continue;
      i = 0;
    }
    if(i==1)
    {
    	return true;
    }else return false;
  }

  public static boolean isLowerCase(String paramString)
  {
    int i = 1;
    for (int k = 0; k < paramString.length(); ++k)
    {
      int j = paramString.charAt(k);
      if ((j >= 97) && (j <= 122))
        continue;
      i = 0;
    }
    if(i==1)
    {
    	return true;
    }else return false;
  }

  	public static String null2String(Object s)
	{
		return s == null ? "" : s.toString().trim();
	}
    public static Integer null2Integer0(Object o)
	{
        try {
			if(o!=null){
			    String str=o.toString().trim();
			    return ("null".equalsIgnoreCase(str)||str.length()==0) ? Integer.valueOf(0) : Integer.valueOf(str);
			} else return Integer.valueOf(0) ;
		} catch (NumberFormatException e) {
			return  Integer.valueOf(0);
		}
	}
    public static Long null2Long0(Object o)
	{
        if(o!=null){
            String str=o.toString().trim();
            return ("null".equalsIgnoreCase(str)||str.length()==0) ? Long.valueOf(0) : Long.valueOf(str);
        } else return Long.valueOf(0) ;
	}
    
    public static double null2Double0(Object o)
	{
        if(o!=null){
            String str=o.toString().trim();
            return ("null".equalsIgnoreCase(str)||str.length()==0) ? Double.valueOf(0) : Double.valueOf(str);
        } else return Long.valueOf(0) ;
	}
    
    public static double round(double v,int scale)
    {
    	if(scale<0){
    	throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }

    	BigDecimal b = new BigDecimal(Double.toString(v));
    	BigDecimal one = new BigDecimal("1");
    	return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static final String replaceAll(String src, String fnd, String rep) {
		if (src == null || src.equals("")) {
			return "";
		}

		String dst = src;

		int idx = dst.indexOf(fnd);

		while (idx >= 0) {
			dst = dst.substring(0, idx) + rep
					+ dst.substring(idx + fnd.length(), dst.length());
			idx = dst.indexOf(fnd, idx + rep.length());
		}

		return dst;
	}
    
    /**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case ' ':
				sb.append("&nbsp;");
				break;
			case '\n':
				sb.append("<br/>");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * html转文本
	 * 
	 * @param htm
	 * @return
	 */
	public static String htm2txt(String htm) {
		if (htm == null) {
			return htm;
		}
		htm = htm.replace("&amp;", "&");
		htm = htm.replace("&lt;", "<");
		htm = htm.replace("&gt;", ">");
		htm = htm.replace("&quot;", "\"");
		htm = htm.replace("&nbsp;", " ");
		htm = htm.replace("<br/>", "\n");
		return htm;
	}
	
	
	
	
	//添加
	/**
	 * 字符串型变整形
	 */
	public static int parse2Int(String str){
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	/**
	 * 判断该字符串是否包含在字符串数组中
	 * @param src
	 * @param val
	 * @return
	 */
	public static boolean isContains(String[] src, String val){
		if(null == src || null == val){
			return false;
		}
		for(int i = 0; i < src.length; i++){
			if(val.equals(src[i])){
				return true;
			}
		}
		return false;
	}
}