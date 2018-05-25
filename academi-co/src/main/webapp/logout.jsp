<%@ page session="true"%>

User '<%=request.getRemoteUser()%>' has been logged out.
<!-- End the session -->
<% session.invalidate(); %>

<!-- <br/><br/>
<a href="index.html">Click here to go to the home page</a> -->


<!-- Redirection to home page -->
<%
    String redirectURL = "home.html";
    response.sendRedirect(redirectURL);
%>



