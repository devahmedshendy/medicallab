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
	
	<s:url value='/css/lib/font-awesome/4.7.0/font-awesome.min.css'  var="minFontAwesomeCSSUri" />
	<s:url value='/css/lib/bootstrap/4.0.0-beta/bootstrap.min.css'   var="minBootstrapCSSUri" />
	<s:url value='/css/lib/animate.css/3.5.2/animate.min.css'        var="minAnimateCSSUri" />
	
	<s:url value='/css/app.css' var="appCSSUri" />
	
	<s:url value='/images/logo/medicallab-logo.png'                  var="logoUri" />
	<s:url value='/images/favicon/medicallab-logo.png'               var="faviconUri" />
  
  
	<link rel="stylesheet" type="text/css" href="${minFontAwesomeCSSUri }" >
	<link rel="stylesheet" type="text/css" href="${minBootstrapCSSUri }" >
  
	<link rel="stylesheet" type="text/css" href="${appCSSUri }" >

  <link rel="shortcut icon" href="${faviconUri }" >
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
  <s:url value='/js/lib/jquery/3.2.1/jquery-3.2.1.min.js'       var="minjQueryJSUri" />
  <s:url value='/js/lib/popper.js/1.11.0/popper-1.11.0.min.js'  var="minPopperJSUri" />
  <s:url value='/js/lib/tether/1.4.0/tether.min.js'             var="minTetherJSUri" />
  <s:url value='/js/lib/bootstrap/4.0.0-beta/bootstrap.min.js'  var="minBootstrapJSUri" />
  <s:url value='/js/lib/font-awesome/4.7.0/facc121533.js'       var="minFontAwesomeJSUri" />
  <s:url value='/js/lib/urijs/1.18.12/URI.min.js'               var="minURIJSUri" />
  <s:url value='/js/lib/urijs/1.18.12/jquery.URI.min.js'        var="minjQueryURIJSUri" />
  <s:url value='/js/lib/qwest/4.5.0/qwest.min.js'               var="miniQwestJSUri"></s:url>

  <s:url value='/js/app.js' var="appJSUri" />


  <script type="text/javascript" src='${minjQueryJSUri }'></script>
  <script type="text/javascript" src='${minPopperJSUri }'></script>
  <script type="text/javascript" src='${minTetherJSUri }'></script>
  <script type="text/javascript" src='${minBootstrapJSUri }'></script>
  <script type="text/javascript" src='${minURIJSUri }'></script>
  <script type="text/javascript" src='${minjQueryURIJSUri }'></script>
  <script type="text/javascript" src='${miniQwestJSUri }'></script>
  
  <script type="text/javascript" src='${appJSUri }'></script>
</body>
</html>