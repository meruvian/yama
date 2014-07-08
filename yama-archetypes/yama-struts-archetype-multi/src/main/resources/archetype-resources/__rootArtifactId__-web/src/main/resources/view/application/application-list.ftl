<html>
	<head>
		<title><@s.text name="page.application.title" /></title>
	</head>
	<body>
		<content tag="header"><@s.text name="page.application.header" /></content>
		<content tag="script">
		<script type="text/javascript" src="<@s.url value="/scripts/jquery/simple-pagination.js" />"></script>
		<script type="text/javascript">
		$(function() {
			$('#pagination').pagination({
				pages: ${apps.totalPages},
				currentPage: ${apps.number},
				hrefTextPrefix: '?q=${q!}&max=${max!}&page='
			});
		});
		</script>
		</content>
		<div class="row">
			<div class="col-md-6">
				<a href="<@s.url value="/applications/-/edit" />" class="btn btn-default col-md-3"><@s.text name="button.main.register" /></a>
			</div>
			<div class="col-md-6">
				<@s.form theme="bootstrap" method="GET">
					<div class="form-group col-md-10">
						<input name="q" value="${q!}" type="text" class="form-control" placeholder="<@s.text name="button.main.search" />...">
					</div>
					<@s.submit cssClass="btn btn-success col-md-2" value="%{getText('button.main.search')}" />
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
										<th><@s.text name="label.application.id" /></th>
										<th><@s.text name="label.application.name" /></th>
										<th><@s.text name="label.application.site" /></th>
									</tr>
								</thead>
								<tbody>
									<#list apps.content as r>
									<tr>
										<td><a href="<@s.url value="/applications/${r.id!}/edit" />">${r.id!}</a></td>
										<td>${r.displayName!}</td>
										<td>${r.site!}</td>
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
								<div class="pagination alert pull-right">Found ${apps.numberOfElements} entries</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>