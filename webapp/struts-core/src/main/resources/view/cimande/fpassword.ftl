<div>
	<@s.form id="login-form" theme="bootstrap" action="forgot" cssClass="form-horizontal" method="post">
		<p>Silakan masukkan alamat email Anda, kami akan segera mengirimkan tautan untuk merubah password Anda.</p>
		<br/>
		<@s.textfield label="Email" name="email" required="true" cssClass="span3" /> </td>
		<input type="submit" value="Kirim" class="btn btn-primary" style=""/>
	</@s.form>
</div>