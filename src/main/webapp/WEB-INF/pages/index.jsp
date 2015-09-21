<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 8/31/15
  Time: 9:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

    <form method="POST" action="./">
        <label>Add expression</label>
        <input type="text" name="expression">

        </input>
        <input type="submit" value="Submit"/>
    </form>

    <c:if test="${!empty result}">
        <label>result : ${result}</label>
    </c:if>

</body>
</html>
