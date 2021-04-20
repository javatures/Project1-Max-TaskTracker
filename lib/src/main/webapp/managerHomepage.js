$(document).ready(function() {
    $('#tasks').DataTable();
    $('#employees').DataTable();
} );

let manSelect = document.getElementById("managerSelect");

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

getJSON("api/users", function (err, data) {
    if (err !== null) {
        alert('Something went wrong: ' + err);
    } else {
        let tempJson = JSON.stringify(data, null, 4);
        let users = JSON.parse(tempJson);
        users.forEach(element => 
        {
            //need an if to check manager matches current user
            let row = document.createElement("tr");
            let fname = document.createElement("td");
            fname.innerHTML = element.fname;
            let lname = document.createElement("td");
            lname.innerHTML = element.lname;
            let uname = document.createElement("td");
            uname.innerHTML = element.userName;
            let taskButton = document.createElement("td");
            taskButton.innerHTML = element.userId;
            row.appendChild(fname);
            row.appendChild(lname);
            row.appendChild(uname);
            row.appendChild(taskButton);

            $('#employeeTable').append(row);
        });
    }
});