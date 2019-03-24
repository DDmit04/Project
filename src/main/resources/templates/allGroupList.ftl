<#import "parts/HTMLshell.ftl" as shell>
<#import "parts/showLists/showGroup.ftl" as groupList> 
 <@shell.htmlPage> 

<#include "parts/security.ftl">

<div class="col-9">
	<div class="tab-content shadow mt-2" id="nav-tabContent" style="background-color: white;">
		<div class="tab-pane fade show active" role="tabpanel" style="padding: 15px 5px;">
			<@groupList.groupList groups />	
		</div>
	</div>
</div>
</@shell.htmlPage>