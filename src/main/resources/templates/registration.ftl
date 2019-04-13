<#import "parts/HTMLshell.ftl" as shell> 
<@shell.htmlPage> 

<div class="container-fluid">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="card col-md-5 shadow">
			<div class="card-body">
				<div class="text-center card-text">
					<h1 class="h3 mb-3 font-weight-normal">Registration</h1>
				</div>
				<#include "parts/forms/rgistrationForm.ftl">
			</div>
		</div>
		<div class="col-md-6"></div>
	</div>
</div>
</@shell.htmlPage> 
