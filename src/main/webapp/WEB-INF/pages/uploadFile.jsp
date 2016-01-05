<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
</head>
<body>
     <form action="uploadFile.do" method="post" enctype="multipart/form-data">
           <input type="text" name="devOnlyId" />设备id<br>
            <input type="text" name="userOnlyId"/>用户id<br>
            <input type="text" name="devType"/>设备类型(PC/Phone/Pad)<br>
            <input type="text" name="productType"/>产品类型(vdp/print)<br>
             <input type="text" name="fileName"/>文件名<br>
             <input type="text" name="ip"/>ip<br>
             <input type="text" name="crc"/>crc<br>
            <input type="file" name="file"/><br/>
            <input type="submit" value="提交"/>
        </form>
  </body>
</html>