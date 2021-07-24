<%--
  Created by IntelliJ IDEA.
  User: 박범선
  Date: 2021-07-03
  Time: 오후 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>
    <%--<li>username=<%=((Member) request.getAttribute("member")).getUsername()%></li>
    <li>age=<%=((Member)request.getAttribute("member")).getAge()%>--%>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
