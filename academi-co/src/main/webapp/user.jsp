<%String keyValue=(String)request.getRemoteUser();%>



<script src="js/modules/allModules.js"></script>
<script src="js/modules/getRESTCall.js"></script>

<script>
    // var jsVariable = "<%=keyValue%>";
    var jsVariable = "<%=(String)request.getRemoteUser()%>";
    // window.jsVariable = jsVariable;

    // document.getElementById("demoAppMyWelcome").innerHTML = jsVariable;


    setUser(jsVariable);

    // console.log('user.jsp called');

    // if(getCookie('username') == 'null'){
    //     console.log("1 croco");
    // } else {
    //     console.log("2 croco");
    // }
        // var jsVariable = "${keyValue}";
    // alert( jsVariable + " " ); //test the value
    window.location.replace("home.html");
</script>

<!-- Redirection to home page -->
