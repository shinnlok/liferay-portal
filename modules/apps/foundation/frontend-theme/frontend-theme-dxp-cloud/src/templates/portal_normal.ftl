<!DOCTYPE html>

<#include init />

<html>

<head>
	<@liferay_util["include"] page=top_head_include />
</head>

<body class="dxp">
	<div class="senna-loading-bar"></div>

	<@liferay_util["include"] page=body_top_include />

	<@liferay_portlet["runtime"]
		portletName="dxp_cloud_portlet"
	/>

	<@liferay_util["include"] page=body_bottom_include />

	<@liferay_util["include"] page=bottom_include />
</body>
</html>