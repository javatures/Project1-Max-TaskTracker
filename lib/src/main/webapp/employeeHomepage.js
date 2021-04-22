let currentUserId = $("#userId").val();

let getTasks = async function () {
    let response = await fetch("api/tasks/employee/" + currentUserId);
    let tasks = await response.json();

    return tasks;
};

let populateTaskTable = async function () {
    let tasks = await getTasks();
    for (const task of tasks) {
        if (task.currentStatus == 1) {
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
            approvalButton.innerHTML = `<form method="post"><button name="submitForApproval" class="btn btn-primary" value="` + task.taskId + `" type="submit">Submit for Approval</button></form>`;
            row.appendChild(approvalButton);

            $('#taskTable').append(row);
        }
    }
};

let getCurrentUser = async function () {
    let response = await fetch("api/users/" + currentUserId);
    let user = await response.json();

    return user;
};

let fillInFullName = async function () {
    let user = await getCurrentUser();

    $("#fullName").html(user.fname + " " + user.lname);
};

let createHistoryButton = function () {
    let historyButton = document.createElement("td");
    historyButton.innerHTML = `<a href="viewHistory/` + currentUserId + `" class="btn btn-primary">View History</a>`;
    $('#history').append(historyButton);
};

(async () => {
    createHistoryButton();
    await fillInFullName();

    await populateTaskTable();
    $('#tasks').DataTable();
})();