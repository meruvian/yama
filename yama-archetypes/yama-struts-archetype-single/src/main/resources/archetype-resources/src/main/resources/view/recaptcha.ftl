<div class="form-group ">
	<div class="controls" id="recaptcha"></div>
</div>
<script type="text/javascript" src="http://www.google.com/recaptcha/api/js/recaptcha_ajax.js"></script>
<script type="text/javascript">
Recaptcha.create("${reCaptchaPublicKey!}", "recaptcha", {
	theme: "white"
	// , callback: Recaptcha.focus_response_field
});
</script>