function validateRegistration() {
    var username = document.getElementById("username");
    var usernameError = document.getElementById("usernameError");
    var email = document.getElementById("userEmail");
    var emailError = document.getElementById("userEmailError");
    var validatePasswordsRes = validatePasswords();
    var validateEmailRes = validateEmail();
	if (!username.value || !email.value || !validatePasswordsRes || !validateEmailRes) {
		if (!username.value) {
			username.classList.add('is-invalid');
			usernameError.innerHTML = 'username can not be empty!';
        }
        if (!email.value) {
			email.classList.add('is-invalid');
			emailError.innerHTML = 'email can not be empty!';
        }
		return false;
	}
	return true;
}

function validateEmail() {
    var email = document.getElementById("userEmail");
    var emailError = document.getElementById("userEmailError");
    var re = /\S+@\S+\.\S+/;
    if(!re.test(email.value)) {
        email.classList.add('is-invalid');
		emailError.innerHTML = 'wrong email style!';
        return false;
    }
    return true;
}

function validateChangeEmail() {
    var code = document.getElementById("changeEmailCode");
    var codeError = document.getElementById("changeEmailCodeError");
    var newEmail = document.getElementById("userEmail");
    var newEmailError = document.getElementById("userEmailError");
    var validateEmailRes = validateEmail();
    if(!code.value || !newEmail.value || !validateEmailRes) {
        if(!code.value) {
            code.classList.add('is-invalid');
			codeError.innerHTML = 'code is empty!';
        }
        if(!newEmail.value) {
            newEmail.classList.add('is-invalid');
			newEmailError.innerHTML = 'new email is empty!';
        }
        return false;
    }
    return true;
}

function validatePasswords() {
    var password = document.getElementById("password");
    var passwordError = document.getElementById("passwordError");
    var retypePassword = document.getElementById("retypePassword");
    var retypePasswordError = document.getElementById("retypePasswordError");
	if (!password.value || password.value != retypePassword.value) {
		if(password != retypePassword) {
			retypePassword.classList.add('is-invalid');
			retypePasswordError.innerHTML = 'passwords are different!';
		}
		if (!password.value) {
			password.classList.add('is-invalid');
			passwordError.innerHTML = 'password can not be empty!';
		}
		return false;
	}
	return true;
}

function validatePost() {
	var tags = document.getElementById("tags");
    var postText = document.getElementById("postText");
    var postTextError = document.getElementById("postTextError");
	var file = document.getElementById("postFile");
	if(!tags.value  && !postText.value && !file.value) {
		postText.classList.add('is-invalid');
		postTextError.innerHTML = 'post can not be empty!';
		return false;
	}
	return true;
}

function validateLogin() {
	if (window.location.href.substring(window.location.href.length - 5, window.location.href.length) == 'error') {
		var password = document.getElementById("password");
		password.classList.add('is-invalid');
		var username = document.getElementById("username");
		username.classList.add('is-invalid');
		var accesError = document.getElementById("usernameError");
		accesError.innerHTML = 'wrong username or password!';
		return false;
	}
	return true;
}

function validateComment() {
    var comment = document.getElementById("commentText");
    var commentTextError = document.getElementById("commentTextError");
	var commentPic = document.getElementById("commentPic");
	if(!comment.value && !commentPic.value) {
		comment.classList.add('is-invalid');
		commentTextError.innerHTML = 'comment can not be empty!';
		return false;
	}
	return true;
}

function validateGroupCreate() {
    var groupName = document.getElementById("groupName");
    var groupNameError = document.getElementById("groupNameError");
	if(!groupName.value) {
		groupName.classList.add('is-invalid');
		groupNameError.innerHTML = 'group name can not be empty!';
		return false;
	}
	return true;
}

function validateChatCreate() {
    var groupName = document.getElementById("chatName");
    var groupNameError = document.getElementById("chatNameError");
	if(!groupName.value) {
		groupName.classList.add('is-invalid');
		groupNameError.innerHTML = 'chat name can not be empty!';
		return false;
	}
	return true;
}

function disposeAlert(elementName) {
	var element = document.getElementById(elementName);
	var elementError = element.nextSibling;
	elementError.innerHTML = '';
	element.classList.remove('is-invalid');
	event.preventDefault();
}