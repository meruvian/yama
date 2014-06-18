			<aside class="left-side sidebar-offcanvas">
				<!-- sidebar: style can be found in sidebar.less -->
				<section class="sidebar">
					<!-- Sidebar user panel -->
					<div class="user-panel">
						<div class="pull-left image">
							<!--
							<img src="img/avatar3.png" class="img-circle" alt="User Image" />
							-->
						</div>
						<div class="pull-left info">
							<p>Hello, <#if currentUser.name??>${currentUser.name.first!} ${currentUser.name.last!}<#else>${currentUser.username}</#if></p>

							<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
						</div>
					</div>
					
					<!-- sidebar menu: : style can be found in sidebar.less -->
					<ul class="sidebar-menu">
						<li class="active">
							<a href="<@s.url value="/" />">
								<i class="fa fa-dashboard"></i> <span>Dashboard</span>
							</a>
						</li>
						<#if isAdmin>
						<li class="treeview">
							<a href="#">
								<i class="fa fa-gears"></i>
								<span>Admin</span>
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li><a href="<@s.url value="/admin/users" />"><i class="fa fa-angle-double-right"></i> User</a></li>
								<li><a href="<@s.url value="/admin/roles" />"><i class="fa fa-angle-double-right"></i> Role</a></li>
							</ul>
						</li>
						</#if>
					</ul>
				</section>
				<!-- /.sidebar -->
			</aside>