<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<a href="${pageContext.servletContext.contextPath}/productList/1">Back to product list</a>
<h1 style="text-align: center">Login</h1>
<div class="d-flex justify-content-center">
    <div>
        <form action="${pageContext.servletContext.contextPath}/login" method='POST'>
            <table>
                <tr>
                    <td><spring:message code="loginPage.user"/></td>
                    <td><input type="text" name="username"></td>
                </tr>
                <tr>
                    <td><spring:message code="loginPage.password"/></td>
                    <td><input type="password" name="password"/></td>
                </tr>
            </table>
            <input name="submit" type="submit" value="Login" style="width: 100%"/>
        </form>
        ${error}
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
