<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">
		   
		    <div>
		    	<a style="margin: 0px 0px 10px 0px;" class="span12 btn btn-primary" href="<@s.url value="/module/eduunit" />"><h3 style="color: white;">Cari Satuan Pendidikan</h3></a>
			</div>
			
			<div>
				<a style="margin: 0px 0px 10px 0px;" class="span12 btn btn-primary" href="<@s.url value="/module/needs/search" />"><h3 style="color: white;">Melacak Kebutuhan</h3></a>
			</div>
			<div style="margin: 0px 0px -20px 0px;">&nbsp;</div>
			<div class="well">
				<h3>Masuk</h3>
				<@s.form id="login-form" theme="bootstrap" action="/auth" cssClass="form-horizontal">
					<#if request.getParameter("error")??>
						<p class="alert alert-error"><@s.text name="frontend.login.failed" /></p>
					</#if>
					<@s.actionmessage theme="bootstrap" />
					<@s.textfield label="Nama User" name="username" cssClass="span9" /> 
					<@s.password label="Kata Sandi" name="password" cssClass="span9" />
					<input type="hidden" name="redirectUri" value="<@s.property value="%{#parameters.redirect_uri}" />" />
					<div class="control-group">
						<div class="controls">
							<input type="submit" value="Masuk" class="btn btn-primary" style="position: absolute; left: -9999px; width: 1px; height: 1px;" />
							<button value="Save" id="login-btn" class="btn btn-primary"><@s.text name="button.login" /></button>
							<a href="#" id="fblogin" class="btn btn-primary" style="background-color: #0074CC; background-image: -ms-linear-gradient(top,#08C,#05C); background-image: -webkit-gradient(linear,0 0,0 100%,from(#08C),to(#05C)); background-image: -webkit-linear-gradient(top,#08C,#05C); background-image: -o-linear-gradient(top,#08C,#05C); background-image: -moz-linear-gradient(top,#08C,#05C); background-image: linear-gradient(top,#08C,#05C); background-repeat: repeat-x;" title="Masuk dengan akun Facebook">
							<#--
							<img src="<@s.url value="/images/fb-logo.jpg" />" alt="Login with Facebook Account" style="max-width: 30%; height: auto; border: 0; padding-left: 10px;" />
							-->
								&nbsp;<i class="icon-facebook icon-white"></i>&nbsp;
							</a>
							<p>Lupa kata sandi atau nama pengguna. Klik <a href="<@s.url value="/security/password/forgot" />">disini</a></p>
						</div>
					</div>
				</@s.form>
				<form name="socialform" method="post" id="socialform"></form>
			</div>
			
			</div>
    	<div class="span6">
    		<div>
    			<a style="margin: 0px 0px 10px 0px;" class="span12 btn btn-primary" href="<@s.url value="/module/needs/new" />"><h3 style="color: white;">Laporkan Kebutuhan</h3></a>
    		</div>
    		<div>
    			<a style="margin: 0px 0px 10px 0px;" class="span12 btn btn-primary" style href="#"><h3 style="color: white;">Laporan Bantu Sekolahku</h3></a>
			</div>
    		<div>
    			<p>Belum memiliki akun?</p>
    			<a class="btn btn-primary" href="<@s.url value="/user/signup" />">Daftar Akun Baru</a>
    		</div>
    		
    	</div>
  	</div>
</div>
<form name="socialform" action="/backend/user/login/facebook" method="post">
</form>
<script type="text/javascript">
$(function() {
	$('#fblogin').click(function(){
		$('#socialform').attr('action', '/backend/user/login/facebook');
		$('#socialform').submit();
		return false;
	});
	$('#login-btn').click(function() { $('#login-form').submit(); });
	$('input[name=username]').focus();
});
</script>