<html>
	<head>
		<title>User</title>
	</head>
	<body>
		<content tag="header">User</content>
		<content tag="headerDetail">Active Users</content>
		<content tag="script">
		<script type="text/javascript" src="<@s.url value="/scripts/jquery/simple-pagination.js" />"></script>
		<script type="text/javascript">
		$(function() {
			$('#pagination').pagination({
				pages: ${users.totalPages},
				currentPage: ${users.number},
				hrefTextPrefix: '?q=${q!}&max=${max!}&page='
			});
		});
		</script>
		</content>
		<div class="row">
			<div class="col-md-6">
				<a href="<@s.url value="/admin/users/-/edit" />" class="btn btn-default col-md-3">Add</a>
			</div>
			<div class="col-md-6">
				<@s.form theme="bootstrap" method="GET">
					<div class="form-group col-md-10">
						<input name="q" value="${q!}" type="text" class="form-control" placeholder="Search...">
					</div>
					<@s.submit cssClass="btn btn-success col-md-2" value="Search" />
				</@s.form>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header">
					</div>
					<div class="box-body">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>Username</th>
										<th>Full Name</th>
										<th>Email</th>
									</tr>
								</thead>
								<tbody>
									<#list users.content as u>
									<tr>
										<td><a href="<@s.url value="/admin/users/${u.username!}/edit" />">${u.username}</a></td>
										<td>${(u.name!).first!} ${(u.name!).last!}</td>
										<td>${u.email!}</td>
									</tr>
									</#list>
								</tbody>
							</table>
						</div>
					</div>
					<div class="box-footer">
						<div class="row">
							<div class="col-md-6">
								<div id="pagination"></div>
							</div>
							<div class="col-md-6">
								<div class="pagination alert pull-right">Found ${users.numberOfElements} entries</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>