<!-- uses in commentList
			 commentEdit
			 parts/showLists/showListsOfPost -->

<#macro show post parentPost postType>

<#include "/parts/security.ftl">

<div class="card mt-3 shadow border-secondary">
	<div class="card-header border-secondary media">
		<#include "/parts/components/post/postHeader.ftl">
		<#include "/parts/components/post/postHeaderDropdown.ftl">
		<#if postType == "repost">
			<div align="right">
				<#if !isEdit>
					<a href="/${post.id}/comments"><i class="fas fa-external-link-square-alt"></i></a>
				<#else>
					<a href="/${parentPost.id}/removeRepost">delete</a>
				</#if>
			</div>
		</#if>
	</div>
	<div class="card-body border-secondary">
		<#include "/parts/components/post/postBody.ftl">
	</div>
	<#if postType != "repost">
		<div class="card-footer bg-transparent border-secondary">
			<#include "/parts/components/post/postFooter.ftl">
		</div>
	</#if>
</div>
<script src="/customJs/formValidate.js"></script>

</#macro>