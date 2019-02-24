function validateRegistration() {
	var username = document.getElementById("username");
	var password = document.getElementById("password");
	if (!username.value || !password.value) {
		if (!username.value) {
			username.classList.add('is-invalid');
			var usernameError = document.getElementById("usernameError");
			usernameError.innerHTML = 'username can not be empty!';
		}
		if (!password.value) {
			password.classList.add('is-invalid');
			var passwordError = document.getElementById("passwordError");
			passwordError.innerHTML = 'password can not be empty!';
		}
		return false;
	}
	return true;
}

function disposeAlertUsername() {
	var username = document.getElementById("username");
	var usernameError = document.getElementById("usernameError");
	usernameError.innerHTML = '';
	username.style.border = "1px solid grey";
	event.preventDefault();
}

function disposeAlertPassword() {
	var password = document.getElementById("password");
	var passwordError = document.getElementById("passwordError");
	passwordError.innerHTML = '';
	password.style.border = "1px solid grey";
	event.preventDefault();
}

function validateLogin() {
	if (window.location.href.substring(window.location.href.length - 5, window.location.href.length) == 'error') {
		var password = document.getElementById("password");
		password.classList.add('is-invalid');
		var username = document.getElementById("username");
		username.classList.add('is-invalid');
		var accesError = document.getElementById("accesError");
		accesError.innerHTML = 'wrong username or password!';
		return false;
	}
	return true;
}

function validateLogin() {
	if (window.location.href.substring(window.location.href.length - 5,
			window.location.href.length) == 'error') {
		var password = document.getElementById("password");
		password.classList.add('is-invalid');
		var username = document.getElementById("username");
		username.classList.add('is-invalid');
		var usernameError = document.getElementById("usernameError");
		usernameError.innerHTML = 'wrong username or password!';
	}
}