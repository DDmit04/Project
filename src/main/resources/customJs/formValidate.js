function validate() {
	var text = document.getElementById("postText");
	if (!text.value) {
		text.style.border = "1px solid red";
		text.write("lul");
		return false;
	}
	return true;
}