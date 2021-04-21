let currentUserId = $("#userId").val();

let getTasks = async function () {
    let response = await fetch("api/tasks/manager/" + currentUserId);
    let tasks = await response.json();

    return tasks;
};

let getUser = async function (recieverId) {
    let response = await fetch("api/users/" + recieverId);
    let user = await response.json();

    return user;
};

let populateTaskTable = async function () {
    let tasks = await getTasks();
    for (const task of tasks) {
        if (task.currentStatus == 2) {
            const user = await getUser(task.reciever);
            let row = document.createElement("tr");

            let taskId = document.createElement("td");
            taskId.innerHTML = task.taskId;
            row.appendChild(taskId);

            let userName = document.createElement("td");
            userName.innerHTML = user.userName;
            row.appendChild(userName);

            let lname = document.createElement("td");
            lname.innerHTML = user.lname;
            row.appendChild(lname);

            let fname = document.createElement("td");
            fname.innerHTML = user.fname;
            row.appendChild(fname);

            let title = document.createElement("td");
            title.innerHTML = task.title;
            row.appendChild(title);

            let body = document.createElement("td");
            body.innerHTML = task.body;
            row.appendChild(body);

            let evid = document.createElement("td");
            evid.innerHTML = task.evidence;
            row.appendChild(evid);

            let approveButton = document.createElement("td");
            approveButton.innerHTML = `<form method="post" action="managerApproveTask"><button name="approveTask" class="btn btn-primary" value="` + task.taskId + `" type="submit">Approve Task</button></form>`;
            row.appendChild(approveButton);

            let denyButton = document.createElement("td");
            denyButton.innerHTML = `<form method="post" action="managerDenyTask"><button name="denyTask" class="btn btn-primary" value="` + task.taskId + `" type="submit">Deny Task</button></form>`;
            row.appendChild(denyButton);

            $('#taskTable').append(row);
        }
    }
};

let getUsers = async function () {
    let response = await fetch("api/users");
    let users = await response.json();

    return users;
}

let populateEmployeeTable = async function () {
    let users = await getUsers();
    for (const user of users) {
        if (user.manager == currentUserId) {
            let row = document.createElement("tr");

            let uname = document.createElement("td");
            uname.innerHTML = user.userName;
            row.appendChild(uname);

            let lname = document.createElement("td");
            lname.innerHTML = user.lname;
            row.appendChild(lname);

            let fname = document.createElement("td");
            fname.innerHTML = user.fname;
            row.appendChild(fname);

            let taskButton = document.createElement("td");
            taskButton.innerHTML = `<form method="post" action="managerCreateTask">
            <button name="createTask" class="btn btn-primary" value="` + user.userId +
                `" type="submit">Create New Task</button></form>`;
            row.appendChild(taskButton);

            $('#employeeTable').append(row);
        }
    }
};

(async () => {
    await populateTaskTable();
    $('#tasks').DataTable();

    await populateEmployeeTable();
    $('#employees').DataTable();
})();