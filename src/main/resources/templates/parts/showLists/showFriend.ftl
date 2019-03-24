<#import "/parts/utils/showPics.ftl" as picture>

<ul class="list-group shadow">
  <li class="list-group-item d-flex justify-content-between align-items-center">
    <div class="border-secondary media">
			<@picture.pic friend "smallPic" "userFriend" />
			<div class="media-body">
				<a href="/${friend.id}/profile" class="h6 ml-2">${friend.username}</a>
			</div>
		</div>
  </li>
</ul>