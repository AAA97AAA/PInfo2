<%String keyValue=(String)request.getRemoteUser();%>

<script src="js/modules/allModules.js"></script>
<script src="js/modules/getRESTCall.js"></script>

<script>

    var jsVariable = "<%=(String)request.getRemoteUser()%>";

    setUser(jsVariable);
    // redirection to home.html
    window.location.replace("home.html");
</script>
