let currentUserId = $("#userId").val();

let getTasks = async function () {
    let response = await fetch("../api/tasks/employee/" + currentUserId);
    let tasks = await response.json();

    return tasks;
};

let populateTaskTable = async function () {
    let tasks = await getTasks();
    for (const task of tasks) {

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

            let status = document.createElement("td");
            if(task.currentStatus == 1)
            {
                status.innerHTML = "Needs to be worked";
            }
            if(task.currentStatus == 2)
            {
                status.innerHTML = "Waiting for manager input";
            }
            if(task.currentStatus == 3)
            {
                status.innerHTML = "Been approved";
            }
            if(task.currentStatus == 4)
            {
                status.innerHTML = "Been denied";
            }
            row.appendChild(status);

            let evid = document.createElement("td");
            if(task.evidenceLocation != null)
            {
                //evid.innerHTML = `<img src="`+task.evidenceLocation+`" width="100" height="60">`;
                evid.innerHTML = `<a href="../`+task.evidenceLocation+`" class="btn btn-primary">View Evidence</a>`;
            }
            else
            {
                evid.innerHTML = "No evidence provided";
            }
            row.appendChild(evid);

            $('#taskTable').append(row);
 
    }
};

let getCurrentUser = async function () {
    let response = await fetch("../api/users/" + currentUserId);
    let user = await response.json();

    return user;
};

let fillInFullName = async function () {
    let user = await getCurrentUser();

    $("#fullName").html(user.fname + " " + user.lname);
};

(async () => {
    await fillInFullName();

    await populateTaskTable();
    $('#tasks').DataTable();
})();