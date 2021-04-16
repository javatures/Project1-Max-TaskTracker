let manSelect = document.getElementById("managerSelect");

var getJSON = function (url, callback) {
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

getJSON('api/users', function (err, data) {
    if (err !== null) {
        alert('Something went wrong: ' + err);
    } else {
        let tempJson = JSON.stringify(data, null, 4);
        let users = JSON.parse(tempJson);
        users.forEach(element => {
            if(element.userType == 1)
            {
                let option = document.createElement("option");
                option.text = element.fname +" "+ element.lname;
                option.value = element.userId;
                manSelect.appendChild(option);
            }
        });
    }
});