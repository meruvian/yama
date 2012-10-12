<@s.form action="/backend/article/add" cssClass="form-horizontal" theme="bootstrap">	
	<@s.hidden name="article.id" />
								
	<#assign id = request.session.getAttribute("cimandeSecurityUser").id />									 
							
	<@s.hidden name="article.user.id" value="${id}"/>
	<@s.textfield key="article.title" name="article.title" cssClass="span3" required="true"/>
	<@s.textarea key="article.content" rows=18  cssClass="textarea span8" name="article.content" required="true" />

	<div class="form-actions">
		<input type="submit" class="btn btn-primary" value="Save" />
		<input type="reset" class="btn" value="Reset" />
	</div>
</@s.form>