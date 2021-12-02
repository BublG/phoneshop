function sendAddToCartForm(id, url) {
    let form = $("#" + id);
    $.ajax({
        type: "POST",
        url: url,
        data: form.serialize(),
        success: function(data, textStatus, jqXHR)
        {
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
    let form = $('#updateForm');
    $.ajax({
        type: "POST",
        url: url,
        data: form.serialize(),
        async: false,
    });
}