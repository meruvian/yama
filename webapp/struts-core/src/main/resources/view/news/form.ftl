<@s.form action="/backend/news/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="news.id" />
								
	<#assign id = request.session.getAttribute("cimandeSecurityUser").id />									 
							
	<@s.hidden name="news.user.id" value="${id}"/>
	<@s.textfield key="news.title" name="news.title" cssClass="span3" required="true"/>
	<@s.textarea key="news.content" rows=18  cssClass="textarea span8" name="news.content" required="true" />

	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>