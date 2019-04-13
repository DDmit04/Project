<#import "parts/HTMLshell.ftl" as shell>

<@shell.htmlPage>

<#if loginAttention??>
	<div class="alert alert-warning col-8" role="alert">
		${loginAttention}
	</div>
</#if>

<div class="container-fluid">
	<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-5 text-center">
				<div class="card shadow">
					<div class="card-body">
						<#include "/parts/forms/loginForm.ftl">
					</div>
				</div>
			</div>
		<div class="col-md-6"></div>
	</div>
</div>
<script src="/customJs/formValidate.js"></script>
<script>
	window.onload = validateLogin;
</script>
</@shell.htmlPage>