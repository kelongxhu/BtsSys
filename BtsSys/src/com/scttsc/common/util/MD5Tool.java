package com.scttsc.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Tool { 
	
    //�÷�������������ַ�ͨ��md5���ܣ�����һ����ܺ���ַ� 
	public static String MD5Encrypt16(String inStr) { 
		MessageDigest md = null; 
		String outStr = null; 
		try { 
			md = MessageDigest.getInstance("MD5"); //����ѡ��������㷨��SHA 
			byte[] digest = md.digest(inStr.getBytes()); 
            //���ص���byet[]��Ҫת��ΪString�洢�ȽϷ��� 
			outStr = bytetoString(digest); 
		} 
		catch (NoSuchAlgorithmException nsae) { 
			nsae.printStackTrace(); 
		} 
		return outStr; 
	} 

	public static String bytetoString(byte[] digest) { 

		String str = ""; 
		String tempStr = ""; 
		for (int i = 1; i < digest.length; i++) { 
			tempStr = (Integer.toHexString(digest[i] & 0xff)); 
			if (tempStr.length() == 1) { 
				str = str + "0" + tempStr; 
			} 
			else { 
				str = str + tempStr; 
			} 
		} 
		return str.toLowerCase(); 

	} 
	
	//32λ����
	public static String MD5Encrypt(String values){
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(values.getBytes());
			byte b[] = md.digest();

			int i;			
			for (int offset = 0; offset <b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}
	
    //������5λ�����   
    public final static String get5Radom(){   
        String newString=null;   
  
        //�õ�0.0��1.0֮�������,��)��100000��   
        double doubleP=Math.random()*100000;   
  
        //�����ݵ���100000,�����1  
        if(doubleP>=100000){   
            doubleP=99999;   
        }   
  
        //Ȼ����������ת��Ϊ����С��������   
        int tempString=(int)Math.ceil(doubleP);   
  
        //ת��Ϊ�ַ�   
        newString=""+tempString;   
  
        //�ѵõ��������Ϊ�̶�����,Ϊ5λ   
        while(newString.length()<5){   
            newString="0"+newString;   
        }   
  
        return newString;   
    }   
  
    //��Ҫ�Ѵ��ݹ�4���ַ����ת��Ϊ����MD5�㷨���ܵ��ַ�   
    public final static String encrypeString(String neededEncrypedString) throws Exception{   
        //��ʼ������֮����ַ�   
        String encrypeString=null;   
  
        //16���Ƶ�����   
        char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};   
  
        //�ַ�ļ��ܹ��   
        try {   
            //����Ҫ���ܵ��ַ�ת��Ϊ�ֽ�����   
            byte[] neededEncrypedByteTemp=neededEncrypedString.getBytes();   
  
            //�õ�MD5�ļ����㷨����   
            MessageDigest md = MessageDigest.getInstance("MD5");   
  
            //�����㷨ʹ�õ�ժҪ   
            md.update(neededEncrypedByteTemp);   
  
            //����㷨���ܹ��   
            byte[] middleResult = md.digest();   
  
            //�Ѽ��ܺ���ֽ�����ת��Ϊ�ַ�   
            int length = middleResult.length;   
            char[] neededEncrypedByte = new char[length * 2];   
            int k = 0;   
            for (int i = 0; i < length; i++) {   
                byte byte0 = middleResult[i];   
                neededEncrypedByte[k++] = hexDigits[byte0 >>> 4 & 0xf];   
                neededEncrypedByte[k++] = hexDigits[byte0 & 0xf];   
            }   
            encrypeString = new String(neededEncrypedByte);   
        } catch (NoSuchAlgorithmException ex) {   
            throw new Exception(ex);   
        }   
  
        //���ؼ���֮����ַ�   
        return encrypeString;   
    }
    
    public final static String getMD5String() {
    	String md5 = "";
    	try {
			md5 = encrypeString(get5Radom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
    }
    
    //�̶���Կ����
    public static String HexEncode(String str) {   
        String hexString = null;   
        if (str != null && str.length() > 0) {   
            char[] digital = "0123456789ABCDEF".toCharArray();   
            StringBuffer sb = new StringBuffer("");   
            try {   
                byte[] bs = str.getBytes("utf-8");   
                int bit;   
                for (int i = 0; i < bs.length; i++) {   
                    bit = (bs[i] & 0x0f0) >> 4;   
                    sb.append(digital[bit]);   
                    bit = bs[i] & 0x0f;   
                    sb.append(digital[bit]);   
                }   
            } catch(Exception e) {   
            }   
            hexString = sb.toString();   
        }   
           
        return hexString;   
    }   
    
    //�̶���Կ����
    public static String HexDecode(String hexString) {   
        String str = null;   
        if (hexString != null && hexString.length() > 0) {   
            String digital = "0123456789ABCDEF";   
            char[] hex2char = hexString.toCharArray();   
            byte[] bytes = new byte[hexString.length() / 2];   
            int temp;   
            for (int i = 0; i < bytes.length; i++) {   
                temp = digital.indexOf(hex2char[2 * i]) * 16;   
                temp += digital.indexOf(hex2char[2 * i + 1]);   
                bytes[i] = (byte)(temp & 0xff);   
            }   
            try {   
                str = new String(bytes, "utf-8");   
            } catch (Exception e) {   
            }   
        }   
        return str;   
    }  
    
	public static void main(String []args){ 
		String test="1"; 

		//����һ��MD5Tool�� 
		MD5Tool myMd5 =new MD5Tool(); 
		String result=myMd5.MD5Encrypt(test); 
		System.out.println (test+" :"+result); 
		
//		String s1 = HexEncode("root");
//		String s2 = HexDecode(s1);
//		System.out.println (s1 + "/" + s2); 
//		
//		String s3 = HexEncode("123456");
//		String s4 = HexDecode(s3);
//		System.out.println (s3 + "/" + s4); 
		
		System.out.println("==="+HexDecode("c4ca4238a0b923820dcc509a6f75849b"));
	} 
} 
