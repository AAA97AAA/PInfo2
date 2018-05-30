/* This function just return the domain with correct port */
function getDomain() {
      // first, we retrieve the domain //
  var oldurl = window.location.href;
  var arr = oldurl.split("/");
  var domain = arr[0] + "//" + arr[2];

    return domain;
}


/* This function uses the page needed to be loaded and the function will create the 
    the https domain
*/
function getProtectedURL(page) {
    myURL = "https://" + window.location.hostname + ":8443/academi-co/#!/" + page;
    return myURL;
}

/* This function return the path to access a resources giving him the argument */
function getProtectedResources(resources) {
    myURL = "https://" + window.location.hostname + ":8443/academi-co/resources/" + resources;
    return myURL;
}

