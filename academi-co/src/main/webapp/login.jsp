<!-- Our Scripts -->
<!-- Angular Academi-co module -->
<script src="/academi-co/js/modules/allModules.js"></script>
<!-- All controllers -->
<script src="/academi-co/js/controllers/allPageControllers.js"></script>
<!-- Routing Configuration -->
<script src="/academi-co/js/route/routeConfig.js"></script>
<!-- Cookie controller -->
<script src="/academi-co/js/controllers/cookieController.js"></script>

<script>
   // getting the status of the connexion (username)
   var jsVariable = "<%=(String)request.getRemoteUser()%>";
   // give it to the cookie
   setUser(jsVariable);
   // redirection to the home page
   window.location.replace("home.html");
</script>
