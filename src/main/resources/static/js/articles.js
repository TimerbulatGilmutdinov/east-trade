function sortArticles() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;

    window.location.href = "/articles?sort=" + timeSelectedValue;
}