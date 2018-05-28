function getDomain() {
      // first, we retrieve the domain //
  var oldurl = window.location.href;
  var arr = oldurl.split("/");
  var domain = arr[0] + "//" + arr[2];

    return domain;
}