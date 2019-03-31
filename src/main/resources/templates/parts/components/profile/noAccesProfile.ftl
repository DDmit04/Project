<div class="row ml-2">
	<div class="col-3">
		<div class="card border-secondary shadow" style="width: 280px;">
			<div class="card-body">
			  	<@picture.pic user "bigPic" "userProfile" />
			</div>
		</div>
	</div>
	<div class="col-8 ml-3">
		<div class="card border-secondary shadow mb-3">
			<div class="card-header">
				<div class="row h5">
					<div class="ml-2 mt-2">
						${user.username}
					</div>
					<div class="col dropdown" align="right">
						<#include "/parts/components/profile/userBanOptions.ftl">
					</div>
				</div>
			</div>
			<div class="card-body">
					
			</div>
		</div>
	<h4 class="display-4 ml-2 " align="left">this user blocked you</h4>
</div>