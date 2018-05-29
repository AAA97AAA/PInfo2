/*
 This JavaScript file contain angular module for academi-co.
    These will be replaced by only http get once it is ready in the back-end

*/

/* Controller for Administrator page */
app.controller('adminController', function($scope, $http){

	
	$scope.AddAdministrator = function() {
	
	$scope.admin.type = 'ADMINISTRATOR';
	
  // TODO: 
  // POST ADD ADMIN

    var urlToGET = getDomain() + '/academi-co/resources/users/administrator';
    var req = {
                method: 'POST',
                url: urlToGET,
                headers: {
                          'Content-Type': 'application/json',
                          'Accept': 'application/json'
                        },
                data: $scope.admin
              };
	
    
    $http(req).then(
            function(response){
              console.log("account created perfectly");
                             },
            function(response){
              console.log("ERROR : account cannot be created!");
              // console.log(response.statusText);
              // console.log(response.status);
              // console.log("End error message");
              alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
               switch(response.status){
                 case 400:
                   alert(response.data + " invalid!");
                 case 403:
                   window.location.replace("/academi-co/#!/forbidden");
                   break;
                 case 404:
                   window.location.replace("/academi-co/#!/notFound");
                   break;
                 case 405:
                   window.location.replace("/academi-co/#!/internalServerError");
                   break;
               }
              }
       
            )
	}
	
	
	//POST ADD TAG
	
	$scope.AddLangTag = function() {

	    $scope.name = { };

	    var urlToGET = getDomain() + '/academi-co/resources/tags/languages/';
	    var req = {
	      method: 'POST',
	      url: urlToGET,
	      headers: {
	          'Content-Type': 'application/json',
	          'Accept': 'application/json'
	        },
	      data: $scope.LangTag
	    }



	    $http(req).then(
	            function(response){
	              console.log("language tag created perfectly");
	                             },
	            function(response){
	              console.log("ERROR : language tag cannot be created!");
	              // console.log(response.statusText);
	              // console.log(response.status);
	              // console.log("End error message");
	              alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
	               switch(response.status){
	                 case 400:
	                   alert(response.data + " invalid!");
	                 case 403:
	                   window.location.replace("/academi-co/#!/forbidden");
	                   break;
	                 case 404:
	                   window.location.replace("/academi-co/#!/notFound");
	                   break;
	                 case 405:
	                   window.location.replace("/academi-co/#!/internalServerError");
	                   break;
	               }
	              }
	       
	            )};


	
	
  // POST ADD MAINTAG 
	$scope.AddMainTag = function() {
		
	$scope.name = { };


    var urlToGET = getDomain() + '/academi-co/resources/tags/';
    var req = {
                method: 'POST',
                url: urlToGET,
                headers: {
                          'Content-Type': 'application/json',
                          'Accept': 'application/json'
                        },
                data: $scope.MainTag
              };
	
    
    $http(req).then(
            function(response){
              console.log("maintag created perfectly");
                             },
            function(response){
              console.log("ERROR : maintag cannot be created!");
              // console.log(response.statusText);
              // console.log(response.status);
              // console.log("End error message");
              alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
               switch(response.status){
                 case 400:
                   alert(response.data + " invalid!");
                 case 403:
                   window.location.replace("/academi-co/#!/forbidden");
                   break;
                 case 404:
                   window.location.replace("/academi-co/#!/notFound");
                   break;
                 case 405:
                   window.location.replace("/academi-co/#!/internalServerError");
                   break;
               }
              }
       
            )
	}
	
	  // POST ADD SECONDTAG 
		$scope.AddSecondTag = function() {
			
		$scope.name = { };
		$scope.id = { };

	    var urlToGET = getDomain() + '/academi-co/resources/tags/' + "{" + id + "}";
	    var req = {
	                method: 'POST',
	                url: urlToGET,
	                headers: {
	                          'Content-Type': 'application/json',
	                          'Accept': 'application/json'
	                        },
	                data: $scope.SecondTag
	              };
		
	    
	    $http(req).then(
	            function(response){
	              console.log("secondtag created perfectly");
	                             },
	            function(response){
	              console.log("ERROR : secondtag cannot be created!");
	              // console.log(response.statusText);
	              // console.log(response.status);
	              // console.log("End error message");
	              alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
	               switch(response.status){
	                 case 400:
	                   alert(response.data + " invalid!");
	                 case 403:
	                   window.location.replace("/academi-co/#!/forbidden");
	                   break;
	                 case 404:
	                   window.location.replace("/academi-co/#!/notFound");
	                   break;
	                 case 405:
	                   window.location.replace("/academi-co/#!/internalServerError");
	                   break;
	               }
	              }
	       
	            )
		}
  // POST ADD BANNER
  // DISPLAY BANNERS, PUT/DELETE BANNER
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

/*Controller for loginSuccess page */
app.controller('loginSuccessController', function($scope, $http){

})


// /* Controller for moderation page */
// app.controller('moderationController', function($scope, $http){
//   // TODO: no need to control the access because, web.xml does it well
//   // if modo --> 
// });

/* Controller for notFound page */
app.controller('notFoundController', function($scope, $http){


});

/* Controller for postThread page */
app.controller('postThreadController', function($scope, $http){
  // TODO: no need to control the access because, web.xml does it well
  // just POST thread
});

/* Controller for profile page */
app.controller('profileController', function($scope, $http, $routeParams){
  // TODO: 
  // conditional display: settings button displayed only for the corresponding user
  
  // get the ressource (the user n° id)
  // we construct the url that we want to get
  var urlToGET = getDomain() + '/academi-co/resources/users/' + $routeParams.id;

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
     $scope.myProfileData = response.data;
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

});

/* Controller for result page */
app.controller('resultController', function($scope, $http, $routeParams){
  // TODO: 
  // result of request
  // do the path param 
  console.log("Bonjour " + $routeParams.searchParameters);
  $scope.message = $routeParams.searchParameters;
  
  
});

/* Controller for settings and preferences page */
app.controller('settingsPreferencesController', function($scope, $http){
  // TODO:
  // PUT / (Use JWT to be sure of the user)
  // here many put
});

/* Controller for signUp page */
app.controller('signUpController', function($scope, $http) {
  $scope.user = {};
  
  $scope.onSubmit = function() {
  
  
    if(document.getElementById("password").value != document.getElementById("password2").value) {
      $.growl.error({ message: "Those passwords didn't match. Try again." });
    } else {
      // This is useless (back-end don't really care about that)
      // but in term of lisibility, this defines the new user as REGISTERED
      $scope.user.type = 'REGISTERED';
      
      $scope.user.password = document.getElementById("password").value;
    
      var urlToGET = getDomain() + '/academi-co/resources/users/';
      var req = {
                  method: 'POST',
                  url: urlToGET,
                  headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                          },
                  data: $scope.user
                };
                
       $http(req).then(
         function(response){
           // account has been created correctly, we redirect the user to the login page
           $.growl.notice({ message: "Account perfectly created! Please login." });
           // here we're sending him to the protected ressource loginSuccess that will load him login page
           window.location.replace("/academi-co/#!/loginSuccess");
           
                          },
         function(response){

            // console.log("ERROR : account cannot be created! " + response);
            switch(response.status){
              case 400:
              // message in case of EMAIL or USERNAME already used
               var errorMessage =  response.data.message + " already used!";
               $.growl.error({ message: errorMessage });             
               break;
              case 403:
                 window.location.replace("/academi-co/#!/forbidden");
                 break;
              case 404:
                window.location.replace("/academi-co/#!/notFound");
                break;
              case 405:
                window.location.replace("/academi-co/#!/internalServerError");
                break;
             }
           }
    
         )
    
    }
  
  }

});

/* Controller for thread page */
app.controller('threadController', function($scope, $http, $routeParams){
  // get the ressource (the thread n° id)

  // we construct the url that we want to get
  var urlToGET = getDomain() + '/academi-co/resources/posts/' + $routeParams.id;

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
      switch(response.status){
        case 403:
          window.location.replace("/academi-co/#!/forbidden");
          break;
        case 404:
          window.location.replace("/academi-co/#!/notFound");
          break;
        case 405:
          window.location.replace("/academi-co/#!/internalServerError");
          break;
      }
  });

  // TODO: if user not connected: remove textArea 
  // document.getElementById("textAreaThread").style.display = "none";

  // TODO: function onSubmit (PUT)

});

/* Controller for home page */
app.controller('homeController', function($scope, $http){
  // DISPLAY THE TAGS + if click on TAG, search result for tag

  // $scope.message = "DUSK TILL DAWN";
  // console.log("baby I'm right here!");
});

/* Controller for preview page*/
app.controller('previewController', function($scope, $http){


});

/* Controller for conditional display (if logged in or logged out)  */
/* HEADER : controller for the header to check if the user is connected or not and display the correct one */

app.controller('isConnectHeader', function($scope, $http){

  // TODO: if connected, need to displ
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
  // login function 
  $scope.login = function() {

    // // we construct the url that we want to get
    // // var urlToGET = getDomain() + '/academi-co/resources/auth';
    // var urlToGET = "https://localhost:8443/academi-co/resources/auth";

    // // console.log(urlToGET);
    // $http({
    //   method: 'GET',
    //   url: urlToGET,
    //   headers: {
    //     'Accept': 'application/json',
    //     'Content-Type': 'application/json'
    //   },
    // }).then(function(response) {
    //   console.log("logged");
    //   window.location.replace("/academi-co/#!/home");
    // }, function(response) {
    //   console.log("problem login " + response.status + " " + response.statusText);
    //   switch(response.status){
    //     case 403:
    //       window.location.replace("/academi-co/#!/forbidden");
    //       break;
    //     case 404:
    //       window.location.replace("/academi-co/#!/notFound");
    //       break;
    //     case 405:
    //       window.location.replace("/academi-co/#!/internalServerError");
    //       break;
    //   }
    // });
          // alert("error " + response.status);
      // // if there is error
      // if(response.status == 404){
      //   window.location.replace("/academi-co/#!/notFound");
      // } else if(response.status == 403) {
      //   window.location.replace("/academi-co/#!/forbidden");
      // } else if(response.status == 405) {
      //   window.location.replace("/academi-co/#!/internalServerError");
      // } else {
      //   // alert("error " + response.status);
      // }

  };

  $scope.signUp = function() {
    window.location.replace("/academi-co/#!/signUp");
  }

});

// TODO: one or two controller for search
//      if the user has opened advanced search, will he be able to write something in the normal search bar

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





 /* Controller for simple search */
 app.controller('simplifiedSearchController', function($scope, $http){

  $scope.search = {};
  $scope.simplifiedSearch = function() {
    if($scope.search.title == null){
      // when the user has entered nothing, we don't search it
      // and ask him to fill out the field
    } else {
      searchUrl = getDomain() + '/academi-co/#!/result/title=' + $scope.search.title;
      // console.log(searchUrl);
      window.location.replace(searchUrl); 
    }
  }
});

/* Controller for advanced search */
app.controller('advancedSearchController', function($scope, $http){
  
  $scope.search = {};

  document.getElementById("myUL1");
  document.getElementById("myUL2");

});