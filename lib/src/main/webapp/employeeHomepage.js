let currentUserId = $("#userId").val();

let getTasks = async function () {
    let response = await fetch("api/tasks/employee/" + currentUserId);
    let tasks = await response.json();

    return tasks;
}

let populateTaskTable = async function () {
    let tasks = await getTasks();
    for (const task of tasks) {
        if(task.currentStatus == 1)
        {
            let row = document.createElement("tr");

            let taskId = document.createElement("td");
            taskId.innerHTML = task.taskId;
            row.appendChild(taskId);

            let title = document.createElement("td");
            title.innerHTML = task.title;
            row.appendChild(title);

            let body = document.createElement("td");
            body.innerHTML = task.body;
            row.appendChild(body);

            let approvalButton = document.createElement("td");
            approvalButton.innerHTML = `<form method="post"><button name="submitForApproval" class="btn btn-primary" value="`+task.taskId+`" type="submit">Submit for Approval</button></form>`;
            row.appendChild(approvalButton);

            $('#taskTable').append(row);
        }   
    }
};

(async () => {
    await populateTaskTable();
    $('#tasks').DataTable();
})();