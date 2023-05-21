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

function sortTasks() {
    let timeSelect = document.getElementById("sortSelect");
    let timeSelectedValue = timeSelect.value;
    let topicSelect = document.getElementById("topicSelect");
    let topicSelectedValue = topicSelect.value;

    if (topicSelectedValue === "ALL") {
        window.location.href = "/tasks?sort=" + timeSelectedValue;
    } else {
        window.location.href = "tasks?sort="+timeSelectedValue+"&topic="+topicSelectedValue;
    }
}