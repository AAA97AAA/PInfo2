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
  // TODO: send credidentials to back-end
  // if OK --> connected
  // if not --> loginError
});

/* Controller for loginError page */
app.controller('loginErrorController', function($scope, $http){


});

/* Controller for moderation page */
app.controller('moderationController', function($scope, $http){
  // TODO: no need to control the access because, web.xml does it well
  // if modo --> 
});

/* Controller for notFound page */
app.controller('notFoundController', function($scope, $http){


});

/* Controller for postThread page */
app.controller('postThreadController', function($scope, $http){
  // TODO: no need to control the access because, web.xml does it well
  // just POST thread
});

/* Controller for profile page */
app.controller('profileController', function($scope, $http){
  // TODO: 
  // conditional display: settings button displayed only for the corresponding user
  // display correct user


});

/* Controller for result page */
app.controller('resultController', function($scope, $http){
  // TODO: 
  // result of request
  // do the path param
 

});

/* Controller for settings and preferences page */
app.controller('settingsPreferencesController', function($scope, $http){
  // TODO:
  // PUT / (Use JWT to be sure of the user)
});

/* Controller for signUp page */
app.controller('signUpController', function($scope, $http) {
  $scope.user = {};
  
  $scope.onSubmit = function() {
  
  
    if(document.getElementById("password").value != document.getElementById("password2").value) {
      alert("Those passwords didn't match. Try again.");
    } else {
      $scope.user.type = 'REGISTERED';
    // alert(sha256_digest($scope.user.password));
      // password encryption
      $scope.user.password = sha256_digest(document.getElementById("password").value);
    
      var req = {
                  method: 'POST',
                  url: 'http://localhost:8080/academi-co/resources/users/',
                  headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                          },
                  data: $scope.user
                };
    
      $http(req).then(
        function(response){
          // console.log("account created perfectly");
                          },
        function(response){
          // console.log("ERROR : account cannot be created!");
          // console.log(response.data);
          // console.log("End error message");
          }
    
        )
    
    //       // window.location.href = "login.jsp";
    //       // alert("Welcome " + $scope.user.username + "! Please login.");
    }
  
  }
  // TODO: hash will change 
  //       redirect to correct page when error  
});

/* Controller for thread page */
app.controller('threadController', function($scope, $http, $routeParams){
  // get the ressource (the thread n° id)
  // first, we retrieve the domain //
  var oldurl = window.location.href;
  var arr = oldurl.split("/");
  var domain = arr[0] + "//" + arr[2];

  // we construct the url that we want to get
  var urlToGET = domain + '/academi-co/resources/posts/' + $routeParams.id;

  // console.log(urlToGET);
  $http({
    method: 'GET',
    url: urlToGET,
    headers: {
      'Accept': 'application/json',
      // 'Content-Type': 'application/json'
    },
  }).then(function(response) {
    // if we correctly have the result, we just take the data
     $scope.thread = response.data;
  }, function(response) {
    // if there is error
    if(response.status == 404){
      window.location.replace("/academi-co/#!/notFound");
    } else if(response.status == 403) {
      window.location.replace("/academi-co/#!/forbidden");
    } else if(response.status == 405) {
      window.location.replace("/academi-co/#!/internalServerError");
    }
  });

  // TODO: if user not connected: remove textArea 
  // document.getElementById("textAreaThread").style.display = "none";

  // TODO: function onSubmit (PUT)

});

/* Controller for home page */
app.controller('homeController', function($scope, $http){

  // $scope.message = "DUSK TILL DAWN";
  // console.log("baby I'm right here!");
});

/* Controller for preview page*/
app.controller('previewController', function($scope, $http){


});

/* Controller for conditional display (if logged in or logged out)  */
/* HEADER : controller for the header to check if the user is connected or not and display the correct one */

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
    //  console.log("message 3");
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

// /* controller that allow only connected user to reply a thread */
// app.controller('threadTextAreaConnected', function($scope, $http) {

//   if(getCookie('username') == 'null'){
//     document.getElementById("textAreaThread").style.display = 'none';
//   } else {
//     document.getElementById("textAreaThread").style.display = 'block';

//   }
  
// });


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





// // /* Controller for simple search */
// // app.controller('simplifiedSearchController', function($scope, $http){

// //   $scope.search = {};

// //   $scope.onSubmit = function() {
// //     alert($scope.search.title);

// //   }
// // });

