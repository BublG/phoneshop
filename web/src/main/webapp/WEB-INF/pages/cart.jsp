<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Cart</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/js/script.js"></script>
</head>
<body>
<h1>Cart</h1>
<p>
    <a href="${pageContext.servletContext.contextPath}/cart">
        My cart: <span id="cartParams">${cart.totalQuantity} items ${cart.totalCost}</span>$
    </a>
</p>
<p>
    <a href="${pageContext.servletContext.contextPath}/productList/1">Back to product list</a>
</p>
<form id="updateForm" method="post" action="${pageContext.servletContext.contextPath}/cart">
    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.phone.brand')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.phone.model')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.phone.color')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.phone.displaySize')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.phone.price')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.quantity')" />
            </th>
            <th>
                <spring:eval expression="@propertyConfigurer.getProperty('cartPage.action')" />
            </th>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${cart.items}" varStatus="status">
            <tr>
                <td>${cartItem.phone.brand}</td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/productDetails/${cartItem.phone.id}">
                            ${cartItem.phone.model}
                    </a>
                </td>
                <td>
                    <c:forEach var="color" items="${cartItem.phone.colors}">
                        ${color.code},
                    </c:forEach>
                </td>
                <td>${cartItem.phone.displaySizeInches}"</td>
                <td>${cartItem.phone.price}$</td>
                <td>
                    <input id="${cartItem.phone.id}" name="quantity" value="${cartItem.quantity}">
                    <div id="${cartItem.phone.id}error" class="error-message"></div>
                    <input name="phoneId" value="${cartItem.phone.id}" type="hidden">
                </td>
                <td>
                    <button type="button"
                            onclick="deleteCartItem('${pageContext.servletContext.contextPath}/cart?phoneId=${cartItem.phone.id}')">
                        Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button>
        Update
    </button>
</form>
<button onclick="updateCart('${pageContext.servletContext.contextPath}/cart')">Test</button>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
