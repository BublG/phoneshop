<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Order overview</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>
<h1>Order overview</h1>
<h2>Thank you for your order</h2>
<h4>Order number: ${order.id}</h4>
<p>
    <a href="${pageContext.servletContext.contextPath}/productList/1">Back to product list</a>
</p>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>
            <spring:message code="cartPage.phone.brand"/>
        </th>
        <th>
            <spring:message code="cartPage.phone.model"/>
        </th>
        <th>
            <spring:message code="cartPage.phone.color"/>
        </th>
        <th>
            <spring:message code="cartPage.phone.displaySize"/>
        </th>
        <th>
            <spring:message code="cartPage.phone.price"/>
        </th>
        <th>
            <spring:message code="cartPage.quantity"/>
        </th>
    </tr>
    </thead>
    <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
        <tr>
            <td>${orderItem.phone.brand}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/productDetails/${orderItem.phone.id}">
                        ${orderItem.phone.model}
                </a>
            </td>
            <td>
                <c:forEach var="color" items="${orderItem.phone.colors}">
                    ${color.code},
                </c:forEach>
            </td>
            <td>${orderItem.phone.displaySizeInches}"</td>
            <td>${orderItem.phone.price}$</td>
            <td>${orderItem.quantity}</td>
        </tr>
    </c:forEach>
    <tr style="border: none!important">
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: 1px solid gray"><spring:message code="orderPage.subtotal"/></td>
        <td style="border: 1px solid gray">${order.subtotal}$</td>
    </tr>
    <tr style="border: none!important">
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: 1px solid gray"><spring:message code="orderPage.delivery"/></td>
        <td style="border: 1px solid gray">${order.deliveryPrice}$</td>
    </tr>
    <tr style="border: none!important">
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: none!important"></td>
        <td style="border: 1px solid gray"><spring:message code="orderPage.total"/></td>
        <td style="border: 1px solid gray">${order.totalPrice}$</td>
    </tr>
</table>
    <div class="container">
        <div class="row">
            <div class="col-2">
                <div class="row"><spring:message code="orderPage.firstName"/></div>
                <div class="row"><spring:message code="orderPage.lastName"/></div>
                <div class="row"><spring:message code="orderPage.address"/></div>
                <div class="row"><spring:message code="orderPage.phone"/></div>
            </div>
            <div class="col-2">
                <div class="row">
                    <div>${order.firstName}</div>
                </div>
                <div class="row">
                    <div>${order.lastName}</div>
                </div>
                <div class="row">
                    <div>${order.deliveryAddress}</div>
                </div>
                <div class="row">
                    <div>${order.contactPhoneNo}</div>
                </div>
            </div>
            <div class="col-8"></div>
        </div>
        <div class="row">
            <div class="col-4">
                <div class="row">
                    ${order.additionalInformation}
                </div>
            </div>
        </div>
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>