<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">
		   
		    <div class="well">
		    	<h3>Cari Satuan Pendidikan</h3>
				<@s.form id="search-form" action="/module/eduunit/search" method="get" cssClass="form-horizontal" theme="bootstrap">
				<div class="control-group">
					<label class="control-label" for="province-id">Province</label>
					<div class="controls">
						<select name="province" id="province-id" class="span9 location" data-url="<@s.url value="/module/province.json?max=0&page=0" />" data-list="provinces" data-txt="name" data-val="id" >
							<option value="0">-- Choose --</option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="city-id">Kab/Kota</label>
					<div class="controls">
						<select name="city" id="city-id" class="span9 location" data-url="<@s.url value="/module/city.json?max=0&page=0" />" data-list="cities" data-txt="name" data-val="id">
							<option value="0">-- Choose --</option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="subdistrict-id">Kecamatan</label>
					<div class="controls">
						<select name="subdistrict" id="subdistrict-id" class="span9 location" data-url="<@s.url value="/module/subdistrict.json?max=0&page=0" />" data-list="subdistricts" data-txt="name" data-val="id">
							<option value="0">-- Choose --</option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="grade-id">Grade</label>
					<div class="controls">
						<select name="school.grade.id" id="grade-id" class="span9 location" data-url="<@s.url value="/module/schoolgrade.json?max=0&page=0" />" data-list="schoolGrades" data-txt="name" data-val="id">
							<option value="0">-- Choose --</option>
						</select>
					</div>
				</div>
				<div>
				<@s.hidden name="order" value="ASC" />
				<@s.hidden name="max" value="20" />
				<@s.hidden name="page" value="1" />
				<@s.hidden name="school.location.id" value="0" id="hdnlocation"/>
				<div class="control-group">
					<div class="controls">
						<input type="submit" class="btn btn-primary" value="Cari" />
					</div>
				</div>
				
				</div>
				</@s.form>
			</div>
			
			<div class="well">
				<h3>Melacak Kebutuhan</h3>
				<p>Untuk mengetahui sudah sampai mana kebutuhanyang dilaporkan silakan masukan kata kunci pada kolom berikut.</p>
				<@s.form id="search-form" action="" cssClass="form-horizontal" theme="bootstrap">
				<input id="floatright" type="text" class="span9">
				</@s.form>
			</div>
			
			<div class="well">
				<h3>Login</h3>
				<@s.form id="login-form" theme="bootstrap" action="/auth" cssClass="form-horizontal">
					<#if request.getParameter("error")??>
						<p class="alert alert-error"><@s.text name="frontend.login.failed" /></p>
					</#if>
					<@s.actionmessage theme="bootstrap" />
					<@s.textfield label="Username" name="username" cssClass="span9" /> 
					<@s.password label="Password" name="password" cssClass="span9" />
					<input type="hidden" name="redirectUri" value="<@s.property value="%{#parameters.redirect_uri}" />" />
					<div class="control-group">
						<div class="controls">
						<input type="submit" value="login" class="btn btn-primary" style="position: absolute; left: -9999px; width: 1px; height: 1px;" />
						<button value="Save" id="login-btn" class="btn btn-primary"><@s.text name="button.login" /></button>
						<p>Lupa kata sandi atau nama pengguna. Klik <a href="<@s.url value="/security/password/forgot" />">disini</a></p>
						</div>
					</div>
				</@s.form>
			</div>
			
			</div>
    	<div class="span6">
    		<div class="well">
    		<h3>Kebutuhan Terbaru<a class="btn btn-primary pull-right" href="<@s.url value="/module/needs/new" />">Laporkan Kebutuhan</a></h3>
    		<#list reports as c>
			<ul style="min-height: 180px;">
				<li>
					<h4>
						<a href="<@s.url value="/module/needs/detail/${c.id!}" />">${c.title!}</a>
					</h4>
					<div>
						<img src="/images/smk.jpg" class="img-polaroid" style="float: left; margin-right: 10px; margin-bottom: 10px;width: 140px;height: 140px;"> 
						<p>${c.logInformation.createDate?string("EEEE, MMMM dd, yyyy")}</p>
						<p class="pdesc">
						<#if c.description??>
							${c.description?replace("\n", "<br />")}
						</#if>
						</p>
					</div>
				</li>
			</ul>
			</#list>
    		</div>
    		
    		<div class="well">
    			<h3>Laporan Bantu Sekolahku</h3>
    			<p class="pdesc">Anda dapat melihat laporan statistik terkait dengan kebutuhan -kebutuhan yang dilaporkan pada sistem Bantu Sekolahku ini.
    			Untuk melihat laporan lengkap silakan klik disini <a href="">Laporan Bantu Sekolahku</a></p>
    		</div>

    		<div class="div-home">
    			<p>Belum memiliki akun?</p>
    			<a class="btn btn-primary" href="<@s.url value="/user/signup" />">Daftar Akun Baru</a>
    		</div>
    		
    	</div>
  	</div>
</div>

<script type="text/javascript">
$(function() {
	$('#province-id').change(function() {
		var a=$('#province-id').val();
		$('#hdnlocation').val(a);
	});
	$('#city-id').change(function() {
		var a=$('#city-id').val();
		$('#hdnlocation').val(a);
	});
	$('#subdistrict-id').change(function() {
		var a=$('#subdistrict-id').val();
		$('#hdnlocation').val(a);
	});
	function checkLocation(){
		if($("#subdistrict-id").val()!=0){
			$("#subdistrict-id").attr("name","school.location.id");
		} else if($("#city-id").val()!=0) {
			$("#city-id").attr("name","school.location.id");
		} else {
			$("#province-id").attr("name","school.location.id");
		}
	}
	function resetNameParam(){
		$("#subdistrict-id").attr("name","subdistrict");
		$("#city-id").attr("name","city");
		$("#province-id").attr("name","province");
	}
	$('#province-id').comboAjax({
		url : '<@s.url value="/module/province.json?max=0&page=0" />',
		change : function() {
			$('#city-id').comboAjax({
				url : '<@s.url value="/module/city/province/" />' + $('#province-id').val() + '.json?max=0&page=0',
				change : function() {
					$('#subdistrict-id').comboAjax({
						url : '<@s.url value="/module/subdistrict/city/" />' + $('#city-id').val() + '.json?max=0&page=0'
					});
				}
			});
		}
	});
	$('#grade-id').comboAjax();
	
	/**
	
	*/
});
</script>