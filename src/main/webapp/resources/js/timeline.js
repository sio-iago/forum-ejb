var posts = [];
var page = 0;

function clearTimeline() {
	$('#timeline').html('');
	page = 0;
}

function updateTimeline() {
	clearTimeline();
	queryData();
}

function showDetails(id) {
	if($('#comments'+id).is(':visible'))
		$('#comments'+id).slideUp("fast");
	else
		$('#comments'+id).slideDown("slow");
}

function mountColumn(id, username, title, description, comments) {
	
	var column = '<div class="col-lg-12"><div class="panel panel-default">'+
		'<div class="panel-heading"><div class="panel-title"><h3>'+username+': '+title+'</h3></div></div>'+ // Title
		'<div class="panel-body"><p>'+description+'<br><br><a href="javascript:void(0)" onclick="showDetails('+id+')" class="btn btn-sm btn-info">Show Comments</a></p></div>'; // Body
		
	column += '<div class="panel-footer" id="comments'+id+'" style="display:none"><h4>Comments</h4><hr>';
	
	column += '<div id="commentsBody'+id+'">';
	if(comments !== undefined && comments !== null && comments.length > 0) {
		for(var i=0; i<comments.length; i++) {
			column += '<p><b>'+comments[i].username+'</b> '+comments[i].message+'</p><hr>';
		}
	}
	else {
		column += '<p id="no-comments-'+id+'">No comments yet!'+'<hr></p>';
	}
	column += '</div>';
	
	column += '<p><form id="commentsForm'+id+'"><textarea name="message" rows="3" class="editArea"></textarea><br><br><a href="javascript:void(0)" class="btn btn-success btn-small" onclick="postComment('+id+')">Post</a></form></p>';
	
	// .panel-footer
	column += '</div>';
	
	// .panel .col-lg-12
	column += '</div></div>';
	
	return column;
}

function parseData(data) {
	
	var timelineDiv = $('#timeline');
	
	var columns = '';
	
	for(var i=0; i<data.length; i++) {
		var nextData = data[i];
		
		if(nextData.title !== undefined && nextData.description !== undefined)
			columns += mountColumn(nextData.id, nextData.username, nextData.title, nextData.description, nextData.comments);
	}
	
	timelineDiv.html(columns+timelineDiv.html());
	
}

function parseDataBottom(data) {
	
	var timelineDiv = $('#timeline');
	
	var columns = '';
	
	for(var i=0; i<data.length; i++) {
		var nextData = data[i];
		
		if(nextData.title !== undefined && nextData.description !== undefined)
			columns += mountColumn(nextData.id, nextData.username, nextData.title, nextData.description, nextData.comments);
	}
	
	timelineDiv.html(timelineDiv.html()+columns);
	
}

function addAuthorization(request) {
    request.setRequestHeader("Authorization", window.localStorage.Authorization);
}

function queryData(page) {
	if(page === undefined || page === null) {
		page = 0
	}
	
	$.ajax({
		type: "GET",
		beforeSend: addAuthorization,
        //the url where you want to sent the userName and password to
        url: routes.posts + '?page='+page,
        contentType : 'application/json',
        success: parseData
	});
}

function queryDataBottom(page) {
	if(page === undefined || page === null) {
		page = 0
	}
	
	$.ajax({
		type: "GET",
		beforeSend: addAuthorization,
        //the url where you want to sent the userName and password to
        url: routes.posts + '?page='+page,
        contentType : 'application/json',
        success: parseDataBottom
	});
}

function clearModal() {
	$('#newPostForm').trigger('reset');
}

function createPost() {
	
	var reqObject = $('#newPostForm').serializeObject();
	
	$.ajax({
        type: "POST",
        //the url where you want to sent the userName and password to
        url: routes.posts,
        beforeSend: addAuthorization,
        contentType : 'application/json',
        async: false,
        //json object to sent to the authentication url
        data: JSON.stringify(reqObject),
        success: function (result) { 
        	clearModal();
        	parseData([result]);
        	emitSuccess('Post created!!!');
        }
    });
}

function showMorePosts() {
	queryDataBottom(++page);
}

function appendComment(id, username, message) {
	$('p').remove('#no-comments-'+id);
	
	var commentsN = $('#commentsBody'+id);
	commentsN.html('<p><b>'+username+':</b> '+message+'</p><hr>'+commentsN.html());
}

function postComment(id, data) {
	var reqObject = $('#commentsForm'+id).serializeObject();
	
	$.ajax({
        type: "POST",
        //the url where you want to sent the userName and password to
        url: routes.comments+'/'+id,
        beforeSend: addAuthorization,
        contentType : 'application/json',
        async: false,
        //json object to sent to the authentication url
        data: JSON.stringify(reqObject),
        success: function (result) { 
        	appendComment(id, result.username, result.message);
        	$('#commentsForm'+id).trigger('reset')
        }
    });
}

