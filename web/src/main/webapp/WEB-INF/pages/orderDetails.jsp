<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Order details</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>
<h1>Order details</h1>
<h4>Order number: ${order.id}</h4>
<h4>Order status: ${order.status}</h4>
<tags:orderOverview order="${order}"/>
<div class="row">
    <div class="col">
        <a href="${pageContext.servletContext.contextPath}/admin/orders/1">
            <button>Back</button>
        </a>
    </div>
    <div class="col">
        <form method="post">
            <input type="hidden" name="status" value="DELIVERED">
            <button>Delivered</button>
        </form>
    </div>
    <div class="col">
        <form method="post">
            <input type="hidden" name="status" value="REJECTED">
            <button>Rejected</button>
        </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
