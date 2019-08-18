<%-- 
    Document   : report
    Created on : May 8, 2016, May 8, 2016 5:17:57 PM
    Author     : gachanja
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setTimeZone value="GMT+3" />

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <sql:query var="rs" dataSource="jdbc/workdb">
            select count(*) from seekers;
        </sql:query>
        Total Number of Users  = ${rs.getRowsByIndex()[0][0]}<br/>
        <hr/>






        <b>LATEST MESSAGE</b><br/>
        <sql:query var="rs" dataSource="jdbc/workdb">
            select * from inmessage order by id desc limit 1;
        </sql:query>
        ID = ${rs.getRowsByIndex()[0][0]}<br/>
        SOURCE = ${rs.getRowsByIndex()[0][1]}<br/>
        MESSAGE = ${rs.getRowsByIndex()[0][2]}<br/>


        DATE = <fmt:formatDate pattern="dd-MMM-yy H:mm a" value="${rs.getRowsByIndex()[0][3]}"/>
        <hr/>

    </body>
</html>
