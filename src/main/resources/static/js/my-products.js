function sortProducts() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;

    window.location.href = "/my-products?sort=" + timeSelectedValue;
}

function deleteProduct(id) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (confirm("Are you sure you want to delete this product?")) {
        $.ajax({
            url: "/products/" + id,
            method: "DELETE",
            headers:{
                [header]:token
            },
            success: function () {
                location.reload();
            },
            error: function (error){
                console.log('Ошибка при удалении'+error)
            }
        });
    }
}