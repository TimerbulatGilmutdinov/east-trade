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