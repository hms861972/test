<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	//获取当前月份
    Calendar cal= Calendar.getInstance();
    int curYear = cal.get(Calendar.YEAR);
    request.setAttribute("curYear",curYear);

    List yearList = new ArrayList();
    for (int i = curYear;i>=curYear-4;i--){
        yearList.add(i);
    }
    request.setAttribute("yearList",yearList);
%>

<!DOCTYPE HTML>
<html>
  <head>
    <%@include file="/common/header.jsp"%>
    <title>年度投诉统计图</title>
      <script src="${basePath}js/fusioncharts/fusioncharts.js"></script>
      <script src="${basePath}js/fusioncharts/fusioncharts.charts.js"></script>
      <script src="${basePath}js/fusioncharts/themes/fusioncharts.theme.fint.js"></script>
      <script>
          $(document).ready(doAnnualStatistic());
          //根据年份统计投诉数
          function doAnnualStatistic(){
              //1.获取年份
              var year = $("#year option:selected").val();
              if (year == ""||year == undefined){
                  year = ${curYear};
              }
              //2.获取年份统计
              $.ajax({
                  url:"${basePath }/nsfw/complain_getAnnualStatisticData.action",
                  data:{"year":year},
                  type:"POST",
                  dataType:"json",
                  success:function (data) {
                      if (data!=null||data!=""||data!=undefined){
                          var revenueChart = new FusionCharts({
                              "type": "line",
                              "renderAt": "chartContainer",
                              "width": "500", //width of the chart
                              "height": "300", //height of the chart
                              "dataFormat": "json",
                              "dataSource": {
                                  "chart": {
                                      "caption": year + " 年度投诉数统计图",
                                      "xAxisName": "月  份",
                                      "yAxisName": "投  诉  数",
                                      "theme": "fint"
                                  },
                                  "data": data.chartData
                              }
                          });
                          revenueChart.render();
                      }else {
                          alert("统计投诉数失败！");
                      }
                  },
                  error:function () {
                      alert("统计投诉数失败！");
                  }
              });


          }

      </script>
  </head>
  
  <body>
  	<br>
    <s:select id="year" list="#request.yearList" onchange="doAnnualStatistic()"></s:select>
    <br>
    <div id="chartContainer"></div>
  </body>
</html>
