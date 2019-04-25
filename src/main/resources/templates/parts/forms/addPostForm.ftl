<!-- uses in postList
			 group 
			 parts/components/profile/noAccesProfile -->

<#include "/parts/security.ftl">

<a class="btn btn-primary btn-lg btn-block mb-3" data-toggle="collapse" href="#collapseExample" role="button" 
aria-expanded="false" aria-controls="collapseExample">
	<#if post??>
		edit post
	<#else>
		add	post
	</#if>
</a>
<div class="collapse <#if isEdit>show</#if>" id="collapseExample">
	<form method="post" enctype="multipart/form-data" onsubmit="return validatePost()">
		<input id="postText" class="form-control col-mt" type="text" name="postText" placeholder="add text" value="<#if post??>${post.postText}</#if>"
			onfocus="disposeAlert('postText')"> 
		<div id="postTextError" class="invalid-feedback"></div>
		<input class="form-control mt-2" type="text" name="tags" id="tags" placeholder="add tags" value="<#if post??>${post.tags}</#if>">
		<div class="input-group mt-2">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="postFile" aria-describedby="inputGroupFileAddon03" >
	    		<label class="custom-file-label" for="postFile">Choose file</label>
	  		</div>
		</div>
		<button class="btn btn-primary mt-2" type="submit">
			<#if post??>
					edit
			<#else>
					add	
			</#if>
		</button>
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
	</form>	
	<script src="/customJs/formValidate.js"></script>
	<script src="/customJs/checkFilename.js"></script>
</div>