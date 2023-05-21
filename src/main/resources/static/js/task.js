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
            success: function (result) {
                window.location.href = '/tasks';
            }
        });
    }
}

function addRespond(taskId) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (confirm("Are you sure you want to respond to this task?")) {
        let url = "/tasks/"+taskId;
        $.ajax({
            url: url,
            method: "POST",
            headers:{
                [header]:token
            }
        });
    }
}

function removeRespond(taskId) {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    if (confirm("Are you sure you want to remove respond from this task?")) {
        $.ajax({
            url: "/tasks/" + taskId,
            method: "Delete",
            headers:{
                [header]:token
            },
            success: function (result) {
                location.reload();
            }
        });
    }
}