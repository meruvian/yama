<!-- <div class="hero-unit"> -->
	<div id="sums-carousel" class="carousel">
	  <div class="carousel-inner">
	    <div class="active item"><img src="/sliderimages/sekolah.jpg" alt=""></div>
	    <div class="item"><img src="/sliderimages/sby.jpg" alt=""></div>
	  </div>
	</div>
<!-- </div> -->

<hr>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span9">
		    <div>
		    	<hr>
				<ul style="min-height: 180px;">
					<li>
						<h4><@s.text name="label.last.edu.unit.text" /></h4>
						<p><img src="/images/smk.jpg" class="img-polaroid" style='float: left; margin-right: 10px; margin-bottom: 10px;width: 140px;height: 140px;'><h4><a href="<@s.url value="/module/eduunit/${school.id!}" />">${school.name!}</a></h4>${school.description!}</p>
					</li>
				</ul>
				<hr>
				<ul style="min-height: 180px;">
					<li>
						<h4><@s.text name="label.last.need.text" /></h4>
						<p> <img src="/images/rusak.jpg" class="img-polaroid" style='float: left; margin-right: 10px; margin-bottom: 10px;width: 140px;height: 140px;'><h4><a href="<@s.url value="/module/needs/detail/${reports[0].id!}" />">${reports[0].title!}</a></h4> ${reports[0].description!}</p>
					</li>
				</ul>
				<hr>
				<ul style="min-height: 180px;">
					<li>
						<h4><@s.text name="label.last.report.text" /></h4>
						<p> <img src="/images/bos.jpg" class="img-polaroid" style='float: left; margin-right: 10px; margin-bottom: 10px;width: 140px;height: 140px;'><h4><a href="#">Anggaran BOS 2012</a></h4>Dalam rangka pelaksanaan program BOS tahun 2012, Kementerian Pendidikan dan Kebudayaan telah menerbitkan Peraturan Menteri Pendidikan dan Kebudayaan Republik Indonesia Nomor 51 Tahun 2011 Tentang Petunjuk Teknis Penggunaan Dana BOS dan Laporan Keuangan BOS Tahun Anggaran 2012.</p>
					</li>
				</ul>
				<hr>
			</div>
    	</div>
    	<div class="span3">
	      	<#include "/view/recent_comments.ftl" />
    	</div>
  	</div>
</div>