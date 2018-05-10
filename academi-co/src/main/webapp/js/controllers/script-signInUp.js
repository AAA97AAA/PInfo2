// Function to remove Sign Up page when we click outside of it



// Function to see the password

function showLoginPassword(obj) {
  var obj = document.getElementById('LoginPassword');
  obj.type = "text";
}
function hideLoginPassword(obj) {
  var obj = document.getElementById('LoginPassword');
  obj.type = "password";
}

function showSignUpPassword(obj) {
  var obj = document.getElementById('SignUpPassword');
  obj.type = "text";
}
function hideSignUpPassword(obj) {
  var obj = document.getElementById('SignUpPassword');
  obj.type = "password";
}
