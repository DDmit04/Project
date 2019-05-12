<#macro modal image picPath fullFooter>

<div class="modal-dialog" role="document">
	<div class="modal-content">
		<div class="align-right mr-2 mt-1">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<div class="modal-body">
			<img src="${picPath}/${image.imgFileName}" class="card-img-top">
		</div>
		<div class="card-footer bg-transparent">
			<#if fullFooter>
				<#include "/parts/components/image/imageFooter.ftl">
			<#else>
				<a role="button" class="btn btn-primary mr-3" href="/images/${image.id}/comments">
					go to image
				</a>
			</#if>
		</div>
	</div>
</div>

</#macro>