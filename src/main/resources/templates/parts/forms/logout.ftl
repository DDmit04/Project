<!-- uses in parts/bars/navbar -->

<form action="/logout" method="post">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	<button class="btn btn-primary" type="submit">Sign Out</button>
</form>