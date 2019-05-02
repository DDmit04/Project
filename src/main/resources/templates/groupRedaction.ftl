<#import "parts/HTMLshell.ftl" as shell> 
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage>

<div class="col-8">
	<div class="card shadow">
		<div class="card-body">
			<div class="text-center card-text">
				<h1 class="h3 mb-3 font-weight-normal">Group information</h1>
			</div>
			<div class="form-group mt-3">
				<@picture.pic group "bigPic" "groupPic" />
				<#include "/parts/forms/groupRedactionForm.ftl">
			</div>
		</div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>

</@shell.htmlPage>