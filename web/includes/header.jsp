<%@page import="sessions.Seeker"%>
<%@page import="utils.Search"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="url" scope="request" value="http://localhost:8080"/>
<c:set var="url" scope="request" value="https://myjob.co.ke"/>
<c:set var="string" value="${pageContext.request.requestURI}"/>
<c:if test="${userSession.username == null}">
    <c:set var="redirect" scope="request" value="true"/>
    <c:if test="${string == '/m/index.jsp'}">
        <c:set var="redirect" scope="request" value="false"/>
    </c:if>
    <c:if test="${fn:startsWith(string, '/m/registration')}">
        <c:set var="redirect" scope="request" value="false"/>
    </c:if>
    <c:if test="${fn:startsWith(string, '/m/content')}">
        <c:set var="redirect" scope="request" value="false"/>
    </c:if>
    <c:if test="${fn:startsWith(string, '/m/expired')}">
        <c:set var="redirect" scope="request" value="false"/>
    </c:if>
    <c:if test="${redirect == 'true'}">
        <c:redirect url="${url}/m/index.jsp"/>
    </c:if>
</c:if>
<c:if test="${userSession.username != null}">
    <c:if test="${fn:startsWith(string, '/m/expired') == false}">
        <jsp:useBean id="currtime" class="java.util.Date" />
        <c:if test="${userSession.expiry le currtime}">
            <c:redirect url="${url}/m/expired.jsp"/>
        </c:if>
    </c:if>
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <link href="/m/css/myjob.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    </head>
    <body>
        <div class="content form">
            <span><label>Employers - <a href="/Desktop">CLICK HERE</a></label></span>
        </div>
        <header>
            <div class="logo">
                <a href="/m/home.jsp"><img src="/m/img/myjob.png" alt=""></a>
            </div>
            <div class="search-box">
                ${userSession.username}
            </div>
            <div class="clearFloat"></div>
        </header>
        <jsp:useBean id="searchSession" scope="session" class="utils.Search" />
        <c:if test="${userSession.username != null}">
            <div class="form">
                <form method="post" action="/m/searchActions">
                    <span>
                        <input type="text" id="keyword" name="keyword" value="${searchSession.keyword}" maxlength="100">
                        <input type="submit" value="Search" name="search" style="display: inline;padding:4px;"/>
                    </span>
                </form>
            </div>
            <div class="clearFloat"></div>
        </c:if>
        <jsp:useBean id="utilSession" scope="session" class="sessions.Utils" />