<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% request.setCharacterEncoding("UTF-8"); %>
<%@ include file="../include/comm.jsp"%>
<!-- <script src="https://www.amcharts.com/lib/4/core.js"></script>
<script src="https://www.amcharts.com/lib/4/charts.js"></script>
<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script> -->
<style>
#chartdiv, #chartdiv2 {
	width: 100%;
	height: 500px;
}

.source_logo {
	width: 200px;
	height: 200px;
}
</style>
</head>
<!-- Chart code -->
<script type="text/javascript" language="javascript">
<%-- var obj = '<%=request.getSession().getAttribute("jsonStr")%>'; --%>
var obj = <%=request.getAttribute("jsonArray")%>;
var positive_sum = <%=request.getAttribute("positive_sum")%>;
var negative_sum = <%=request.getAttribute("negative_sum")%>;
var neutral_sum = <%=request.getAttribute("neutral_sum")%>;
//var data = JSON.parse(obj);
	am4core.ready(function() {
		barChart();
		pieChart();
	}); // end am4core.ready()
	
	function barChart(){
		// Themes begin
		am4core.useTheme(am4themes_animated);
		// Themes end
		
		// Create chart instance
		var chart = am4core.create("chartdiv", am4charts.XYChart);
		chart.scrollbarX = new am4core.Scrollbar();
	
		// Add data
		chart.data = obj;
	
		// Create axes
		var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
		categoryAxis.dataFields.category = "register_date";
		categoryAxis.renderer.grid.template.location = 0;
		categoryAxis.renderer.minGridDistance = 50;
		categoryAxis.renderer.labels.template.horizontalCenter = "right";
		categoryAxis.renderer.labels.template.verticalCenter = "middle";
		categoryAxis.renderer.labels.template.rotation = 270;
		categoryAxis.tooltip.disabled = true;
		categoryAxis.renderer.minHeight = 110;
	
		var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
		valueAxis.title.text = "Frequency";
		
		valueAxis.renderer.minWidth = 50;
	
		// Create series
		var series = chart.series.push(new am4charts.ColumnSeries());
		series.name = "긍정";
		series.sequencedInterpolation = true;
		series.dataFields.valueY = "positive_count";
		series.dataFields.categoryX = "register_date";
		series.tooltipText = "[{categoryX}: bold]{valueY}[/]";
		series.fill=am4core.color("rgb(59, 157, 255);");
		series.columns.template.strokeWidth = 0;
	
		var series2 = chart.series.push(new am4charts.ColumnSeries());
		series2.name = "부정";
		series2.sequencedInterpolation = true;
		series2.dataFields.valueY = "negative_count";
		series2.dataFields.categoryX = "register_date";
		series2.tooltipText = "[{categoryX}: bold]{valueY}[/]";
		series2.fill=am4core.color("#e04400");
		series2.columns.template.strokeWidth = 0;
	
		var series3 = chart.series.push(new am4charts.ColumnSeries());
		series3.name = "중립";
		series3.sequencedInterpolation = true;
		series3.dataFields.valueY = "neutral_count";
		series3.dataFields.categoryX = "register_date";
		series3.tooltipText = "[{categoryX}: bold]{valueY}[/]";
		series3.fill=am4core.color("#f3e165");
		series3.columns.template.strokeWidth = 0;
	
		series.tooltip.pointerOrientation = "vertical";
	
		// 차트 색 투명도
		series.columns.template.column.fillOpacity = 0.8;
		series2.columns.template.column.fillOpacity = 0.8;
		series3.columns.template.column.fillOpacity = 0.8;
	
		// on hover, make corner radiuses bigger
		var hoverState = series.columns.template.column.states.create("hover");
		hoverState.properties.cornerRadiusTopLeft = 0;
		hoverState.properties.cornerRadiusTopRight = 0;
		hoverState.properties.fillOpacity = 1;
	
		// Cursor
		chart.cursor = new am4charts.XYCursor();
	     	
		// 범례, 대부분의 경우 범례를 만들려면 Legend 차트 legend 속성에 인스턴스 만 할당하면 됨
		chart.legend = new am4charts.Legend();
	}
	function pieChart(){
		// Themes begin
		am4core.useTheme(am4themes_animated);
		// Themes end

		var chart = am4core.create("chartdiv2", am4charts.PieChart3D);
		chart.hiddenState.properties.opacity = 0; // this creates initial fade-in

		chart.legend = new am4charts.Legend();

		chart.data = [
		  {
		  	emotion: "긍정",
		    count: positive_sum
		  },
		  {
		  	emotion: "부정",
		    count: negative_sum
		  },
		  {
		  	emotion: "중립",
		    count: neutral_sum
		  }
		];
		var colorSet = new am4core.ColorSet();
		colorSet.list = ["rgb(59, 157, 255)", "#e04400", "#f3e165"].map(function(color) {
		  return new am4core.color(color);
		});
		
		var series = chart.series.push(new am4charts.PieSeries3D());
		series.dataFields.value = "count";
		series.dataFields.category = "emotion";
		
		series.colors = colorSet;
	}
</script>
<body>
	<c:if test="${source == 'blog'}">
		<div><img src="../../../resources/images/blog_logo.png" class="source_logo"/></div>
	</c:if>
	<c:if test="${source == 'twitter'}">
		<div><img src="../../../resources/images/twitter_logo.png" class="source_logo"/></div>
	</c:if>
	<c:if test="${source == 'news' }">
		<div><img src="../../../resources/images/news_logo.png" class="source_logo"/></div>
	</c:if>
	<c:if test="${source == 'facebook'}">
		<div><img src="../../../resources/images/facebook_logo.png" class="source_logo"/></div>
	</c:if>
	<c:if test="${source == 'insta'}">
		<div><img src="../../../resources/images/insta_logo.png" class="source_logo"/></div>
	</c:if>
	<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">검색 기간 : ${startDate}~${endDate}</span></p>
	<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic";>연관어추이(감성) 주제어 : <span style="color:red;">${keyword}</span></span></p>
	<div id="chartdiv"></div>
	<div id="chartdiv2"></div>
</body>
</html>