package com.scttsc.business.service.impl;

import java.util.*;

import com.scttsc.business.model.Bbu;
import com.scttsc.business.model.MateEntry;
import com.scttsc.business.service.WyLogManager;
import com.scttsc.business.util.DateConverter;
import com.scttsc.business.vo.FindBackReponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scttsc.business.dao.BbuDao;
import com.scttsc.business.dao.BbuManualDao;
import com.scttsc.business.model.BbuManual;
import com.scttsc.business.model.BtsManual;
import com.scttsc.business.service.BbuManualManager;
import com.scttsc.common.util.StringUtil;

@Service("bbuManualManager")
@Transactional(readOnly = true)
public class BbuManualManagerImpl implements BbuManualManager {

    public static Logger LOG = Logger.getLogger(BbuManualManagerImpl.class);
	@Autowired
	private BbuManualDao bbuManualDao;
	@Autowired
	private BbuDao bbuDao;
    @Autowired
    private WyLogManager wyLogManager;

	public BbuManual getById(Long intId) throws Exception {
		return bbuManualDao.getById(intId);
	}

	public int insert(BbuManual record) throws Exception {
        //insert数据
		int j = bbuManualDao.insert(record);
		// 手工标识置为1
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("manualFlag", 1);
		map.put("intId", record.getIntId());
		bbuDao.updateByMap(map);

        //切入日志
        wyLogManager.insertLog(record);

		return j;
	}

    /**
     * 更改bbu手工数据
     * @param record
     * @return
     * @throws Exception
     */

	public int update(BbuManual record) throws Exception {
        //插入更新日志
        wyLogManager.updateLog(record);
        //更新操作
		return bbuManualDao.update(record);
	}

	/**
	 * 导入BBU数据
	 */
	public void importInsert(Map record) throws Exception {
		Long intId=StringUtil.null2Long0(record.get("intId"));
        BbuManual bbuManual=new BbuManual();
        ConvertUtilsBean convertUtils = new ConvertUtilsBean();
        DateConverter dateConverter = new DateConverter();
        String[] datePattern = { "yyyy-mm-dd", "yyyy-mm"};
        dateConverter.setPatterns(datePattern);
        convertUtils.register(dateConverter,Date.class);
        BeanUtils.populate(bbuManual, record);
        bbuManual.setUpdatetime(new Date());
		BbuManual obj=getById(intId);
		if(obj==null){
			//插入
            insert(bbuManual);
		}else{
			//覆盖更新
			update(bbuManual);
			
		}
	}

    /**
     * 接口修改bbu字段数据
     * @param record
     * @return
     * @throws Exception
     */
    public int updateByInterface(Map record) throws Exception {
        return bbuManualDao.updateByInterface(record);
    }



    public List<MateEntry> findDelBbuData(List<Long> ids) throws Exception {
        List<MateEntry> mateEntries = new ArrayList<MateEntry>();
        for (Long delIntId : ids) {
            Bbu bbuDel = bbuDao.getById(delIntId);
            if (bbuDel == null) {
                continue;
            }
            //同名再用纯BBU
            Map map = new HashMap();
            map.put("name", bbuDel.getName());
            map.put("deleteFlag", 0);
            map.put("isShare", 0);
            map.put("bbuType", "1,2");//纯BBU数据
            List<Bbu> bbuList = bbuDao.selectByMap(map);//在用bts
            Bbu bbu = null;
            if (bbuList != null && bbuList.size() > 0) {
                bbu = bbuList.get(0);
            }
            if (bbu != null&&bbu.getManualFlag().intValue()==0) {
                MateEntry mateEntry = new MateEntry();
                mateEntry.setDel_IntId(bbuDel.getIntId().toString());
                mateEntry.setDel_BscName(bbuDel.getBscName());
                mateEntry.setDel_Name(bbuDel.getName());
                mateEntry.setDel_BtsId(bbuDel.getBtsId().intValue());
                mateEntry.setDel_ManualFlag(bbuDel.getManualFlag().intValue());
                mateEntry.setIntId(bbu.getIntId().toString());
                mateEntry.setBscName(bbu.getBscName());
                mateEntry.setName(bbu.getName());
                mateEntry.setBtsId(bbu.getBtsId().intValue());
                mateEntry.setManualFlag(bbu.getManualFlag().intValue());
                mateEntries.add(mateEntry);
            }
        }
        return mateEntries;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public FindBackReponse findManualData(List<String> ids) {
        FindBackReponse reponse = new FindBackReponse();
        int sucess = 0;
        try {
            for (String intIdObj : ids) {
                String[] pair = intIdObj.split(",");
                BbuManual bbuManual = bbuManualDao.getById(new Long(pair[0]));//废弃数据的手工数据
                //找回原则：废弃存在手工数据，当前再用不存在手工数据
                if (bbuManual != null) {
                    Long intId= new Long(pair[1]);//再用intId
                    BbuManual bbuManual1=bbuManualDao.getById(intId);
                    if(bbuManual1!=null){
                        bbuManualDao.deleteByIntId(intId);
                    }
                    //将废弃数据手工数据关联到现在的数据INT_ID
                    Map map = new HashMap();
                    map.put("newIntId", new Long(pair[1]));
                    map.put("intId", bbuManual.getIntId());
                    bbuManualDao.updateByIntID(map);
                    //如果现在有数据，删除现在的，避免删除失败
                    //将现在的数据INT_ID状态更新为已录入
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("manualFlag", 1);
                    map2.put("intId", new Long(pair[1]));
                    bbuDao.updateByMap(map2);
                    //将废弃数据INT_ID状态更新未未录入
                    Map<String, Object> map3 = new HashMap<String, Object>();
                    map3.put("manualFlag", 0);
                    map3.put("intId", new Long(pair[0]));
                    bbuDao.updateByMap(map3);
                    sucess++;
                }
            }
            reponse.setResult("Y");
            reponse.setSucessNum(sucess);
        } catch (Exception e) {
            reponse.setSucessNum(sucess);
            reponse.setResult("N");
            LOG.error(e.getMessage(), e);
        }
        return reponse;
    }


}
