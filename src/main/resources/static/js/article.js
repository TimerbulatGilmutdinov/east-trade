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
                window.location.href = '/articles';
            },
            error: function (error){
                console.log('Ошибка при удалении'+error)
            }
        });
    }
}