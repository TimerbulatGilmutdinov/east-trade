function deleteArticle(taskId) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (confirm("Are you sure you want to delete this article?")) {
        $.ajax({
            url: "/articles/" + taskId,
            method: "DELETE",
            headers:{
                [header]:token
            },
            success: function (result) {
                location.reload();
            },
            error: function (error){
                console.log('Ошибка при удалении'+error)
            }
        });
    }
}

function sortArticles() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;

    window.location.href = "/my-articles?sort=" + timeSelectedValue;
}
