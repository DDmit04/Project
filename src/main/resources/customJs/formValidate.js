function validateRegistration() {
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	if (!username.value || !password.value) {
		if (!username.value) {
			username.style.border = "1px solid red";
		}
		if (!password.value) {
			password.style.border = "1px solid red";
		}
		return false;
	}
	return true;
}

function disposeAlertUsername() {
	var username = document.getElementById("username");
	username.style.border = "1px solid grey";
	usernameError = null;
	event.preventDefault();
}

function disposeAlertPassword() {
	var password = document.getElementById("password");
	password.style.border = "1px solid grey";
	event.preventDefault();
}