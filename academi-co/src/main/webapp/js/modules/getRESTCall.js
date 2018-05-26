/*
 This JavaScript file contain angular module for academi-co.
    These will be replaced by only http get once it is ready in the back-end
    Until that, we mock the JSON content
    TODO: redefine the controller name with key REST Words
*/

/* how to GET element */

// moduleAngular
app
.controller('profilePageLoad', function($scope, $http) {
	$http.get("http://localhost:8080/academi-co/resources/users/2")
	.then(function(response) {
		$scope.myProfileData = response.data;
	})
});


app
.controller('threadLoad', function($scope, $http) {
	$http.get("http://localhost:8080/academi-co/resources/posts/2")
	.then(function(response) {
		$scope.thread = response.data;
	})
});



/* POST User (create an account) */

app.controller('signUpController', function($scope, $http) {
  $scope.user = {};

  $scope.onSubmit = function() {


    if(document.getElementById("password").value != document.getElementById("password2").value) {
      alert("Those passwords didn't match. Try again.");
    } else {
      $scope.user.type = 'REGISTERED';
      // alert(sha256_digest($scope.user.password));
      $scope.user.password = sha256_digest(document.getElementById("password").value);


      var req = {
        method: 'POST',
        url: 'http://localhost:8080/academi-co/resources/users/',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
        data: $scope.user
      }

      $http(req).then(
        function(response){
          console.log("account created perfectly");
        },
        function(response){
            console.log("ERROR : account cannot be created!");
            console.log(response.data);
            console.log("End error message");
        }

      )

      // window.location.href = "login.jsp";
      // alert("Welcome " + $scope.user.username + "! Please login.");
    }

  }

});

/* controller for the header to check if the user is connected or not and display the correct one */

app.controller('isConnectHeader', function($scope, $http){
  var notConnected = getCookie('username') == 'null';
  var notDefined = getCookie('username') == null;

  if(notConnected){
    document.getElementById("rightConnectedComponent").style.display = 'none';
    document.getElementById("rightNonConnectedComponent").style.display = 'block';
    // alert("not Connected Angular");
  } else if(notDefined) {
    document.getElementById("rightConnectedComponent").style.display = 'none';
    document.getElementById("rightNonConnectedComponent").style.display = 'block';
  } else { // connected
    document.getElementById("rightNonConnectedComponent").style.display = 'none';
    document.getElementById("rightConnectedComponent").style.display = 'block';
    console.log("message 3");
    $scope.userInbox = {
      "messages": [
        {
          "id": "1",
          "content": "Someone commented your thread: What is the..."
        },
        {
          "id": "2",
          "content": "Someone upvoted your thread: How to make flat bread"
        },
        {
          "id": "3",
          "content": "Someone asked a question with the tag Maths"
        }
      ]
    };
    // alert("Connected Angular");
  }
});

/* controller that allow only connected user to reply a thread */
app.controller('threadTextAreaConnected', function($scope, $http) {

  if(getCookie('username') == 'null'){
    document.getElementById("textAreaThread").style.display = 'none';
  } else {
    document.getElementById("textAreaThread").style.display = 'block';

  }
  

});


/* COOKIE */

function setCookie(name,value) {
  document.cookie = name + "=" + (value || "")  + "; path=/";
}
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
function eraseCookie(name) {   
  document.cookie = name+'=; Max-Age=-99999999;';  
};


// This function creates/sets the cookie username with the corresponding one
// username if logged in 
// null if not
function setUser(username) {
  // window.userContenu = username;
  setCookie('username', username);
  // alert(username);
};

