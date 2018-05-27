<script src="js/modules/allModules.js"></script>
<script src="js/modules/getRESTCall.js"></script>

<script>
   // getting the status of the connexion (username)
   var jsVariable = "<%=(String)request.getRemoteUser()%>";
   // give it to the cookie
   setUser(jsVariable);
   // redirection to the home page
   window.location.replace("home.html");
</script>
