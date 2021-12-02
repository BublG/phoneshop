<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>ProductList</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/js/script.js"></script>
</head>
<body>
<p>
    <a href="${pageContext.servletContext.contextPath}/cart">
        My cart: <span id="cartParams">${cart.totalQuantity} items ${cart.totalCost}</span>$
    </a>
</p>
<form action="${pageContext.servletContext.contextPath}/productList/1">
    <input name="query" value="${param.query}">
    <button>Search</button>
</form>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>Image</th>
        <th>
            Brand
            <tags:sortLink sort="brand" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="brand" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </th>
        <th>
            Model
            <tags:sortLink sort="model" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="model" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </th>
        <th>Color</th>
        <th>
            Display size
            <tags:sortLink sort="displaySizeInches" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="displaySizeInches" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </th>
        <th>
            Price
            <tags:sortLink sort="price" order="asc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-up-solid.svg">
            </tags:sortLink>
            <tags:sortLink sort="price" order="desc">
                <img width="10px" height="17px"
                     src="${pageContext.servletContext.contextPath}/images/long-arrow-alt-down-solid.svg">
            </tags:sortLink>
        </th>
        <th>Quantity</th>
        <th>Action</th>
    </tr>
    </thead>
    <c:forEach var="phone" items="${phones}">
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            </td>
            <td>${phone.brand}</td>
            <td>
                    <a href="${pageContext.servletContext.contextPath}/productDetails/${phone.id}">${phone.model}</a>
            </td>
            <td>
                <c:forEach var="color" items="${phone.colors}">
                    ${color.code},
                </c:forEach>
            </td>
            <td>${phone.displaySizeInches}"</td>
            <td>${phone.price}$</td>
            <td>
                <form id="${phone.id}">
                    <input name="quantity" id="${phone.id}quantity"/>
                    <input name="phoneId" type="hidden" value="${phone.id}">
                </form>
                <div class="error-message" id="${phone.id}error"></div>
            </td>
            <td>
                <button onclick="sendAddToCartForm(${phone.id},
                        '${pageContext.servletContext.contextPath}/ajaxCart')">
                    Add to cart
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty phones}">
    <nav>
        <ul class="pagination">
            <li class="page-item">
                <c:set var="prevPage" value="${page == 1 ? page : page - 1}"/>
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/productList/${prevPage}?sortField=${param.sortField}&sortOrder=${param.sortOrder}&query=${param.query}">
                    Previous
                </a>
            </li>
            <c:forEach var="pageNumber" items="${pageNumbers}">
                <li class="${pageNumber == page ? 'page-item active' : 'page-item'}">
                    <a class="page-link"
                       href="${pageContext.servletContext.contextPath}/productList/${pageNumber}?sortField=${param.sortField}&sortOrder=${param.sortOrder}&query=${param.query}">
                            ${pageNumber}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item">
                <c:set var="nextPage" value="${page == maxPage ? page : page + 1}"/>
                <a class="page-link"
                   href="${pageContext.servletContext.contextPath}/productList/${nextPage}?sortField=${param.sortField}&sortOrder=${param.sortOrder}&query=${param.query}">
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