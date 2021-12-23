<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>ProductDetails</title>
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
    <tags:loginHeader/>
</p>
<p>
    <a href="${pageContext.servletContext.contextPath}/cart">
        My cart: <span id="cartParams">${cart.totalQuantity} items ${cart.totalCost}</span>$
    </a>
</p>
<p>
    <a href="${pageContext.servletContext.contextPath}/productList/1">Back to product list</a>
</p>
<div class="container">
    <div class="row">
        <div class="col">
            <h1>${phone.model}</h1>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
            <div>${phone.description}</div>
            <h1>Price: ${phone.price}$</h1>
            <form id="${phone.id}">
                <input name="quantity" id="${phone.id}quantity"/>
                <input name="phoneId" type="hidden" value="${phone.id}">
            </form>
            <div class="error-message" id="${phone.id}error"></div>
            <button onclick="sendAddToCartForm(${phone.id},
                    '${pageContext.servletContext.contextPath}/ajaxCart')">
                Add to cart
            </button>
        </div>
        <div class="col">
            <h3><spring:message code="productDetailsPage.section.display"/></h3>
            <table class="table table-bordered">
                <tr>
                    <td><spring:message code="productDetailsPage.display.size"/></td>
                    <td>${phone.displaySizeInches}"</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.display.resolution"/></td>
                    <td>${phone.displayResolution}</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.display.technology"/></td>
                    <td>${phone.displayTechnology}</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.display.pixelDensity"/></td>
                    <td>${phone.pixelDensity}</td>
                </tr>
            </table>
            <h3><spring:message code="productDetailsPage.section.dimensionsAndWeight"/></h3>
            <table class="table table-bordered">
                <tr>
                    <td><spring:message code="productDetailsPage.dimensionsAndWeight.length"/></td>
                    <td>${phone.lengthMm}mm</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.dimensionsAndWeight.width"/></td>
                    <td>${phone.widthMm}mm</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.dimensionsAndWeight.color"/></td>
                    <td>
                        <c:if test="${not empty phone.colors}">
                            ${phone.colors.toArray()[0].code}
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.dimensionsAndWeight.weight"/></td>
                    <td>${phone.weightGr}g</td>
                </tr>
            </table>
            <h3><spring:message code="productDetailsPage.section.camera"/></h3>
            <table class="table table-bordered">
                <tr>
                    <td><spring:message code="productDetailsPage.camera.front"/></td>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.camera.back"/></td>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
            </table>
            <h3><spring:message code="productDetailsPage.section.battery"/></h3>
            <table class="table table-bordered">
                <tr>
                    <td><spring:message code="productDetailsPage.battery.talkTime"/></td>
                    <td>${phone.talkTimeHours} hours</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.battery.standByTime"/></td>
                    <td>${phone.standByTimeHours} hours</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.battery.capacity"/></td>
                    <td>${phone.batteryCapacityMah}mAh</td>
                </tr>
            </table>
            <h3><spring:message code="productDetailsPage.section.other"/></h3>
            <table class="table table-bordered">
                <tr>
                    <td><spring:message code="productDetailsPage.other.colors"/></td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color.code},
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.other.deviceType"/></td>
                    <td>${phone.deviceType}</td>
                </tr>
                <tr>
                    <td><spring:message code="productDetailsPage.other.bluetooth"/></td>
                    <td>${phone.bluetooth}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
