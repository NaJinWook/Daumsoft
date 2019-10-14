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
#chartdiv {
	width: 100%;
	height: 500px;
}

.source_logo {
	width: 400px;
	height: 200px;
}
</style>
<script>
var data = <%=request.getAttribute("jsonArray")%>
	// Themes begin
	am4core.ready(function() {
		am4core.useTheme(am4themes_animated);
		// Themes end

		// Create chart instance
		var chart = am4core.create("chartdiv", am4charts.XYChart);
		chart.scrollbarX = new am4core.Scrollbar();

		chart.exporting.menu = new am4core.ExportMenu(); // 파일 저장 기능

		// Add data
		chart.data = data;

		// Create axes
		var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
		categoryAxis.dataFields.category = "regDate";
		categoryAxis.renderer.grid.template.location = 0;
		categoryAxis.renderer.minGridDistance = 50;
		categoryAxis.renderer.labels.template.horizontalCenter = "middle";
		categoryAxis.renderer.labels.template.verticalCenter = "middle";
		categoryAxis.tooltip.disabled = true;
		categoryAxis.renderer.minHeight = 110;

		var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
		valueAxis.title.text = "Article Count";
		//valueAxis.title.rotation = 360;
		valueAxis.title.fontSize = 20;
		valueAxis.title.fontWeight = "bold";
		valueAxis.renderer.minWidth = 50;

		// Create series
		var series = chart.series.push(new am4charts.ColumnSeries());
		series.name = "막대차트 이름";
		series.sequencedInterpolation = true;
		series.dataFields.valueY = "collection";
		series.dataFields.categoryX = "regDate";
		series.tooltipText = "[{categoryX}: bold]{valueY}[/]";
		series.fill = am4core.color("rgb(59, 157, 255);");
		series.columns.template.strokeWidth = 0;
		series.tooltip.pointerOrientation = "vertical";

		// 차트 색 투명도
		series.columns.template.column.fillOpacity = 0.8;

		// on hover, make corner radiuses bigger
		var hoverState = series.columns.template.column.states.create("hover");
		hoverState.properties.cornerRadiusTopLeft = 0;
		hoverState.properties.cornerRadiusTopRight = 0;
		hoverState.properties.fillOpacity = 1;

		// Cursor
		chart.cursor = new am4charts.XYCursor();

		// 범례, 대부분의 경우 범례를 만들려면 Legend 차트 legend 속성에 인스턴스 만 할당하면 됨
// 		chart.legend = new am4charts.Legend();

		var lineSeries = chart.series.push(new am4charts.LineSeries());
		lineSeries.name = "라인차트 이름";
		lineSeries.dataFields.valueY = "collection";
		lineSeries.dataFields.categoryX = "regDate";
		lineSeries.stroke = am4core.color("#ffd700");
		lineSeries.strokeWidth = 3;
		lineSeries.propertyFields.strokeDasharray = "lineDash";
		lineSeries.tooltip.label.textAlign = "middle";

		var bullet = lineSeries.bullets.push(new am4charts.Bullet());
		bullet.fill = am4core.color("#ffd700"); // tooltips grab fill from parent by default
		//bullet.tooltipText = "[#fff font-size: 15px]{name} in {categoryX}:\n[/][#fff font-size: 20px]{valueY}[/] [#fff]{additional}[/]"
		var circle = bullet.createChild(am4core.Circle);
		circle.radius = 4;
		circle.fill = am4core.color("#fff");
		circle.strokeWidth = 4;
	}); // end am4core.ready()
</script>
</head>
<body>
	<c:if test="${siteId == '1'}">
		<div><img src="../../../resources/images/naver_logo.png" class="source_logo"/></div>
	</c:if>
	<c:if test="${siteId == '2' }">
		<div><img src="../../../resources/images/daum_logo.png" class="source_logo"/></div>
	</c:if>
	<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">검색 기간 : <span style="color:#e04400;">${startDate} ~ ${endDate}</span></span></p>
	<c:if test="${categoryId == 'all' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">카테고리 : <span style="color:#002060;">정치+경제+사회</span></span></p>
	</c:if>
	<c:if test="${categoryId == '100' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">카테고리 : <span style="color:#e04400;">정치</span></span></p>
	</c:if>
	<c:if test="${categoryId == '101' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">카테고리 : <span style="color:#e04400;">경제</span></span></p>
	</c:if>
	<c:if test="${categoryId == '102' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">카테고리 : <span style="color:#e04400;">사회</span></span></p>
	</c:if>
	<c:if test="${oId == 'all' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#002060;">통합</span></span></p>
	</c:if>
	<c:if test="${oId == '001' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">연합뉴스</span></span></p>
	</c:if>
	<c:if test="${oId == '003' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">뉴시스</span></span></p>
	</c:if>
	<c:if test="${oId == '421' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">뉴스1</span></span></p>
	</c:if>
	<c:if test="${oId == '277' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">아시아경제</span></span></p>
	</c:if>
	<c:if test="${oId == '008' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">머니투데이</span></span></p>
	</c:if>
	<c:if test="${oId == '014' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">파이낸셜뉴스</span></span></p>
	</c:if>
	<c:if test="${oId == '018' }">
		<p style="padding-top:10px;"><span style="font-weight:bold;font-size:30px;font-style:italic;">언론사 : <span style="color:#e04400;">이데일리</span></span></p>
	</c:if>
	<div id="chartdiv"></div>
</body>
</html>