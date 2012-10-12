<div style="background-color: #FFFFFF; margin: 7px; padding:7px; -webkit-border-radius: 4px; margin-bottom: 10px;"> 
	<h2 style="margin: 0 0;">${news.title!}</h2>
	<font size="2"> Posted by <a href="#">${news.user.name.first!} ${news.user.name.last!}</a> on ${news.logInformation.createDate!}</font>
    
	<p style="text-align: justify;margin-top: 12px;">
		${news.content!}
	</p>
</div>

