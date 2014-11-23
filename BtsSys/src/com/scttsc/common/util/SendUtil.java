package com.scttsc.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class SendUtil {
	public static Map<String, Object> splitSender(String userIds,String userInfo){
		String group = "",depts = "",cUsers = "",pUsers = "",others = "";
		userIds = StringUtil.replaceAll(userIds, "，", ",");
		userInfo = StringUtil.replaceAll(userInfo, "，", ",");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(userIds)){
			for(String str:userIds.split(",")){
				if(str.indexOf("00003:")!=-1){
					group +="," + str;
					//如果是分组，表示是个人通讯录
				}
				if(str.indexOf("00001:")!=-1){
					depts +="," + str;
					//如果是机构
				}
				if(str.indexOf("00002:")!=-1){
					cUsers +="," + str;
					//如果是公司通讯录
				}	
				if(str.indexOf("00004:")!=-1){
					pUsers +="," + str;
					//如果是个人通讯录
				}	
			}
		}
		if(StringUtils.isNotEmpty(userInfo)){
			for(String str:userInfo.split(",")){
				if(str.indexOf("[")==-1 && str.indexOf("]")==-1 && str.indexOf("_")==-1){
					others +="," + str;
					//如果是手动输入的手机
				}
			}
		}
		if(group.startsWith(",")) group = group.substring(1,group.length());
		if(depts.startsWith(",")) depts = depts.substring(1,depts.length());
		if(cUsers.startsWith(",")) cUsers = cUsers.substring(1,cUsers.length());
		if(pUsers.startsWith(",")) pUsers = pUsers.substring(1,pUsers.length());
		if(others.startsWith(",")) others = others.substring(1,others.length());
		group = StringUtil.replaceAll(group, "00003:", "");
		group = StringUtil.replaceAll(group, "个人通讯录,","");
		group = StringUtil.replaceAll(group, "个人通讯录","");
		depts = StringUtil.replaceAll(depts, "00001:", "");
		cUsers = StringUtil.replaceAll(cUsers, "00002:", "");
		pUsers = StringUtil.replaceAll(pUsers, "00004:", "");
		map.put("group", StringUtils.isEmpty(group)?null:group);
		map.put("depts", StringUtils.isEmpty(depts)?null:depts);
		map.put("cUsers", StringUtils.isEmpty(cUsers)?null:cUsers);
		map.put("pUsers", StringUtils.isEmpty(pUsers)?null:pUsers);
		map.put("others", StringUtils.isEmpty(others)?null:others);
		return map;
	}
	
	public static Map<String, Object> checkContents(String contents,String filter){
    	String[] filters = filter.split(",");
    	Pattern p = null;
    	Matcher matche = null;
    	Map<String, Object> map = new HashMap<String, Object>();
    	for(int i = 0 ; i < filters.length ; i ++){
    		p=Pattern.compile(filters[i]);
    		matche = p.matcher(contents);
    		if (matche.find()) {
    			map.put("filterC", matche.group(0));
                return map;
            }
    	}
    	return map;
    }
	
	public static int getStrLen(String fromStr){
		int FromLen = fromStr.length();
		int ChineseLen = 0;
		for (int i = 0; i < FromLen; i++)
		{
			if (gbValue(fromStr.charAt(i)) > 0) {
				ChineseLen = ChineseLen + 2;
			} else {
				ChineseLen++;
			}
		}
		return ChineseLen;
	}
	
	public static int gbValue(char ch){
		String str = new String();
		str += ch;
		try
		{
			byte[] bytes = str.getBytes("GBK");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		}
		catch (Exception e)
		{
			return 0;
		}
	}  
	
	/**
	 * 
	 * checkPhone: 检查号码是否正确
	 *
	 * @param  @param phone
	 * @param  @return
	 * @return boolean
	 * @throws
	 */
    public static boolean checkPhone(String phone){
       
//    	Pattern pattern = Pattern.compile("^(1)+([0-9]{2})+([0-9]{4})+([0-9]{4})+");
    	Pattern pattern = Pattern.compile("^1(3[3]|5[3]|8[09])\\d{8}$");

        Matcher matcher = pattern.matcher(phone);
        
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
