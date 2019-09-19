<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false"
 %>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <style>
      #container {
        width: 100%;
        background-color: rgb(157, 206, 255);
      }
      #header {
		  background-color: rgb(34, 49, 63);
		  width: 100%;
		  height: 150px;
      }
      #content {
		  width: 100%;
		  min-height: 800px;
		  background-color: rgb(157, 206, 255);
      }
      #footer {
          clear: both;
		  background-color: rgb(34, 49, 63);
		  width: 100%;
		  height: 100px;
      }
    </style>
    <title><tiles:insertAttribute name="title" /></title>
  </head>
    <body>
    <div id="container">
      <div id="header">
         <tiles:insertAttribute name="header"/>
      </div>
      <div id="content">
      	<div id="contents_box">
          <tiles:insertAttribute name="body"/>
        </div>
      </div>
      <div id="footer">
          <tiles:insertAttribute name="footer"/>
      </div>
    </div>
  </body>
</html>