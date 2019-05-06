<#import "parts/HTMLshell.ftl" as shell> 
<#import "/parts/utils/showPics.ftl" as picture>
<@shell.htmlPage>

<div class="col-8">
	<div class="card shadow">
		<div class="card-body">
			<div class="text-center card-text">
				<h1 class="h3 mb-3 font-weight-normal">Your profile</h1>
			</div>
			<div class="form-group mt-3">
				<#if user??>
					<@picture.pic user "bigPic" "userPic" />
					<#include "/parts/forms/userRedactionForm.ftl">
				<#elseif group??>
					<@picture.pic group "bigPic" "groupPic" />
					<#include "/parts/forms/groupRedactionForm.ftl">
				</#if>
			</div>
		</div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>

</@shell.htmlPage>
