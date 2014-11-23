<%@ page import="com.scttsc.business.model.Bts" %>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>室分手工数据</title>
    <%@ include file="/appviews/common/tag.jsp" %>
    <link href="${ctx}/resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet"/>

</head>
<body>
<div class="tabbable">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#tab1" data-toggle="tab">室分录入</a></li>
        <li><a href="#tab2" data-toggle="tab">直放站录入</a></li>
        <li><a href="#tab3" data-toggle="tab">干放站录入</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <p>
                    <%=((Bts)request.getAttribute("bts")).getName()%>
            <form style="padding-bottom: 15px;padding-top: 20px;">
                <div class="controls row-fluid">
                    <span class="span4 form-horizontal">
                          <div class="control-group">
                              <label class="control-label">室内分布站点名称：</label>

                              <div class="controls">
                                  <select id="cons">
                                      <input type="text" id="name" name="name" value="${bts.name}">
                                  </select>
                              </div>
                          </div>
                    </span>
                     <span class="span4 form-horizontal">
                          <div class="control-group">
                              <label class="control-label">本地网:</label>

                              <div class="controls">
                                  <input type="text" id="cityName" name="cityName" value="${bts.cityName}">
                              </div>
                          </div>
                    </span>
                    <span class="span4 form-horizontal">
                          <div class="control-group">
                              <label class="control-label">所属区县:</label>

                              <div class="controls">
                                  <input type="text" id="countyName" name="countyName" value="${bts.country.cityName}">
                              </div>
                          </div>
                    </span>


                </div>
                <div class="controls row-fluid">

                </div>
                <div class="form-actions">
                    <button class="btn btn-info" type="button" id="submit">确认</button>
                    <button class="btn btn-info" type="reset" onclick="javascript:history.back(-1);">返回</button>
                </div>
            </form>

            </p>
        </div>
        <div class="tab-pane" id="tab2">
            <p>Howdy, I'm in Section 2.</p>
        </div>
        <div class="tab-pane" id="tab3">
            <p>Howdy, I'm in Section 2.</p>
        </div>
    </div>
</div>
</body>
</html>