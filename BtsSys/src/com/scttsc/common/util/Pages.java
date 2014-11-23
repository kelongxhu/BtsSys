package com.scttsc.common.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class Pages {

  String filename = ""; //文件名
  int page = 1; //页号
  long totals = -1; //记录总数
  int perpagenum =10; //每页显示记录数
  int style = 3; //分页字串样式
  int allpage = 1; //总页数
  int cpage = 1; //当前页
  int spage = 1; //开始记录数
  String listPageBreak = "";
  String[] pagesign = null;

  public Pages() {
  }
  

  public Pages(int page, int totals, int perpagenum,int style) {
    this.page = page;
    this.totals = totals;
    this.perpagenum = perpagenum;
    this.style = style;
  }

  public Pages(int page, int totals, int perpagenum) {
    this.page = page;
    this.totals = totals;
    this.perpagenum = perpagenum;
  }

  public Pages(int page, int perpagenum) {
    this.page = page;
    this.perpagenum = perpagenum;
  }

  public String getFileName() {
    return this.filename;
  }

  public void setFileName(String aFileName) {
    this.filename = aFileName;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int aPage) {
    this.page = aPage;
  }

  public long getTotals() {
    return this.totals;
  }

  public void setTotals(long aTotals) {
    this.totals = aTotals;
  }

  public int getPerPageNum() {
    return this.perpagenum;
  }

  public void setPerPageNum(int aperpagenum) {
    this.perpagenum = aperpagenum;
  }

  public int getStyle() {
    return this.style;
  }

  public void setStyle(int aStyle) {
    this.style = aStyle;
  }

  public void setPagesign(String[] apagesign) {
    this.pagesign = apagesign;
  }

  public int getSpage() {
    return this.spage;
  }

  public void doPageBreak() {
    this.allpage = (int) Math.ceil( (this.totals + this.perpagenum - 1) / this.perpagenum);
    if(page<1)page=1;
    int intPage = this.page;
    if (intPage > this.allpage) {
      this.cpage = 1;
    }
    else {
      this.cpage = intPage;
    }
    this.spage = (this.cpage - 1) * this.perpagenum;
    getPageBreakStr();
  }

  public String getListPageBreak() {
    return this.listPageBreak;
  }
  

//取得url参数
  public static String getUrlParam(HttpServletRequest request)
  {
	  String strFormMethod = request.getMethod();
	  String strFormGetName = "";
	  StringBuffer tt=new StringBuffer();
	  try
	  {
		  Enumeration enu = request.getParameterNames();
		  tt.append(request.getRequestURL());
		  tt.append("?query");
		  while(enu.hasMoreElements())
		  {
			  String urlParaStr = (String)enu.nextElement();
			  if (!urlParaStr.equals("page")) {
				  strFormGetName = request.getParameter(urlParaStr);
				  //if(strFormMethod.equals("GET")){
					//  strFormGetName = Common.decodeURL(strFormGetName,"GBK");						  
				  //}
				  //strFormGetName = Common.encodeURL(strFormGetName,"GBK");
				  tt.append("&" + urlParaStr + "=" + strFormGetName);
			  }
		  }			  
	  }
	  catch(Exception ex)
	  {
		System.out.println(" error:"+ex.getMessage());
	  }
	  return tt.toString();
  }
  
  private void getPageBreakStr() {

    if (this.filename.indexOf("?") == -1 &&
        (this.filename.endsWith(".do"))) {
      this.filename = this.filename + "?";
    }
    else {
      if (!this.filename.endsWith("&")) {
        this.filename = this.filename + "&";
      }
    }

    StringBuffer sb = new StringBuffer();
    
    /**
     * 类似如[ 1 2 3 4 5 6 7 8 9 10 ]的分页类
     * @param strQueryChar String 请求的页面名称和参数
     * @param iPage int 当前 Page 页值
     * @param iPageSize int Page 大小
     * @param intRsCount 总信息量
     * @param intPageCount 总分页数
     * @param strStyleCss 链接样式
     * @return 字符串 sb
     */
    if (this.style==1) {
        if(this.page>this.allpage){
             page=allpage;
        }
        int aa=page-5;
        int bb=page+4;
        if(aa<=0)
        {
          aa=1;
          bb=10;
        }
        if((page>allpage-4)&&(allpage>9))
        {
        	aa=allpage-9;
        	bb=allpage;
        }
        sb.append("<span id='pageList' name='pageList'>");
        if (totals>perpagenum){
        	sb.append("共" + totals + "条&nbsp;" + page + "/" + allpage + "页&nbsp;&nbsp;&nbsp;&nbsp;");
        	
            if (page > 1) {
            	sb.append("<a href='" + filename + "page=1" 
                        + "'><img src='/"+SystemConfiguration.getString("app.name")+"/images/left_first_act.gif' align='absbottom' border=0 /></a>");
            	sb.append("<a href='" + filename + "page=" +
                            (page - 1) + "'><img src='/"+SystemConfiguration.getString("app.name")+"/images/pre_page_simple_act.gif' align='absbottom' border=0 /></a>&nbsp;");
            }
            else {
            	sb.append("<img src='/"+SystemConfiguration.getString("app.name")+"/images/left_first.gif' align='absbottom' border=0 />&nbsp;");
            	sb.append("<img src='/"+SystemConfiguration.getString("app.name")+"/images/pre_page_simple.gif' align='absbottom' border=0 />&nbsp;");
            }
            int i = 0;
            for (i = aa; i <= bb; i++) {
                if (i == page) {
                    sb.append("<font color='#ff0000'>" + i + "</font>");
                    sb.append("&nbsp;");
                }
                else {
                    sb.append("<a href='" + filename + "page=" + i +
                               "'>" + i + "</a>&nbsp;");
                }
                if (i == allpage) {
                    i++;
                    break;
                }
            }
            if (page < allpage) {
                sb.append("<a href='" + filename + "page=" + (page + 1) +
                           "'><img src='/"+SystemConfiguration.getString("app.name")+"/images/next_page_simple_act.gif' align='absbottom' border=0 /></a>&nbsp;");               
                sb.append("<a href='" + filename + "page="+allpage
                        + "'><img src='/"+SystemConfiguration.getString("app.name")+"/images/right_end_act.gif' align='absbottom' border=0 /></a>");
            }
            else {
            	sb.append("<img src='/"+SystemConfiguration.getString("app.name")+"/images/next_page_simple.gif' align='absbottom' border=0 />&nbsp;");
            	sb.append("<img src='/"+SystemConfiguration.getString("app.name")+"/images/right_end.gif' align='absbottom' border=0 />&nbsp;");
            }
            
        }else{
        	sb.append("共" + totals + "条");
        }
        sb.append("</span>");
        this.listPageBreak = sb.toString();
        return;
    }
    
    if (this.style==2) {
        if(this.page>this.allpage){
             page=allpage;
        }
        int aa=page-5;
        int bb=page+4;
        if(aa<=0)
        {
          aa=1;
          bb=10;
        }
        sb.append("<span id='pageList' name='pageList'>");
        if (totals>perpagenum){
        	sb.append("共" + totals + "条&nbsp;" + page + "/" + allpage + "页&nbsp;&nbsp;");
            if (page > 1) {
            	sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"&",""),"{$page}",(page - 1)+"") + "'><img src='/images/pre_page_simple_act.gif' align='absbottom' border=0 /></a>&nbsp;");
            }
            else {
            	sb.append("<img src='/images/pre_page_simple.gif' align='absbottom' border=0 />&nbsp;");
            }
            int i = 0;
            for (i = aa; i <= bb; i++) {
                if (i == page) {
                    sb.append("<font color='#ff0000'>" + i + "</font>");
                    sb.append("&nbsp;");
                }
                else {
                    sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"&",""),"{$page}",i+"") + "'>" + i + "</a>&nbsp;");
                }
                if (i == allpage) {
                    i++;
                    break;
                }
            }
            if (page < allpage) {
                sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"&",""),"{$page}",(page + 1)+"") + "'><img src='/images/next_page_act.gif' align='absbottom' border=0 /></a>&nbsp;");               
            }
            else {
            	sb.append("<img src='/images/next_page.gif' align='absbottom' border=0 />");
            }           
        }else{
        	sb.append("共" + totals + "条");
        }
        sb.append("</span>");
        this.listPageBreak = sb.toString();
        return;
    }
    
    if (this.style==3) {
        if(this.page>this.allpage){
             page=allpage;
        }
        int aa=page-5;
        int bb=page+4;
        if(aa<=0)
        {
          aa=1;
          bb=10;
        }
        sb.append("<span id='pageList' name='pageList'>");
        if (totals>perpagenum){
        	sb.append("共" + totals + "条&nbsp;" + page + "/" + allpage + "页&nbsp;&nbsp;");
            if (page > 1) {
            	sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"",""),"{$page}",(page - 1)+"") + "'><img src='/images/pre_page_simple_act.gif' align='absbottom' border=0 /></a>&nbsp;");
            }
            else {
            	sb.append("<img src='/images/pre_page_simple.gif' align='absbottom' border=0 />&nbsp;");
            }
            int i = 0;
            for (i = aa; i <= bb; i++) {
                if (i == page) {
                    sb.append("<font color='#ff0000'>" + i + "</font>");
                    sb.append("&nbsp;");
                }
                else {
                    sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"",""),"{$page}",i+"") + "'>" + i + "</a>&nbsp;");
                }
                if (i == allpage) {
                    i++;
                    break;
                }
            }
            if (page < allpage) {
                sb.append("<a href='" + Common.replaceAll(Common.replaceAll(filename,"",""),"{$page}",(page + 1)+"") + "'><img src='/images/next_page_act.gif' align='absbottom' border=0 /></a>&nbsp;");               
            }
            else {
            	sb.append("<img src='/images/next_page.gif' align='absbottom' border=0 />");
            }           
        }else{
        	sb.append("共" + totals + "条");
        }
        sb.append("</span>");
        this.listPageBreak = sb.toString();
        return;
    }
  }

}

