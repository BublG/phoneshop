function sendAddToCartForm(id, url) {
    let form = $("#" + id);
    $.ajax({
        type: "POST",
        url: url,
        data: form.serialize(),
        success: function (data, textStatus, jqXHR) {
            if (jqXHR.status == 202) {
                document.getElementById(id + "error").innerText = data;
            } else {
                document.getElementById("cartParams").innerText = data;
                document.getElementById(id + "quantity").value = '';
                document.getElementById(id + "error").innerText = '';
            }
        }
    });
}

function deleteCartItem(url) {
    let request = new XMLHttpRequest();
    request.open('DELETE', url, false);
    request.send();
    window.location = '/phoneshop-web/cart';
}

function updateCart(url) {
    let phoneIds = document.getElementsByName("phoneId");
    let quantities = document.getElementsByName("quantity");
    let rowsArray = [];
    for (let i = 0; i < phoneIds.length; i++) {
        rowsArray.push({phoneId: phoneIds.item(i).value, quantity: quantities.item(i).value})
    }
    $.ajax({
        type: "PUT",
        url: url,
        data: JSON.stringify(rowsArray),
        contentType: "application/json;charset=utf-8",
        async: false,
        success: function (updateCartResponse) {
            let errorMessages = document.getElementsByClassName('error-message')
            for (let i = 0; i < errorMessages.length; i++) {
                errorMessages.item(i).innerHTML = '';
            }
            let errors = updateCartResponse['errors']
            if (JSON.stringify(errors) == JSON.stringify({})) {
                window.location = '/phoneshop-web/cart';
            } else {
                for (let key in errors) {
                    document.getElementById(key + 'error').innerText = errors[key];
                }
                document.getElementById("cartParams").innerText = updateCartResponse['cartStatus'];
            }
        }
    });
}