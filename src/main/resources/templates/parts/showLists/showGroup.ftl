<!-- uses in groupList
			 groupConnections
			 parts/show/showUserConnections
			 searchList  -->

<#macro groupList groups >

<#import "/parts/utils/showPics.ftl" as picture>
				
<#list groups as group>
	<ul class="list-group">
	  <li class="list-group-item d-flex justify-content-between align-items-center" style="border-left: none;
	 																					   border-right: none;
	 																					   border-top: none">
		   <div class="border-secondary media">
		  		<@picture.pic group "mediumPic" "groupList" />
				<div class="media-body">
					<a href="/groups/${group.id}" class="h4 ml-2">${group.groupName}</a>
					<div class="ml-2">
						${group.groupTitle}
					</div>
					<div class="ml-2">
					   	${group.subCount} subscribers
					</div>
				</div>
			</div>
		</li>
	</ul>
<#else>
	<h4 class="display-4 ml-2 " align="<#if !search??>left<#else>center</#if>">No groups :(</h4>
</#list>

</#macro>