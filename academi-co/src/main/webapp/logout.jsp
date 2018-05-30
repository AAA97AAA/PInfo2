<%@ page session="true"%>
<!-- End the session -->
<% session.invalidate(); %>

<!-- <br/><br/>cookieController
<a href="index.html">Click here to go to the home page</a> -->

<!-- Include JS Scripts -->
<script src="/academi-co/js/controllers/domain.js"></script>
<script src="/academi-co/js/controllers/cookieController.js"></script>

<!-- Redirection to home page -->
<script>
    /* 
        Token deleted and redirect to home page
    */
    setCookie('tokenJWT', null);
    // alert("LOGOUT.JSP:" + getCookie('tokenJWT'));
    window.location.replace(getProtectedURL(""));

</script>



