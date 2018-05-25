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
.controller('UserInbox', function($scope, $http) {
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
});


app
.controller('threadLoad', function($scope, $http) {
	$http.get("http://localhost:8080/academi-co/resources/posts/2")
	.then(function(response) {
		$scope.thread = response.data;
	})
});


/* POST User */

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
        function(){
          console.log("Aslam c'est le meilleur");
        },
        function(){
            console.log("ERROR POST mais Aslam reste le meilleur tout de même");
        }

      )

      // window.location.href = "login.jsp"
      // alert("Welcome " + $scope.user.username + "! Please login.");
    }
    
  }

})


