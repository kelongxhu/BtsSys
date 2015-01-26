package com.scttsc.business.service.impl;

import com.scttsc.baselibs.model.Cons;
import com.scttsc.business.dao.*;
import com.scttsc.business.model.WyColumneditInterface;
import com.scttsc.business.service.ColumneditInterfaceManager;
import com.scttsc.common.util.Common;
import com.scttsc.common.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 14-1-2
 * Time: 下午3:35
 */
@Service("columneditInterfaceManager")
public class ColumneditInterfaceManagerImpl implements ColumneditInterfaceManager {
    @Autowired
    private WyColumneditInterfaceDao wyColumneditInterfaceDao;
    @Autowired
    private BtsManualDao btsManualDao;
    @Autowired
    private BbuManualDao bbuManualDao;
    @Autowired
    private IndoorManualDao indoorManualDao;
    @Autowired
    private CellManualDao cellManualDao;


    public int insert(WyColumneditInterface record) throws Exception {
        return wyColumneditInterfaceDao.insert(record);
    }

    public int updateByPrimaryKeySelective(WyColumneditInterface record) throws Exception {
        return wyColumneditInterfaceDao.updateByPrimaryKeySelective(record);
    }

    public List<WyColumneditInterface> selectByMap(Map record) throws Exception {
        return wyColumneditInterfaceDao.selectByMap(record);
    }

    public int countByMap(Map record) throws Exception {
        return wyColumneditInterfaceDao.countByMap(record);
    }

    public WyColumneditInterface selectByPrimaryKey(Long id) throws Exception {
        return wyColumneditInterfaceDao.selectByPrimaryKey(id);
    }

    /**
     * 接口修改审核
     *
     * @param ids
     * @param status，1=审核通过；2=审核不通过
     * @throws Exception
     */
    public void wyColumneditInterfaceEditOpposed(String ids, int status, Long confirmUserId) throws Exception {
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            if (status == 1) {
                //审核通过
                wyColumneditInterfaceSucess(new Long(id));
            }
            //更改状态
            WyColumneditInterface wyColumneditInterface2 = new WyColumneditInterface();
            wyColumneditInterface2.setId(new Long(id));
            wyColumneditInterface2.setStatus(status);
            wyColumneditInterface2.setConfirmuserid(confirmUserId);
            wyColumneditInterface2.setUpdatetime(new Date());
            wyColumneditInterfaceDao.updateByPrimaryKeySelective(wyColumneditInterface2);
        }
    }

    private void wyColumneditInterfaceSucess(Long id) throws Exception {
        WyColumneditInterface wyColumneditInterface = wyColumneditInterfaceDao.selectByPrimaryKey(id);
        if (wyColumneditInterface != null) {
            Map updateMap = new HashMap();
            String encolumnname = wyColumneditInterface.getEncolumnname();
            String columnvalue = wyColumneditInterface.getColumnvalue();
            if (!Common.isEmpty(encolumnname)) {
                String[] encolumnnameArr = encolumnname.split(",");
                String[] columnvalueArr = columnvalue.split(",");
                if (encolumnnameArr != null) {
                    for (int i = 0; i < encolumnnameArr.length; i++) {
                        //特殊处理项
                        if ("MR_STRUT_NAME".equals(encolumnnameArr[i])) {
                            //机房结构
                            Cons con = ConstantUtil.getInstance().getCons("MRSTRUT", columnvalueArr[i]);
                            if (con == null) {
                                throw new Exception("");
                            }
                            updateMap.put("MR_STRUT", con.getCode());
                        } else if("INSTALL_POS_CODE_NAME".equals(encolumnnameArr[i])){
                            //主设备安装位置
                            Cons con = ConstantUtil.getInstance().getCons("BTSINSTALL", columnvalueArr[i]);
                            if (con == null) {
                                throw new Exception("");
                            }
                            updateMap.put("INSTALL_POS_CODE", con.getCode());

                        } else if("TOWER_TYPE_NAME".equals(encolumnnameArr[i])){
                            //塔跪类型
                            Cons con = ConstantUtil.getInstance().getCons("TOWERTYPE", columnvalueArr[i]);
                            if (con == null) {
                                throw new Exception("");
                            }
                            updateMap.put("TOWER_TYPE", con.getCode());
                        }else{
                            //正常数据
                            updateMap.put(encolumnnameArr[i], columnvalueArr[i]);

                        }
                    }
                }
            }
            updateMap.put("INT_ID", wyColumneditInterface.getIntId());
            int type = wyColumneditInterface.getType();
            switch (type) {
                case 1:
                    btsManualDao.updateByInterface(updateMap);
                    break;
                case 2:
                    bbuManualDao.updateByInterface(updateMap);
                    break;
                case 3:
                    indoorManualDao.updateByInterface(updateMap);
                    break;
                case 4:
                    cellManualDao.updateByInterface(updateMap);
                    break;
            }
        }
    }
}

