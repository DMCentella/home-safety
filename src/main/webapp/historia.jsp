<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Nuestra Historia | Home Safety");
%>
<%@ include file="/componentes/header.jspf"%>

<style>
.map-container {
	position: relative;
	width: 100%;
	height: 400px;
	border-radius: 12px;
	overflow: hidden;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

.map-buttons {
	display: flex;
	gap: 10px;
	margin-bottom: 15px;
	flex-wrap: wrap;
}

.map-buttons button {
	background-color: var(--hs-primary, #0b6efd);
	color: white;
	border: none;
	border-radius: 8px;
	padding: 8px 14px;
	cursor: pointer;
	transition: background .3s;
}

.map-buttons button:hover {
	background-color: #084298;
}
</style>

<div class="card card-soft">
	<div class="card-body p-4">
		<h1 class="h4 mb-3">Nuestra Historia</h1>
		<p class="text-muted">Home Safety nació con la misión de acercar
			la atención médica a cada familia de forma sencilla, confiable y
			accesible. A lo largo de los años hemos crecido, sumando
			profesionales de distintas especialidades y adoptando tecnologías que
			nos permiten conectar mejor con nuestros pacientes.</p>

		<div class="timeline">
			<ul class="list-group list-group-flush">
				<li class="list-group-item"><strong>2018 — Fundación:</strong>
					iniciamos como un pequeño consultorio enfocado en medicina general.
				</li>
				<li class="list-group-item"><strong>2020 — Expansión:</strong>
					incorporamos nuevas especialidades y abrimos nuestra primera sede
					adicional.</li>
				<li class="list-group-item"><strong>2022 —
						Digitalización:</strong> lanzamos la plataforma web para agendar y
					gestionar citas médicas en línea.</li>
				<li class="list-group-item"><strong>2025 — Actualidad:</strong>
					seguimos creciendo, con doctores, pacientes y consultorios
					conectados en una sola plataforma.</li>
			</ul>
		</div>

		<div class="mt-4">
			<img src="img/historia.jpg" class="img-fluid rounded mb-4"
				alt="Historia Home Safety">
		</div>

		<!-- Bloque Mapa -->
		<h2 class="h5 mt-4">Nuestras Sedes</h2>
		<p class="text-muted mb-3">Puedes encontrarnos en nuestras 3
			sedes:</p>

		<div class="map-buttons">
			<button onclick="loadMap('Trujillo')">Sede Trujillo</button>
			<button onclick="loadMap('Piura')">Sede Piura</button>
			<button onclick="loadMap('Chiclayo')">Sede Chiclayo</button>
		</div>

		<div class="map-container">
			<iframe id="mapFrame"
				src="https://www.google.com/maps?q=Ca.+Adolfo+Hidalgo+331,+Urb.+Santa+María,+Trujillo,+Perú&output=embed"
				width="100%" height="100%" style="border: 0;" allowfullscreen=""
				loading="lazy"></iframe>
		</div>
	</div>
</div>

<script>
	function loadMap(city) {
		const frame = document.getElementById("mapFrame");
		let url = "";

		switch (city) {
		case "Trujillo":
			url = "https://www.google.com/maps?q=Ca.+Adolfo+Hidalgo+331,+Urb.+Santa+María,+Trujillo,+Perú&output=embed";
			break;
		case "Piura":
			url = "https://www.google.com/maps?q=Urb.+Sta+Isabel+Calle+Sta+Maria+277+MZ+G+LT+04,+Piura,+Perú&output=embed";
			break;
		case "Chiclayo":
			url = "https://www.google.com/maps?q=Mz.+I+Lote+10+Urb.+La+Florida,+Chiclayo,+Perú&output=embed";
			break;
		}
		frame.src = url;
	}
</script>

<%@ include file="/componentes/footer.jspf"%>
