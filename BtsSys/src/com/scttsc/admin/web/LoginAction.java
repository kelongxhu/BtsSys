package com.scttsc.admin.web;

import com.scttsc.admin.model.City;
import com.scttsc.admin.model.CityTreeNode;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.UserManager;
import com.scttsc.common.util.ConstantUtil;
import com.scttsc.common.util.MD5Util;
import com.scttsc.common.util.StringUtil;
import com.scttsc.common.web.BaseAction;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：用户登录验证
 */
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 1600015947443432912L;

    private UserManager userManager;

    private String account;

    private String password;

    private String validateCode;

    private String iUrl;

    private int isCheckPwd;

    private String errMsg;

    private String intId;// 传过来的用户ID

    public String checkPassword() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("userName", account);
            User user = userManager.login(map);
            if (user != null) {
                String pwdMd5 = MD5Util.encryptToMD5(password);
//                if (!user.getPassword().equals(pwdMd5)) {
//                    isCheckPwd = 1;// 验证密码
//                } else
                if (!this.validateValidateCode(validateCode)) {
                    isCheckPwd = 6;// 验证验证码
                } else {
                    isCheckPwd = 4;// 其它
                }
            } else {
                isCheckPwd = 5;// 用户不存在
            }
        } catch (Exception e) {
            e.printStackTrace();
            isCheckPwd = 0;
        }
        map.put("isCheckPwd", isCheckPwd);
        JSONObject jsonObject = JSONObject.fromObject(map);
        jsonString = jsonObject.toString();
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (!StringUtil.isEmpty(intId)) {
                map.put("intId", intId);
            } else {
                errMsg = "账号为空,请输入账号！";
                return ERROR;
            }
            User user = userManager.login(map);
            if (user != null) {
                City city = user.getCity();
                String cityId = city.getId() + "";
                CityTreeNode cityTree = ConstantUtil.buildCityTree(cityId);// 本地网
                CityTreeNode countryTree = ConstantUtil
                        .buildCountryTree(cityId);// 区县
                StringBuilder cityIdSb = new StringBuilder();// 本地网ID集合
                StringBuilder countryIdSb = new StringBuilder();// 区县ID集合
                initCityIds(cityTree, cityIdSb);
                initCityIds(countryTree, countryIdSb);
                cityIdSb.delete(cityIdSb.length() - 1, cityIdSb.length());
                countryIdSb.delete(countryIdSb.length() - 1, countryIdSb.length());
                user.setCityIds(cityIdSb.toString());
                user.setCountryIds(countryIdSb.toString());
                this.getSession().setAttribute("user", user);
                return SUCCESS;
            } else {
                errMsg = "该用户不存在！请联系管理员！";
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errMsg = "登陆异常!";
            return ERROR;
        }
    }


    public String login2() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            if (!StringUtil.isEmpty(account)) {
                map.put("userName", account);
            } else {
                errMsg = "账号为空,请输入账号！";
                return ERROR;
            }
            User user = userManager.login(map);
            if (user != null) {
                City city = user.getCity();
                String cityId = city.getId() + "";
                CityTreeNode cityTree = ConstantUtil.buildCityTree(cityId);// 本地网
                CityTreeNode countryTree = ConstantUtil
                        .buildCountryTree(cityId);// 区县
                StringBuilder cityIdSb = new StringBuilder();// 本地网ID集合
                StringBuilder countryIdSb = new StringBuilder();// 区县ID集合
                initCityIds(cityTree, cityIdSb);
                initCityIds(countryTree, countryIdSb);
                cityIdSb.delete(cityIdSb.length() - 1, cityIdSb.length());
                countryIdSb.delete(countryIdSb.length() - 1, countryIdSb.length());
                user.setCityIds(cityIdSb.toString());
                user.setCountryIds(countryIdSb.toString());
                this.getSession().setAttribute("user", user);
                return SUCCESS;
            } else {
                errMsg = "该用户不存在！请联系管理员！";
                return ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errMsg = "登陆异常!";
            return ERROR;
        }
    }

    /**
     * 修改密码页面
     *
     * @return
     */
    public String pwdEditPage() {
        return SUCCESS;
    }

    /**
     * @param validateCode
     * @return boolean 返回类型
     * @throws
     * @Title: login
     * @Description: TODO 验证 提交数据到数据库时输的验证码是否正确
     */
    public boolean validateValidateCode(String validateCode) {

        String validateCodeSaved = (String) this.getSession().getAttribute(
                "validateCode");
        this.getSession().removeAttribute("validateCode");

        if (validateCodeSaved != null) {
            return validateCodeSaved.equalsIgnoreCase(validateCode);
        } else {
            return false;
        }
    }

    public String loginOut() {
        User user = (User) this.getSession().getAttribute("user");
        this.getSession().removeAttribute("user");
        return SUCCESS;
    }

    /**
     * 初始化地市集合
     *
     * @param root
     * @param sb
     */
    @SuppressWarnings("rawtypes")
    private void initCityIds(CityTreeNode root, StringBuilder sb) {
        if (!root.isLeaf()) {
            List children = root.getChildren();
            for (int i = 0; i < children.size(); i++) {
                CityTreeNode node = (CityTreeNode) children.get(i);
                initCityIds(node, sb);
            }
        } else {
            sb.append(root.getId() + ",");
        }
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public int getIsCheckPwd() {
        return isCheckPwd;
    }

    public void setIsCheckPwd(int isCheckPwd) {
        this.isCheckPwd = isCheckPwd;
    }

    public String getIUrl() {
        return iUrl;
    }

    public void setIUrl(String url) {
        iUrl = url;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getIntId() {
        return intId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

}