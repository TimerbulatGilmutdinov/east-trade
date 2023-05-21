function sortProducts() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;

    window.location.href = "/products?sort=" + timeSelectedValue;
}