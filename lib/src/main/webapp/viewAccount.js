let currentUserId = $("#userId").val();

let getCurrentUser = async function () {
    let response = await fetch("api/users/" + currentUserId);
    let user = await response.json();

    return user;
};

let populateInputs = async function()
{
    let user = await getCurrentUser();

    $('#usernameInput').val(user.userName);
    $('#passwordInput').val(user.userPassword);
    $('#fnameInput').val(user.fname);
    $('#lnameInput').val(user.lname);
};

(async () => {
    await populateInputs();
})();