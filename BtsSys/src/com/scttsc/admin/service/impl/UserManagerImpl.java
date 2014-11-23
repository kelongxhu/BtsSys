package com.scttsc.admin.service.impl;

import com.scttsc.admin.dao.UserDao;
import com.scttsc.admin.model.City;
import com.scttsc.admin.model.CityTreeNode;
import com.scttsc.admin.model.User;
import com.scttsc.admin.service.UserManager;
import com.scttsc.common.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userManager")
public class UserManagerImpl implements
        UserManager {
    @Autowired
    private UserDao userDao;

    public User login(Object map) throws Exception {
        return userDao.login(map);
    }

    public User getByPriKey(Long id) throws Exception {
        return userDao.getByPriKey(id);
    }

    public User selectByImsi(String imsi) throws Exception {
        List<User> userList = userDao.selectByImsi(imsi);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    public User strongUser(User user) throws Exception {
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
        return user;
    }

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
}
