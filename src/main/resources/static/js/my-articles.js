function deleteArticle(taskId) {
    if (confirm("Are you sure you want to delete this article?")) {
        $.ajax({
            url: "/articles/" + taskId,
            method: "DELETE",
            success: function (result) {
                location.reload();
            }
        });
    }
}