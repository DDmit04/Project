<form method="post" enctype="multipart/form-data" action="">
	<input id="commentText" class="form-control col-mt border-secondary shadow" type="text" name="commentText" placeholder="comment..." value="<#if comment??>${comment.commentText}</#if>"> 
	<div id="commentTextError" class="invalid-feedback"></div>
	<button class="btn btn-primary mt-2" type="submit">
		add
	</button>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>	