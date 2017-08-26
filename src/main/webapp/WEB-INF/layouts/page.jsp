<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE>

<html lang="en">  

<head>
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
    
	<title>MedicalLab</title>
	
	<s:url value='/css/lib/font-awesome/4.7.0/font-awesome.min.css'  var="minFontAwesomeCSSUrl" />
	<s:url value='/css/lib/bootstrap/4.0.0-beta/bootstrap.min.css'   var="minBootstrapCSSUrl" />
	<s:url value='/css/lib/animate.css/3.5.2/animate.min.css'        var="minAnimateCSSUrl" />
	
	<s:url value='/css/app.css' var="appCSSUrl" />
	
	<s:url value='/images/logo/medicallab-logo.png'                  var="logoUrl" />
	<s:url value='/images/favicon/medicallab-logo.png'               var="faviconUrl" />
  
  
	<link rel="stylesheet" type="text/css" href="${minFontAwesomeCSSUrl }" >
	<link rel="stylesheet" type="text/css" href="${minBootstrapCSSUrl }" >
  
	<link rel="stylesheet" type="text/css" href="${appCSSUrl }" >

  <link rel="shortcut icon" href="${faviconUrl }" >
</head>

<body>
  <!-- Page Layout HTML -->
  <header id="pageHeader">
    <t:insertAttribute name="pageHeader"></t:insertAttribute>
  </header>
  
  <section id="pageAlert">
    <t:insertAttribute name="pageAlert"></t:insertAttribute>
	</section>
  
  <main id="pageBody">
    <t:insertAttribute name="pageBody"></t:insertAttribute>
  </main>
  
  <footer id="pageFooter">
    <t:insertAttribute name="pageFooter"></t:insertAttribute>
  </footer>
  
  
  <!-- Javascript Includes -->
  <s:url value='/js/lib/jquery/3.2.1/jquery-3.2.1.min.js'       var="minjQueryJSUrl" />
  <s:url value='/js/lib/popper.js/1.11.0/popper-1.11.0.min.js'  var="minPopperJSUrl" />
  <s:url value='/js/lib/tether/1.4.0/tether.min.js'             var="minTetherJSUrl" />
  <s:url value='/js/lib/bootstrap/4.0.0-beta/bootstrap.min.js'  var="minBootstrapJSUrl" />
  <s:url value='/js/lib/font-awesome/4.7.0/facc121533.js'       var="minFontAwesomeJSUrl" />
  <s:url value='/js/lib/urijs/1.18.12/URI.min.js'               var="minURIJSUrl" />
  <s:url value='/js/lib/urijs/1.18.12/jquery.URI.min.js'        var="minjQueryURIJSUrl" />

  <s:url value='/js/app.js' var="appJSUrl" />


  <script type="text/javascript" src='${minjQueryJSUrl }'></script>
  <script type="text/javascript" src='${minPopperJSUrl }'></script>
  <script type="text/javascript" src='${minTetherJSUrl }'></script>
  <script type="text/javascript" src='${minBootstrapJSUrl }'></script>
  <script type="text/javascript" src='${minURIJSUrl }'></script>
  <script type="text/javascript" src='${minjQueryURIJSUrl }'></script>
  
  <script type="text/javascript" src='${appJSUrl }'></script>
</body>
</html>