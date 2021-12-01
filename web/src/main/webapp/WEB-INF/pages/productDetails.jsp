<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    My cart: <span id="cartParams">${cart.totalQuantity} items ${cart.totalCost}</span>$
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
            <h3>Display</h3>
            <table class="table table-bordered">
                <tr>
                    <td>Size</td>
                    <td>${phone.displaySizeInches}"</td>
                </tr>
                <tr>
                    <td>Resolution</td>
                    <td>${phone.displayResolution}</td>
                </tr>
                <tr>
                    <td>Technology</td>
                    <td>${phone.displayTechnology}</td>
                </tr>
                <tr>
                    <td>Pixel density</td>
                    <td>${phone.pixelDensity}</td>
                </tr>
            </table>
            <h3>Dimensions & weight</h3>
            <table class="table table-bordered">
                <tr>
                    <td>Length</td>
                    <td>${phone.lengthMm}mm</td>
                </tr>
                <tr>
                    <td>Width</td>
                    <td>${phone.widthMm}mm</td>
                </tr>
                <tr>
                    <td>Color</td>
                    <td>
                        <c:if test="${not empty phone.colors}">
                            ${phone.colors.toArray()[0].code}
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Weight</td>
                    <td>${phone.weightGr}g</td>
                </tr>
            </table>
            <h3>Camera</h3>
            <table class="table table-bordered">
                <tr>
                    <td>Front</td>
                    <td>${phone.frontCameraMegapixels} megapixels</td>
                </tr>
                <tr>
                    <td>Back</td>
                    <td>${phone.backCameraMegapixels} megapixels</td>
                </tr>
            </table>
            <h3>Battery</h3>
            <table class="table table-bordered">
                <tr>
                    <td>Talk time</td>
                    <td>${phone.talkTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Stand by time</td>
                    <td>${phone.standByTimeHours} hours</td>
                </tr>
                <tr>
                    <td>Battery capacity</td>
                    <td>${phone.batteryCapacityMah}mAh</td>
                </tr>
            </table>
            <h3>Other</h3>
            <table class="table table-bordered">
                <tr>
                    <td>Colors</td>
                    <td>
                        <c:forEach var="color" items="${phone.colors}">
                            ${color.code},
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Device type</td>
                    <td>${phone.deviceType}</td>
                </tr>
                <tr>
                    <td>Bluetooth</td>
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
