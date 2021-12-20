<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>OrderList</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<h1>Orders</h1>
<table class="table table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th><spring:message code="orderListPage.orderNumber"/></th>
        <th><spring:message code="orderListPage.customer"/></th>
        <th><spring:message code="orderListPage.phone"/></th>
        <th><spring:message code="orderListPage.address"/></th>
        <th><spring:message code="orderListPage.date"/></th>
        <th><spring:message code="orderListPage.totalPrice"/></th>
        <th><spring:message code="orderListPage.status"/></th>
    </tr>
    </thead>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <a href="${pageContext.servletContext.contextPath}/admin/orderDetails/${order.id}">${order.id}</a>
            </td>
            <td>${order.firstName} ${order.lastName}</td>
            <td>${order.contactPhoneNo}</td>
            <td>${order.deliveryAddress}</td>
            <td>${order.created}</td>
            <td>${order.totalPrice}$</td>
            <td>${order.status}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty orders}">
    <nav>
        <ul class="pagination">
            <li class="page-item">
                <c:set var="prevPage" value="${page == 1 ? page : page - 1}"/>
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/admin/orders/${prevPage}">
                    Previous
                </a>
            </li>
            <c:forEach var="pageNumber" items="${pageNumbers}">
                <li class="${pageNumber == page ? 'page-item active' : 'page-item'}">
                    <a class="page-link"
                       href="${pageContext.servletContext.contextPath}/admin/orders/${pageNumber}">
                            ${pageNumber}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item">
                <c:set var="nextPage" value="${page == maxPage ? page : page + 1}"/>
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/admin/orders/${nextPage}">
                    Next
                </a>
            </li>
        </ul>
    </nav>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
