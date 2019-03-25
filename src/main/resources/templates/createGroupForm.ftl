<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<#include "/parts/security.ftl">

<div class="card shadow col-8">
	<div class="card-body">
		<div class="text-center card-text">
			<form method="post" enctype="multipart/form-data" onsubmit="return validateGroupCreate()">
				<div class="form-group mt-3">
					<input id="groupName" class="form-control col-mt ${(groupNameError??)?string('is-invalid', '')}" type="text" name="groupName" 
						   placeholder="group name" value="${registrationName?ifExists}" onfocus="disposeAlert('groupName')"> 
					<div id="groupNameError" class="invalid-feedback"><#if groupNameError??>${groupNameError}</#if></div>
					<input class="form-control mt-2" type="text" name="groupTitle" id="tags" placeholder="title (optional)">
					<input class="form-control mt-2" type="text" name="groupInformation" id="tags" placeholder="infirmation (optional)">
					<div class="input-group mt-2">
				  		<div class="custom-file">
				    		<input type="file" name="file" class="custom-file-input" id="postFile" aria-describedby="inputGroupFileAddon03"  >
				    		<label class="custom-file-label text-left" for="postFile">Chose group pic (optional)</label>
				  		</div>
					</div>
				</div>
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
				<button class="btn btn-primary mt-2" type="submit">create group</button>
			</form>	
		</div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>
<script src="/customJs/checkFilename.js"></script>

</@shell.htmlPage>