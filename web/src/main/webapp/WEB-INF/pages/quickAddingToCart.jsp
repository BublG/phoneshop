<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>QuickAdding</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>
<p>
    <a href="${pageContext.servletContext.contextPath}/productList/1">Back to product list</a>
</p>
<c:forEach var="message" items="${successMessages}">
    <div class="success-message">${message.key} ${message.value}</div>
</c:forEach>
<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>
<div class="container">
    <div class="row">
        <div class="col">
            <spring:message code="quickAddingPage.productModel"/>
        </div>
        <div class="col">
            <spring:message code="quickAddingPage.quantity"/>
        </div>
    </div>
    <form method="post">
        <c:forEach begin="0" end="7" varStatus="status">
            <div class="row">
                <div class="col">
                    <input name="model" value="${not empty phoneModelErrors[status.index]
                     or not empty quantityErrors[status.index] ? paramValues.model[status.index] : ""}">
                    <c:if test="${not empty phoneModelErrors[status.index]}">
                    <span class="error-message">${phoneModelErrors[status.index]}</span>
                    </c:if>
                </div>
                <div class="col">
                    <input name="quantity" value="${not empty phoneModelErrors[status.index]
                     or not empty quantityErrors[status.index] ? paramValues.quantity[status.index] : ""}">
                    <c:if test="${not empty quantityErrors[status.index]}">
                        <span class="error-message">${quantityErrors[status.index]}
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <button>Add to cart</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
