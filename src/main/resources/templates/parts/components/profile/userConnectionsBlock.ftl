<!-- parts/components/profile/noAccesProfile  -->

<a href="/${user.id}/profile/socialList/friends" role="button" class="btn btn-primary">
	 friends: <span class="badge badge-light">${user.friendCount}</span>
</a>
<a href="/${user.id}/profile/socialList/subscribesrs" role="button" class="btn btn-primary">
	 subscribesrs: <span class="badge badge-light">${user.subscribersCount}</span>
</a>
<a href="/${user.id}/profile/socialList/subscriptions" role="button" class="btn btn-primary">
	 subscriptions: <span class="badge badge-light">${user.subscriptionsCount}</span>
</a>
<a href="/${user.id}/profile/socialList/groups" role="button" class="btn btn-primary">
	 groups: <span class="badge badge-light">${user.groupSubscriptionsCount}</span>
</a>