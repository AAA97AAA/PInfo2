/*
 This JavaScript file contain angular module for academi-co.
    These will be replaced by only http get once it is ready in the back-end

*/

/* Controller for Administrator page */
app.controller('adminController', function($scope, $http){
  //   var url = window.location.href;
  //  var arr = url.split("/");
  //   var result = arr[0] + "//" + arr[2];
  //  console.log(result); 
});

/* Controller for forbidden page */
app.controller('forbiddenController', function($scope, $http){
 

});

/* Controller for help page */
app.controller('helpController', function($scope, $http){


});

/* Controller for internal Server Error page */
app.controller('internalServerErrorController', function($scope, $http){


});

/* Controller for login page */
app.controller('loginController', function($scope, $http){


});

/* Controller for loginError page */
app.controller('loginErrorController', function($scope, $http){


});

/* Controller for moderation page */
app.controller('moderationController', function($scope, $http){


});

/* Controller for notFound page */
app.controller('notFoundController', function($scope, $http){


});

/* Controller for postThread page */
app.controller('postThreadController', function($scope, $http){


});

/* Controller for profile page */
app.controller('profileController', function($scope, $http){


});

/* Controller for result page */
app.controller('resultController', function($scope, $http){
 

});

/* Controller for settings and preferences page */
app.controller('settingsPreferencesController', function($scope, $http){


});

/* Controller for signUp page */
app.controller('signUpController', function($scope, $http){

});

/* Controller for thread page */
app.controller('threadController', function($scope, $http){


});

/* Controller for home page */
app.controller('homeController', function($scope, $http){

  // $scope.message = "DUSK TILL DAWN";
  // console.log("baby I'm right here!");
});

/* Controller for preview page*/
app.controller('previewController', function($scope, $http){


});




// /* how to GET element */

// // moduleAngular
// route.controller('profilePageLoad', function($scope, $http) {

//   $http({
//   method: 'GET',
//   url: 'http://localhost:8080/academi-co/resources/users/2',
//   headers: {
//       'Accept': 'application/json',
//       // 'Content-Type': 'application/json'
//     },
// }).then(function(response) {
//     console.log("Aslam c'est le meilleur");
//     $scope.myProfileData = response.data;
//   }, function(response) {
//     console.log("ERROR GET mais Aslam reste le meilleur tout de même");
//     console.log(response.status);
//   });

// });


// app
// .controller('profilePageLoad', function($scope, $http) {
// 	$http.get("http://localhost:8080/academi-co/resources/users/2")
// 	.then(function(response) {
// 		$scope.myProfileData = response.data;
// 	})
// });


// app
// .controller('threadLoad', function($scope, $http) {
// 	$http.get("http://localhost:8080/academi-co/resources/posts/2")
// 	.then(function(response) {
// 		$scope.thread = response.data;
// 	})
// });



// /* POST User (create an account) */

// app.controller('signUpController', function($scope, $http) {
//   $scope.user = {};

//   $scope.onSubmit = function() {


//     if(document.getElementById("password").value != document.getElementById("password2").value) {
//       alert("Those passwords didn't match. Try again.");
//     } else {
//       $scope.user.type = 'REGISTERED';
//       // alert(sha256_digest($scope.user.password));
//       $scope.user.password = sha256_digest(document.getElementById("password").value);


//       var req = {
//         method: 'POST',
//         url: 'http://localhost:8080/academi-co/resources/users/',
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json'
//           },
//         data: $scope.user
//       }

//       $http(req).then(
//         function(response){
//           console.log("account created perfectly");
//         },
//         function(response){
//             console.log("ERROR : account cannot be created!");
//             console.log(response.data);
//             console.log("End error message");
//         }

//       )

//       // window.location.href = "login.jsp";
//       // alert("Welcome " + $scope.user.username + "! Please login.");
//     }

//   }

// });

// /* controller for the header to check if the user is connected or not and display the correct one */

// app.controller('isConnectHeader', function($scope, $http){
//   var notConnected = getCookie('username') == 'null';
//   var notDefined = getCookie('username') == null;

//   if(notConnected){
//     document.getElementById("rightConnectedComponent").style.display = 'none';
//     document.getElementById("rightNonConnectedComponent").style.display = 'block';
//     // alert("not Connected Angular");
//   } else if(notDefined) {
//     document.getElementById("rightConnectedComponent").style.display = 'none';
//     document.getElementById("rightNonConnectedComponent").style.display = 'block';
//   } else { // connected
//     document.getElementById("rightNonConnectedComponent").style.display = 'none';
//     document.getElementById("rightConnectedComponent").style.display = 'block';
//     console.log("message 3");
//     $scope.userInbox = {
//       "messages": [
//         {
//           "id": "1",
//           "content": "Someone commented your thread: What is the..."
//         },
//         {
//           "id": "2",
//           "content": "Someone upvoted your thread: How to make flat bread"
//         },
//         {
//           "id": "3",
//           "content": "Someone asked a question with the tag Maths"
//         }
//       ]
//     };
//     // alert("Connected Angular");
//   }
// });

// /* controller that allow only connected user to reply a thread */
// app.controller('threadTextAreaConnected', function($scope, $http) {

//   if(getCookie('username') == 'null'){
//     document.getElementById("textAreaThread").style.display = 'none';
//   } else {
//     document.getElementById("textAreaThread").style.display = 'block';

//   }
  
// });



// // /* Controller for simple search */
// // app.controller('simplifiedSearchController', function($scope, $http){

// //   $scope.search = {};

// //   $scope.onSubmit = function() {
// //     alert($scope.search.title);

// //   }
// // });

