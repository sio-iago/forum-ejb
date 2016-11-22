function verifyLoggedUser() {
	if(window.localStorage.Authorization === undefined || window.localStorage.Authorization === null)
		window.location.href = 'login.html';
}

function doLogout() {
	delete window.localStorage.Authorization;
	verifyLoggedUser();
}

function addDismiss() {
	return '<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>';
}

function addAuthorization(request) {
	request.setRequestHeader("Authorization", window.localStorage.Authorization);
}

function emitError(message) {
	$('#messages').html('<div class="alert alert-danger">'+message+addDismiss()+'</div>');
}

function emitSuccess(message) {
	$('#messages').html('<div class="alert alert-success">'+message+addDismiss()+'</div>');
}

function emitInfo(message) {
	$('#messages').html('<div class="alert alert-info">'+message+addDismiss()+'</div>');
}