<html>
	<head>
		<title>Role</title>
	</head>
	<body>
		<content tag="header">Role</content>
		<content tag="headerDetail">Role List</content>
		<content tag="script">
		<script type="text/javascript" src="<@s.url value="/scripts/jquery/simple-pagination.js" />"></script>
		<script type="text/javascript">
		$(function() {
			$('#pagination').pagination({
				pages: ${roles.totalPages},
				currentPage: ${roles.number},
				hrefTextPrefix: '?q=${q!}&max=${max!}&page='
			});
		});
		</script>
		</content>
		<div class="row">
			<div class="col-md-6">
				<a href="<@s.url value="/admin/roles/-/edit" />" class="btn btn-default col-md-3">Add</a>
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
										<th>Name</th>
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<#list roles.content as r>
									<tr>
										<td><a href="<@s.url value="/admin/roles/${(r.name!'')?upper_case}/edit" />">${(r.name!"")?upper_case}</a></td>
										<td>${r.description!}</td>
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
								<div class="pagination alert pull-right">Found ${roles.numberOfElements} entries</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>