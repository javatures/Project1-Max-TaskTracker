$(document).ready(function () {
    var table1 = $('#employees').DataTable();
    var table2 = $('#tasks').DataTable();

    populateTaskTableMain();
    populateEmployeeTable();

    console.log("test4");
});

let getJSON = function (url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status === 200) {
            callback(null, xhr.response);
        } else {
            callback(status, xhr.response);
        }
    };
    xhr.send();
};

let populateEmployeeTable = function () {
    let currentUserId = $("#userId").val();

    getJSON("api/users", function (err, data) {
        if (err !== null) {
            alert('Something went wrong: ' + err);
        } else {
            let tempJson = JSON.stringify(data, null, 4);
            let users = JSON.parse(tempJson);
            users.forEach(element => {
                if (element.manager == currentUserId) {
                    let row = document.createElement("tr");
                    let fname = document.createElement("td");
                    fname.innerHTML = element.fname;
                    let lname = document.createElement("td");
                    lname.innerHTML = element.lname;
                    let uname = document.createElement("td");
                    uname.innerHTML = element.userName;
                    let taskButton = document.createElement("td");
                    taskButton.innerHTML = `<form method="post" action="managerCreateTask"><button name="createTask" class="btn btn-primary" value="` + element.userId + `" type="submit">Create New Task</button></form>`;
                    row.appendChild(fname);
                    row.appendChild(lname);
                    row.appendChild(uname);
                    row.appendChild(taskButton);

                    $('#employeeTable').append(row);
                }
            });
        }
    });
};

let populateTaskTable = function () {
    let currentUserId = $("#userId").val();

    getJSON("api/tasks/manager/" + currentUserId, function (err, data) {
        if (err !== null) {
            alert('Something went wrong: ' + err);
        } else {
            let tempJson = JSON.stringify(data, null, 4);
            let users = JSON.parse(tempJson);
            users.forEach(element => {
                let row = document.createElement("tr");
                let taskId = document.createElement("td");
                taskId.innerHTML = element.taskId;

                getJSON("api/users/" + element.reciever, function (err, data) {
                    if (err !== null) {
                        alert('Something went wrong: ' + err);
                    } else {
                        let innerTempJson = JSON.stringify(data, null, 4);
                        let innerUsers = JSON.parse(innerTempJson);
                        console.log(innerUsers);
                        let uname = document.createElement("td");
                        uname.innerHTML = innerUsers.userName;
                        let fname = document.createElement("td");
                        fname.innerHTML = innerUsers.fname;
                        let lname = document.createElement("td");
                        lname.innerHTML = innerUsers.lname;

                        row.appendChild(uname);
                        row.appendChild(fname);
                        row.appendChild(lname);
                    }
                });
                let title = document.createElement("td");
                title.innerHTML = element.title;
                let body = document.createElement("td");
                body.innerHTML = element.body;
                let evid = document.createElement("td");
                evid.innerHTML = element.evidence;
                let approveButton = document.createElement("td");
                approveButton.innerHTML = `<form method="post"><button name="approveTask" class="btn btn-primary" value="` + taskId + `" type="submit">Approve Task</button></form>`;
                let denyButton = document.createElement("td");
                denyButton.innerHTML = `<form method="post"><button name="denyTask" class="btn btn-primary" value="` + taskId + `" type="submit">Deny Task</button></form>`;

                row.appendChild(taskId);
                row.appendChild(title);
                row.appendChild(body);
                row.appendChild(evid);
                row.appendChild(approveButton);
                row.appendChild(denyButton);

                $('#taskTable').append(row);
            });
        }
    });
};

let populateTaskTableMain = function () {
    let row;
    populateTaskTableOne(row);
    //populateTaskTableTwo(row);
    //populateTaskTableThree(row);
}

let populateTaskTableOne = function (row) {
    let currentUserId = $("#userId").val();

    getJSON("api/tasks/manager/" + currentUserId, function (err, data) {
        if (err !== null) {
            alert('Something went wrong: ' + err);
        } else {
            let tempJson = JSON.stringify(data, null, 4);
            let users = JSON.parse(tempJson);
            users.forEach(element => {
                row = document.createElement("tr");
                let taskId = document.createElement("td");
                taskId.innerHTML = element.taskId;

                row.appendChild(taskId);
                console.log(element.reciever);
                populateTaskTableTwo(row, element.reciever);
                populateTaskTableThree(row, element);

                $('#taskTable').append(row);
            });
        }
    });
};

let populateTaskTableTwo = function (row, recieverId) {
    console.log(recieverId);

    getJSON("api/users/" + recieverId, function (err, data) {
        if (err !== null) {
            alert('Something went wrong: ' + err);
        } else {
            let innerTempJson = JSON.stringify(data, null, 4);
            let innerUsers = JSON.parse(innerTempJson);
            console.log(innerUsers);
            let uname = document.createElement("td");
            uname.innerHTML = innerUsers.userName;
            let fname = document.createElement("td");
            fname.innerHTML = innerUsers.fname;
            let lname = document.createElement("td");
            lname.innerHTML = innerUsers.lname;

            row.appendChild(uname);
            row.appendChild(fname);
            row.appendChild(lname);
        }
    });
};

let populateTaskTableThree = function (row, element) {
    let title = document.createElement("td");
    title.innerHTML = element.title;
    let body = document.createElement("td");
    body.innerHTML = element.body;
    let evid = document.createElement("td");
    evid.innerHTML = element.evidence;
    let approveButton = document.createElement("td");
    approveButton.innerHTML = `<form method="post"><button name="approveTask" class="btn btn-primary" value="` + element.taskId + `" type="submit">Approve Task</button></form>`;
    let denyButton = document.createElement("td");
    denyButton.innerHTML = `<form method="post"><button name="denyTask" class="btn btn-primary" value="` + element.taskId + `" type="submit">Deny Task</button></form>`;

    row.appendChild(title);
    row.appendChild(body);
    row.appendChild(evid);
    row.appendChild(approveButton);
    row.appendChild(denyButton);
};