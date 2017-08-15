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
	
	<!-- CSS Includes -->
	<link rel="stylesheet" 
	       href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" 
	       integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" 
	       crossorigin="anonymous">

	       
  <script src="https://use.fontawesome.com/facc121533.js"></script>
	       
	<link rel="stylesheet"
	       type="text/css"
	       href="<s:url value='/resources/css/app.css' />" >
	      
  <!-- Favicon Include -->
  <link rel="shortcut icon"
          href="<s:url value='/resources/images/medicallab-logo.png' />" >
  
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
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" 
          integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" 
          crossorigin="anonymous"></script>
          
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" 
          integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" 
          crossorigin="anonymous"></script>
  
  <script type="text/javascript"
          src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" 
          integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" 
          crossorigin="anonymous"></script>
          
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" 
          integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" 
          crossorigin="anonymous"></script>
          
  <script type="text/javascript"
          src='<s:url value="/resources/js/app.js"></s:url>'></script>
</body>
</html>