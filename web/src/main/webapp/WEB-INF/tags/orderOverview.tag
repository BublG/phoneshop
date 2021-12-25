<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="order" required="true" type="com.es.core.model.order.Order" %>

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