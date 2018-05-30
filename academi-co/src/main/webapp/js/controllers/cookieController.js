
/* COOKIE */

/* set a cookie with a name (creates it if not existing) */
function setCookie(name,value) {
    document.cookie = name + "=" + (value || "")  + "; path=/";
}

/* get the cookie by the name */
function getCookie(name) {
var nameEQ = name + "=";
var ca = document.cookie.split(';');
for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
}
return null;
}

/* erase the cookie */
function eraseCookie(name) {   
    document.cookie = name+'=; Max-Age=-99999999;';  
    // if( get_cookie( name ) ) {
    //     document.cookie = name + "=" +
    //     ((path) ? ";path="+path:"")+
    //     ((domain)?";domain="+domain:"") +
    //     ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
    // }
};
  
  
/* This function creates/sets the cookie username 
    with the corresponding one  username if logged in null if not */
function setUser(username) {
    setCookie('username', username);
};