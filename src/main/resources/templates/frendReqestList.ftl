<#import "parts/HTMLshell.ftl" as shell> <@shell.htmlPage> 

<ul>
	<#list frendReqestsFrom as from>
	Your requests:
			<li class="list-group-item">
				<div>to user: ${from.reqiestTo.username}</div>
				<div>${from.frendReqestText}</div>
				<div>${from.creationDate}</div>
				<div>${from.id}</div>
				<a class="btn btn-primary" href="/${from.reqiestTo.id}/frendRequest/${from.id}/denial" role="button">denial</a>
			</li>
		
	<#else>
		No request sended by you
	</#list>
</ul>
<ul>
	<#list frendReqestsTo as to>
	reqest to you:
			<li class="list-group-item">
				<div>from user: ${to.reqiestFrom.username}</div>
				<div>${to.frendReqestText}</div>
				<div>${to.creationDate}</div>
				<div>${to.id}</div>
				<a class="btn btn-primary" href="/${to.reqiestFrom.id}/frendRequest/${to.id}/accept" role="button">accept</a>
				<a class="btn btn-primary" href="/${to.reqiestFrom.id}/frendRequest/${to.id}/denial" role="button">denial</a>
				
			</li>
	<#else>
		no reqest sended to you
	</#list>
</ul>

</@shell.htmlPage> 