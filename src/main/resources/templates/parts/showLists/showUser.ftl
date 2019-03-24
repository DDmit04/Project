<#import "/parts/utils/showPics.ftl" as picture>

<ul class="list-group">
	<li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
 																						 border-right: none;
 																						 border-top: none">
		   	<div class="border-secondary media">
				<@picture.pic user "smallPic" "user" />
			<div class="media-body">
				<a href="/${user.id}/profile" class="h6 ml-2">${user.username}</a>
			</div>
		</div>
	</li>
</ul>