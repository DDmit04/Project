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
	<form method="post" enctype="multipart/form-data">
		<input id="postText" class="form-control col-mt" type="text" name="text" placeholder="add text" value="<#if post??>${post.postText}</#if>"> 
		<input class="form-control mt-2" type="text" name="tags" placeholder="add tags" value="<#if post??>${post.tags}</#if>">
		<div class="input-group mt-3">
	  		<div class="custom-file">
	    		<input type="file" name="file" class="custom-file-input" id="inputGroupFile03" aria-describedby="inputGroupFileAddon03" >
	    		<label class="custom-file-label" for="inputGroupFile03">Choose file</label>
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
	<script type="application/javascript">
			$('.custom-file-input').on('change', function() { 
				   let fileName = $(this).val().split('\\').pop(); 
				   $(this).next('.custom-file-label').addClass("selected").html(fileName); 
				});
 	</script>
</div>