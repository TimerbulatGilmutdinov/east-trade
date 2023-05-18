function deleteTask(taskId) {
    if (confirm("Are you sure you want to delete this task?")) {
        $.ajax({
            url: "/tasks/" + taskId,
            method: "DELETE",
            success: function () {
                location.reload();
            }
        });
    }
}