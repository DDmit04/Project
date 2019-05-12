<#macro imageRepost adminedGroups image>

<form method="post" action="/images/${image.id}/repost">
	<#if adminedGroups?size !=0>
		<div class="custom-control custom-checkbox inline">
			<input type="checkbox" class="custom-control-input my-1" name="groupRepostCheckbox" id="groupRepostCheckbox${image.id}"
				onchange="activateGroupSelect(${image.id});">
			<label class="custom-control-label my-1" for="groupRepostCheckbox${image.id}">
				repost to group
			</label>
		</div>
		<select class="custom-select my-2" id="groupRepostSelect${image.id}"  name="groupRepostSelect" disabled="disabled">
			<#list adminedGroups as group>
				<option value="${group.id}">${group.groupName}</option>
			</#list>
		</select>
	</#if>
	<input id="repostText" name="repostText" class="form-control col-mt" type="text" placeholder="repost text"> 
	<input id="repostTags" name="repostTags" class="form-control col-mt mt-2" type="text" placeholder="repost tags">
	<button class="btn btn-primary mt-2" type="submit">repost</button>
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</#macro>