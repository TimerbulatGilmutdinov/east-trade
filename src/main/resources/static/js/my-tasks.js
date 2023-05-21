function deleteTask(taskId) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (confirm("Are you sure you want to delete this task?")) {
        $.ajax({
            url: "/tasks/" + taskId,
            method: "DELETE",
            headers:{
                [header]:token
            },
            success: function () {
                location.reload();
            }
        });
    }
}

function sortTasks() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;
    let topicSelect = document.getElementById("topicSelect");
    let topicSelectedValue = topicSelect.value;

    if (topicSelectedValue === "ALL") {
        window.location.href = "/my-tasks?sort=" + timeSelectedValue;
    } else {
        window.location.href = "/my-tasks?sort="+timeSelectedValue+"&topic="+topicSelectedValue;
    }
}