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
            approveButton.innerHTML = `<form method="post"><button name="approveTask" class="btn btn-primary" value="` + task.taskId + `" type="submit">Approve Task</button></form>`;
            row.appendChild(approveButton);

            let denyButton = document.createElement("td");
            denyButton.innerHTML = `<form method="post"><button name="denyTask" class="btn btn-primary" value="` + task.taskId + `" type="submit">Deny Task</button></form>`;
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

            let lname = document.createElement("td");
            lname.innerHTML = user.lname;
            row.appendChild(lname);

            let fname = document.createElement("td");
            fname.innerHTML = user.fname;
            row.appendChild(fname);

            let uname = document.createElement("td");
            uname.innerHTML = user.userName;
            row.appendChild(uname);

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

let populateTaskTablePromise = async function () {
    let currentUserId = $("#userId").val();
    let taskUrl = "api/tasks/manager/" + currentUserId;


    let taskPromise = new Promise(async function (myResolve, myReject) {
        let req = new XMLHttpRequest();
        req.open('GET', taskUrl, true);
        req.responseType = 'json';
        req.onload = function () {
            if (req.status == 200) {
                myResolve(req.response);
            } else {
                myReject("JSON not Found");
            }
        };
        req.send();
    });

    taskPromise.then(
        async function (value) {
            let tempJson = JSON.stringify(value, null, 4);
            let tasks = JSON.parse(tempJson);
            tasks.forEach(data => {
                //if(data.currentStatus == 2)
                //{
                let row = document.createElement("tr");
                let taskId = document.createElement("td");
                taskId.innerHTML = data.taskId;

                row.appendChild(taskId);

                let userUrl = "api/users/" + data.reciever

                let userDataPromise = new Promise(async function (myResolve2, myReject2) {
                    let req2 = new XMLHttpRequest();
                    req2.open('GET', userUrl, true);
                    req2.responseType = 'json';
                    req2.onload = function () {
                        if (req2.status == 200) {
                            console.log(row);
                            myResolve2(req2.response, row);
                        } else {
                            myReject2("JSON not Found");
                        }
                    };
                    req2.send();
                });

                userDataPromise.then(
                    async function (value2, row2) {
                        console.log(row2);
                        let tempJson2 = JSON.stringify(value2, null, 4);
                        let user = JSON.parse(tempJson2);

                        let uname = document.createElement("td");
                        uname.innerHTML = user.userName;
                        let fname = document.createElement("td");
                        fname.innerHTML = user.fname;
                        let lname = document.createElement("td");
                        lname.innerHTML = user.lname;

                        row2.appendChild(uname);
                        row2.appendChild(fname);
                        row2.appendChild(lname);

                    },
                    async function (error2) {
                        alert('Something went wrong in inner promise: ' + error2);
                    });
                //await userDataPromise;

                let title = document.createElement("td");
                title.innerHTML = data.title;
                let body = document.createElement("td");
                body.innerHTML = data.body;
                let evid = document.createElement("td");
                evid.innerHTML = data.evidence;
                let approveButton = document.createElement("td");
                approveButton.innerHTML = `<form method="post"><button name="approveTask" class="btn btn-primary" value="` + data.taskId + `" type="submit">Approve Task</button></form>`;
                let denyButton = document.createElement("td");
                denyButton.innerHTML = `<form method="post"><button name="denyTask" class="btn btn-primary" value="` + data.taskId + `" type="submit">Deny Task</button></form>`;

                row.appendChild(title);
                row.appendChild(body);
                row.appendChild(evid);
                row.appendChild(approveButton);
                row.appendChild(denyButton);

                $('#taskTable').append(row);
                //}

            });
        },
        async function (error) { alert('Something went wrong in outer promise: ' + error); }
    );
};


let populateEmployeeTableOld = function () {
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

/*
$(document).ready(function () {
    var table1 = $('#employees').DataTable();
    //var table2 = $('#tasks').DataTable();

    //populateTaskTablePromise();
    populateEmployeeTable();
});
*/
let populateTaskTableOld = function () {
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