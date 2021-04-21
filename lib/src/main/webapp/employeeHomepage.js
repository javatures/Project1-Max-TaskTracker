$(document).ready(function() {

    var table2 = $('#tasks').DataTable();

    populateTaskTable();
     
    console.log("test4");
} );

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

let populateTaskTable = function () {
    let currentUserId = $("#userId").val();

getJSON("api/tasks/employee/"+currentUserId, function (err, data) {
    if (err !== null) {
        alert('Something went wrong: ' + err);
    } else {
        console.log("Asdj");
        let tempJson = JSON.stringify(data, null, 4);
        let users = JSON.parse(tempJson);
        users.forEach(element => 
        {
            //need an if to check manager matches current user
            let row = document.createElement("tr");
            let taskId = document.createElement("td");
            taskId.innerHTML = element.taskId;
            let title = document.createElement("td");
            title.innerHTML = element.title;
            let body = document.createElement("td");
            body.innerHTML = element.body;
            let evidenceButton = document.createElement("td");
            evidenceButton.innerHTML = `<form method="post"><button name="submitEvidence" class="btn btn-primary" value="`+taskId+`" type="submit">Submit Evidence</button></form>`;
            let approvalButton = document.createElement("td");
            approvalButton.innerHTML = `<form method="post"><button name="submitForApproval" class="btn btn-primary" value="`+taskId+`" type="submit">Submit for Approval</button></form>`;

            row.appendChild(taskId);
            row.appendChild(title);
            row.appendChild(body);
            row.appendChild(evidenceButton);
            row.appendChild(approvalButton);

            $('#taskTable').append(row);
        });
    }
});
};