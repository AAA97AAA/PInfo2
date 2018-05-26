<%@ page session="true"%>
<!-- End the session -->
<% session.invalidate(); %>

<!-- <br/><br/>
<a href="index.html">Click here to go to the home page</a> -->


<!-- Redirection to home page -->
<%
    String redirectURL = "user.jsp";
    response.sendRedirect(redirectURL);
%>



