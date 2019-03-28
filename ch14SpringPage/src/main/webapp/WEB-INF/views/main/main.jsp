<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<!-- 상단 컨텐츠 시작 -->
<div class="custom-center-content">
	<div class="container">
		<div class="row">
			<div class="col-md-3 col-lg-3">
				<h2>Getting started</h2>
				<p>An overview of Bootstrap, how to download and use, basic
					templates and examples, and more.</p>
				<p>
					<a href="#myModal" class="btn btn-primary btn-lg">Learn more</a>
				</p>
			</div>
			<div class="col-md-9 col-lg-9">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						<li data-target="#carousel-example-generic" data-slide-to="3"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner custom-image-space">
						<div class="item active">
							<img src="${pageContext.request.contextPath}/resources/images/center_img01.jpg" alt="First slide">
							<!-- <div class="carousel-caption">
								<h3>Koala</h3>
								<p>Animal World!!</p>
							</div> -->
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/resources/images/center_img02.jpg" alt="Second slide">
							<!-- <div class="carousel-caption">
								<h3>Lighthouse</h3>
								<p>Animal World!!</p>
							</div> -->
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/resources/images/center_img03.jpg" alt="Third slide">
							<!-- <div class="carousel-caption">
								<h3>Penguins</h3>
								<p>Animal World!!</p>
							</div> -->
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/resources/images/center_img04.jpg" alt="Third slide">
							<!-- <div class="carousel-caption">
								<h3>Tulips</h3>
								<p>Animal World!!</p>
							</div> -->
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic"
						role="button" data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left"></span>
					</a> <a class="right carousel-control"
						href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 상단 컨텐츠 시작 -->
<!-- 중앙 컨텐츠 시작 -->
<div class="container">
	<div class="row">
		<div class="col-lg-8">

			<!-- 패널 리스트 시작 -->
			<div class="panel panel-info">
				<!-- 패널 해드 시작 -->
				<div class="panel-heading"><span id="my_weather"></span> <span id="my_address">여긴 어디?</span></div>
				<div id="map" style="width:100%;height:235px;"></div>
			</div>
		</div>
		<div class="col-lg-4">
			<!-- 패널 리스트 시작 -->
			<div class="panel panel-info">
				<!-- 패널 해드 시작 -->
				<div class="panel-heading">Panel heading</div>
				<!-- 리스트 시작 -->
				<div class="list-group">
					<a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">
							List group item heading <span class="badge">14</span>
						</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a> <a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">List group item heading</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a> <a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">List group item heading</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a>
				</div>
			</div>
		</div>
	</div>

	<!-- 썸네일 시작 -->
	<div class="row">
		<div class="col-sm-6 col-lg-3">
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/resources/images/center_img01.jpg">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>Cras justo odio, dapibus ac facilisis in, egestas eget
						quam. Donec id elit non mi porta gravida at eget metus. Nullam id
						dolor id nibh ultricies vehicula ut id elit.</p>
				</div>
			</div>
		</div>

		<div class="col-sm-6 col-lg-3">
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/resources/images/center_img02.jpg">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>Cras justo odio, dapibus ac facilisis in, egestas eget
						quam. Donec id elit non mi porta gravida at eget metus. Nullam id
						dolor id nibh ultricies vehicula ut id elit.</p>
				</div>
			</div>
		</div>

		<div class="col-sm-6 col-lg-3">
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/resources/images/center_img03.jpg">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>Cras justo odio, dapibus ac facilisis in, egestas eget
						quam. Donec id elit non mi porta gravida at eget metus. Nullam id
						dolor id nibh ultricies vehicula ut id elit.</p>
				</div>
			</div>
		</div>

		<div class="col-sm-6 col-lg-3">
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/resources/images/center_img04.jpg">
				<div class="caption">
					<h3>Thumbnail label</h3>
					<p>Cras justo odio, dapibus ac facilisis in, egestas eget
						quam. Donec id elit non mi porta gravida at eget metus. Nullam id
						dolor id nibh ultricies vehicula ut id elit.</p>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 중앙 컨텐츠 끝 -->



