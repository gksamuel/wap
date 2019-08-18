<%@include file="../includes/header.jsp" %>
<sql:query var="rs" dataSource="jdbc/workdb">
    select count(*) from jobs j join category c on j.categoryid = c.id where j.categoryid = ?::integer;
    <sql:param value="${utilSession.jobCategory}"/>
</sql:query>    
<jsp:setProperty name="utilSession" property="pageCount" value="${rs.getRowsByIndex()[0][0]/10}"/>
<c:if test="${param.nav == 'next'}">
    ${utilSession.addPageNo()}
</c:if>
<c:if test="${param.nav == 'back'}">
    ${utilSession.subPageNo()}
</c:if>
<c:redirect url="category.jsp"/>
