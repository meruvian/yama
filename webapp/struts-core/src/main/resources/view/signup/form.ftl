<@s.form action="/user/signup" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="signup.id" />
	<font color="#C24E4E">${notifEmail}</font>
	<h3><@s.text name="user.signup.login_information" /></h3>
	<@s.textfield key="user.signup.username" name="signUp.username" cssClass="span3" required="true"/>
	<@s.password key="user.signup.password" id="pass" name="signUp.password" cssClass="span3" required="true"/>
	<@s.password key="user.signup.confrim_password" id="cpass" name="cpass" cssClass="span3" />
	<hr />
	<h3><@s.text name="user.signup.personal_information" /></h3>
	<@s.textfield key="user.signup.firstname" name="signUp.name.first" cssClass="span3" required="true"/>
	<@s.textfield key="user.signup.lastname" name="signUp.name.last" cssClass="span3" required="true"/>
	<@s.textfield key="user.signup.email" name="signUp.email" cssClass="span3" required="true"/>
	<@s.textfield key="user.signup.mobile_phone" name="" cssClass="span3" />
	<@s.textfield key="user.signup.address" name="signUp.address.street1" cssClass="span4" />
	<@s.textfield name="signup.address.street2" cssClass="span4" />
	<div class="control-group">
		<label class="control-label"></label>
		<div class="controls">
			<font color="#C24E4E">${notifCaptcha}</font>
			<div id="recaptcha_div"></div>
		</div>
	</div>
	      
  </form>
	
	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="<@s.text name="user.signup.register" />"/>
	</div>
</@s.form>

      <script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>

      <!-- Wrapping the Recaptcha create method in a javascript function -->
      <script type="text/javascript">
         $(document).ready(function() {
           Recaptcha.create("6LdoBdESAAAAAHXfBTxpdn0gdlo3Ov3oLaOf4Jeh", "recaptcha_div", {
             theme: "clean",
             callback: Recaptcha.focus_response_field});
        });
      </script>
	
	<script>
    $('#cpass').keyup(function() {
  		var password = $("#pass").val();
    	var confirmPassword = $("#cpass").val();

    	if (password != confirmPassword){
    	$("#txtcheck").remove();
    	$("#cpass").after("<font color=\"#C24E4E\" id=\"txtcheck\">&nbsp;&nbsp;Passwords do not match!</font>");
    	}else{
    	$("#txtcheck").remove();
        $("#cpass").after("<font color=\"green\" id=\"txtcheck\">&nbsp;&nbsp;Passwords match.</font>");
        }
	});
      </script>