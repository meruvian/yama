<div style="background-color: #FFFFFF; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
	<h2 style="margin: 0 0;">${article.title!}</h2>
	<font size="2"> Posted by <a href="#">${article.user.name.first!} ${article.user.name.last!}</a> on ${article.logInformation.createDate!}</font>
    
	<p style="text-align: justify;margin-top: 12px;">
		${article.content!}
	</p>
</div>

