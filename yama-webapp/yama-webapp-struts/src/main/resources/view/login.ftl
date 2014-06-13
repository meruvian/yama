		<div class="form-box" id="login-box">
			<div class="header">Sign In</div>
			<form action="<@s.url value="${request.servletPath}/do" />" method="post">
				<div class="body bg-gray">
					<div class="form-group">
						<input type="text" name="username" class="form-control" placeholder="User ID"/>
					</div>
					<div class="form-group">
						<input type="password" name="password" class="form-control" placeholder="Password"/>
					</div>		  
					<div class="form-group">
						<input type="checkbox" name="_spring_security_remember_me"/> Remember me
					</div>
				</div>
				<div class="footer">															   
					<button type="submit" class="btn bg-olive btn-block">Sign me in</button>  
					
					<p><a href="#">I forgot my password</a></p>
					
					<a href="<@s.url value="/register" />" class="text-center">Register a new membership</a>
				</div>
			</form>

			<div class="margin text-center">
				<span>Sign in using social networks</span>
				<br />
				<a class="btn bg-light-blue btn-circle" href="<@s.url value="/login/social/facebook/auth" />">
					<i class="fa fa-facebook"></i>
				</a>
				<#--<a class="btn bg-aqua btn-circle"><i class="fa fa-twitter"></i></a>-->
				<a class="btn bg-red btn-circle" href="<@s.url value="/login/social/google/auth" />">
					<i class="fa fa-google-plus"></i>
				</a>
			</div>
		</div>