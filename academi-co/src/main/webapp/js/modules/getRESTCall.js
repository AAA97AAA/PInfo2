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
