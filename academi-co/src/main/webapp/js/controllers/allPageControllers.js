/*
 This JavaScript file contain angular module for academi-co.
    These will be replaced by only http get once it is ready in the back-end

*/

/* Controller for Administrator page */
app.controller('adminController', function($scope, $rootScope, $http){

  // Add an administrator
    $scope.AddAdministrator = function() {
    
    // set the new account to administrator type
      $scope.admin.type = 'ADMINISTRATOR';

    // POST ADD ADMIN
    var urlToGET = getProtectedResources('users/administrator');
    // our request
    var req = {
                method: 'POST',
                url: urlToGET,
                headers: {
                          'Content-Type': 'application/json',
                          'Accept': 'application/json'
                        },
                data: $scope.admin
              };  

    // execution of the request
    $http(req).then(
            // success function
            function(response){
              
            $.growl.notice({ message: "Administrator created successfully!" });
                             },
            // failed function
            function(response){
              $.growl.error({ message: "Administrator cannot be created." });

               switch(response.status){
                 case 400:
                  //  alert(response.data + " invalid!");
                  $.growl.error({ message: "Administrator cannot be created." });
                   break;
                 case 403:
                   window.location.replace("/academi-co/#!/forbidden");
                   break;
                 case 404:
                   window.location.replace("/academi-co/#!/notFound");
                   break;
                 case 500:
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
                  $.growl.notice({ message: "Language tag perfectly created." });
                                 },
                function(response){
                $.growl.error({ message: "ERROR : language tag cannot be created!"});

                  //  switch(response.status){
                  //  case 400:
                  //  $.growl.error({ message: "ERROR : language tag cannot be created! Already existing."});
                  //   //  alert(response.data + " invalid!");
                  //   break;
                  //    case 403:
                  //      window.location.replace("/academi-co/#!/forbidden");
                  //      break;
                  //    case 404:
                  //      window.location.replace("/academi-co/#!/notFound");
                  //      break;
                  //    case 500:
                  //      window.location.replace("/academi-co/#!/internalServerError");
                  //      break;
                  //  }
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
              $.growl.notice({ message: "Maintag perfectly created." });
                             },
            function(response){
              $.growl.error({ message: "Maintag cannot be created." });
              // console.log(response.statusText);
              // console.log(response.status);
              // console.log("End error message");
              // alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
              //  switch(response.status){
              //    case 400:
              //     //  alert(response.data + " invalid!");
              //     $.growl.error({ message: "Maintag cannot be created. Already existing" });
              //      break;
              //    case 403:
              //      window.location.replace("/academi-co/#!/forbidden");
              //      break;
              //    case 404:
              //      window.location.replace("/academi-co/#!/notFound");
              //      break;
              //    case 500:
              //      window.location.replace("/academi-co/#!/internalServerError");
              //      break;
              //  }
              }

            )
    }

      // POST ADD SECONDTAG
        $scope.AddSecondTag = function() {

        var urlToGET = getDomain() + '/academi-co/resources/tags/' + $scope.SecondTag.id;
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
                  $.growl.notice({ message: "Secondary tag perfectly created." });
                                 },
                function(response){
                  $.growl.error({ message: "Secondary tag cannot be created." });
                  // alert("url " + urlToGET + " et " + $scope.SecondTag.name);
                  // console.log(response.statusText);
                  // console.log(response.status);
                  // console.log("End error message");
                  // alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
                  //  switch(response.status){
                  //    case 400:
                  //      alert(response.data + " invalid!");
                  //    case 403:
                  //      window.location.replace("/academi-co/#!/forbidden");
                  //      break;
                  //    case 404:
                  //      window.location.replace("/academi-co/#!/notFound");
                  //      break;
                  //    case 500:
                  //      window.location.replace("/academi-co/#!/internalServerError");
                  //      break;
                  //  }
                  }

                )
        }
  // POST ADD BANNER
  $scope.AddBanner = function() {
                
    $scope.data = { };
    
      var urlToGET = getDomain() + '/academi-co/resources/advertisements/';
      var req = {
                  method: 'POST',
                  url: urlToGET,
                  headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                          },
                  data: $scope.NewAd
                };
    
      
      $http(req).then(
              function(response){
                $.growl.notice({ message: "Banner perfectly created." });
                               },
              function(response){
                $.growl.error({ message: "Banner tag cannot be created." });
                // console.log(response.statusText);
                // console.log(response.status);
                // console.log("End error message");
                // alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
                //  switch(response.status){
                //    case 400:
                //      alert(response.data + " invalid!");
                //    case 403:
                //      window.location.replace("/academi-co/#!/forbidden");
                //      break;
                //    case 404:
                //      window.location.replace("/academi-co/#!/notFound");
                //      break;
                //    case 500:
                //      window.location.replace("/academi-co/#!/internalServerError");
                //      break;
                //  }
                }
         
              )
    }

  // DISPLAY BANNERS, PUT/DELETE BANNER
});

/* Controller for advanced search */
app.controller('advancedSearchController', function($scope, $rootScope, $http){
	
	$scope.getChildren = function(){
		
		newElement1();
		
		
		  var elements = document.getElementById("myUL1").getElementsByTagName("li");
		    var elem = elements[0];
		    var id1 = elem.innerText;
		    id1 = id1.split(" ");
		    var idParent = id1[0];
		    
			
		    var primaryTagURL = getDomain() + '/academi-co/resources/tags/' + idParent;
		    // request
		    

		    
		    var req = {
		                  method: 'GET',
		                  url   : primaryTagURL,
		                  headers: {
		                    'Content-Type': 'application/json',
		                    'Accept': 'application/json'
		                  },
		            };
		    
			
		    $http(req).then(
		            function(response){
		              $scope.secondaryTagDisplay = response.data;
		              // alert("ok" + $scope.primaryTag[0].name);
		                              },
		            function(response){
		              $.growl.error({ message: "Error while loading secondary tags"});
		                // switch(response.status){
		                //   case 403:
		                //     window.location.replace("/academi-co/#!/forbidden");
		                //     break;
		                //   case 404:
		                //     window.location.replace("/academi-co/#!/notFound");
		                //     break;
		                //   case 500:
		                //     window.location.replace("/academi-co/#!/internalServerError");
		                //     break;
		                // }
		              }
		    )
	}
	

    // First we need to retrieve the primary tags to show
    var primaryTagURL = getProtectedResources("tags");
  
    // request
    var req = {
                  method: 'GET',
                  url   : primaryTagURL,
                  headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                  },
            };
    
      $http(req).then(
        function(response){
          $rootScope.primaryTag = response.data;
          // alert("ok" + $scope.primaryTag[0].name);
                          },
        function(response){
          $.growl.error({ message: "Error while loading tags"});
            // switch(response.status){
            //   case 403:
            //     window.location.replace("/academi-co/#!/forbidden");
            //     break;
            //   case 404:
            //     window.location.replace("/academi-co/#!/notFound");
            //     break;
            //   case 500:
            //     window.location.replace("/academi-co/#!/internalServerError");
            //     break;
            // }
          }
    
        )

  $scope.search = {};

  document.getElementById("myUL1");
  document.getElementById("myUL2");


  $scope.submitAdvancedSearch = function() {
    // alert("oklm ta fait une recherche avec " + document.getElementsByTagName("li").value + " et " + document.getElementById("myUL2").value);
    for (i = 0; i < document.getElementsByTagName("li").length; i++){
      console.log( document.getElementsById("myUL1").options[i]);
    }
  }

});


/* Controller for displaying the secondary tags related as children to maintags*/

/* Controller for home page */
app.controller('homeController', function($scope, $rootScope, $http){
  // DISPLAY THE TAGS + if click on TAG, search result for tag


});

/* Controller for conditional display (if logged in or logged out)  */
/* HEADER : controller for the header to check if the user is connected or not and display the correct one */

app.controller('isConnectHeader', function($scope, $rootScope, $http){

  // alert("header charged");

  // $scope.show_me = false;

  /*
   if there is no token, the user is not connected: not connected display
   if there is a token, the user is connected and we display his profile
   */
  if((getCookie('tokenJWT') == null) || (getCookie('tokenJWT') == '')){
    // not connected
    // $scope.show_me = false;

    // alert("not connected");
    document.getElementById("rightConnectedComponent").style.display = 'none';
    document.getElementById("rightNonConnectedComponent").style.display = 'block';
    
    } else {
    // alert("connected");
    // $scope.show_me = true;

        // user data to obtain profile pic and link
          var nom = "users/" + jwt_decode(getCookie('tokenJWT')).sub;
          // alert(nom);
          var userURL = getProtectedResources(nom);
          
          // request
          var req = {
                        method: 'GET',
                        url   : userURL,
                        headers: {
                          'Content-Type': 'application/json',
                          'Accept': 'application/json'
                        },
                  };
          
            $http(req).then(
              function(response){
                $rootScope.user = response.data;
                                },
              function(response){
                $.growl.error({ message: "Error while loading user details"});
                  switch(response.status){
                    case 403:
                      window.location.replace("/academi-co/#!/forbidden");
                      break;
                    case 404:
                      window.location.replace("/academi-co/#!/notFound");
                      break;
                    case 500:
                      window.location.replace("/academi-co/#!/internalServerError");
                      break;
                  }
                }
          
              )

      // connected
       document.getElementById("rightNonConnectedComponent").style.display = 'none';
       document.getElementById("rightConnectedComponent").style.display = 'block';
      //  alert("done");
      
  }


  // login function (when login button is pressed)
  $scope.login = function() {
    // we redirect to login page 
    window.location.replace(getProtectedURL("login"));
  };

  // when signUp function is pressed
  $scope.signUp = function() {
    // redirection to the correct page
    window.location.replace(getProtectedURL("signUp"));
  }


  
});



/* Controller for loginSuccess page */
/*
    This controller is doing the GET for JWT Token once the user gets connected
*/
app.controller('loginSuccessController', function($scope, $rootScope, $http){

  // link to GET the token
  var urlToGET = getProtectedResources("auth");

  // get the token
     $http({
       method: 'GET',
       url: urlToGET,
         headers: {
          //  'Accept': 'application/json',
          //  'Content-Type': 'application/json'
           'Accept': 'text/plain',
           'Content-Type': 'text/plain'
         },
       }).then(function(response) {
        // once we got the token, we store it in a cookie
        setCookie('tokenJWT', response.data);
        // we put it in the header of all transactions
        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data;
        // we have a global variable with the id of the user
        //  $rootScope.idUserConnecte =  jwt_decode(getCookie('tokenJWT')).sub;
        // we need to take the data
        var nom = "users/" + jwt_decode(getCookie('tokenJWT')).sub;
        // alert(nom);
        var userURL = getProtectedResources(nom);
        
        // request
        var req = {
                      method: 'GET',
                      url   : userURL,
                      headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'application/json'
                      },
                };
        
          $http(req).then(
            function(response){
              $rootScope.user = response.data;
                              },
            function(response){
              $.growl.error({ message: "Error while loading user details"});
                switch(response.status){
                  case 403:
                    window.location.replace("/academi-co/#!/forbidden");
                    break;
                  case 404:
                    window.location.replace("/academi-co/#!/notFound");
                    break;
                  case 500:
                    window.location.replace("/academi-co/#!/internalServerError");
                    break;
                }
              }
        
            )

        // we are connected
        document.getElementById("rightNonConnectedComponent").style.display = 'none';
        document.getElementById("rightConnectedComponent").style.display = 'block';
        // we redirect to home page
        window.location.replace(getProtectedURL(""));

       }, function(response) {
        // if there is problem, we handle it
         switch(response.status){
           case 403:
             window.location.replace("/academi-co/#!/forbidden");
             break;
           case 404:
             window.location.replace("/academi-co/#!/notFound");
             break;
           case 500:
             window.location.replace("/academi-co/#!/internalServerError");
             break;
         }
       });

});


/* Controller for postThread page */
app.controller('postThreadController', function($scope, $rootScope, $http){
  // TODO: no need to control the access because, web.xml does it well
  // just POST thread
	
	var listOfFils=[];
	var increment = -1;
	$scope.getChildrenIDs = function(){
		newElementPTP2();
		
		  var elements = document.getElementById("myULPTP2").getElementsByTagName("li");
		    increment += 1;
		    var elem = elements[increment];
		    var id1 = elem.innerText;
		    id1 = id1.split(" ");
		    var idFils= id1[0];
		    listOfFils.push(idFils);
		    //$.growl.error({ message: idFils});
		    //$.growl.error({ message: listOfFils});
		    
	}

     $scope.getChildrenPTP1 = function(){
		
		newElementPTP1();
		
		
		  var elements = document.getElementById("myULPTP1").getElementsByTagName("li");
		    var elem = elements[0];
		    var id1 = elem.innerText;
		    id1 = id1.split(" ");
		    var idParent = id1[0];
		    
		   
			
		    var secondaryTagURL = getDomain() + '/academi-co/resources/tags/' + idParent;
		    // request
		    
		    
		    
		    var req = {
		                  method: 'GET',
		                  url   : secondaryTagURL,
		                  headers: {
		                    'Content-Type': 'application/json',
		                    'Accept': 'application/json'
		                  },
		            };
		    
			
		    $http(req).then(
		            function(response){
		              $scope.secondaryTagDisplay2 = response.data;
		              //$.growl.error({ message: $scope.secondaryTagDisplay2.children[0]});
		              // alert("ok" + $scope.primaryTag[0].name);
		                              },
		            function(response){
		              $.growl.error({ message: "Error while loading secondary tags"});
		                // switch(response.status){
		                //   case 403:
		                //     window.location.replace("/academi-co/#!/forbidden");
		                //     break;
		                //   case 404:
		                //     window.location.replace("/academi-co/#!/notFound");
		                //     break;
		                //   case 500:
		                //     window.location.replace("/academi-co/#!/internalServerError");
		                //     break;
		                // }
		              }
		    )
	}	
	
  // First we need to retrieve the primary tags to show
  var primaryTagURL = getProtectedResources("tags");
  
  // request of primary tag with their children
  var req = {
                method: 'GET',
                url   : primaryTagURL,
                headers: {
                  'Content-Type': 'application/json',
                  'Accept': 'application/json'
                },
          };
  
    $http(req).then(
      function(response){
        $rootScope.primaryTag = response.data;
        // alert("ok" + $scope.primaryTag[0].name);
                        },
      function(response){
        $.growl.error({ message: "Error while loading primary tags"});
          switch(response.status){
            case 403:
              window.location.replace("/academi-co/#!/forbidden");
              break;
            case 404:
              window.location.replace("/academi-co/#!/notFound");
              break;
            case 500:
              window.location.replace("/academi-co/#!/internalServerError");
              break;
          }
        }
  
      )



  $scope.CreatePost = function(){
    	
//        $.growl.error({ message: author});
//        $.growl.error({ message: title});
//        $scope.post.title = document.getElementById("userInput1").value;
//    	$scope.post.title = document.getElementById("userInput1").value;
//    	var rip = $scope.post.title 
        $.growl.error({ message: ""});
//        $scope.content = document.getElementById("userInput2").value;
//        $.growl.error({ message: content.value})
//        $scope.creationDate = "";
//        $scope.subject = {"id":idParent};
//        $scope.topics = { };
//        $scope.language = { };
        
//        $.growl.error({ message: author});
//        $.growl.error({ message: title});
//        $.growl.error({ message: content});
//        $.growl.error({ message: creationDate});
//        $.growl.error({ message: subject});
//        $.growl.error({ message: topics});
//        $.growl.error({ message: language});
       
//     var urlToPOST = getDomain() + '/academi-co/resources/posts/';
//        var req = {
//                method: 'POST',
//                url: urlToPOST,
//                headers: {
//                          'Content-Type': 'application/json',
//                          'Accept': 'application/json'
//                        },
//                //data: {$scope.author,'title':$scope.title,'content':$scope.content,'creationDate':$scope.creationDate,'subject':$scope.subject,'topics':$scope.topics,'language':$scope.language}
//              };
//    
//    
//     $http(req).then(
//                function(response){
//                  $.growl.notice({ message: "Post perfectly created." });
//                                 },
//                function(response){
//                  $.growl.error({ message: "Post cannot be created." });
//                  // console.log(response.statusText);
//                  // console.log(response.status);
//                  // console.log("End error message");
//                  // alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
//                   switch(response.status){
//                    //  case 400:
//                    //    alert(response.data + " invalid!");
//                     case 403:
//                       window.location.replace("/academi-co/#!/forbidden");
//                       break;
//                     case 404:
//                       window.location.replace("/academi-co/#!/notFound");
//                       break;
//                     case 500:
//                       window.location.replace("/academi-co/#!/internalServerError");
//                       break;
//                   }
//                  }
//           
//                )
        }

        $scope.Comment = function(){
            
            $scope.id = { };
            $scope.author = { };
            $scope.content = { }; 
            $scope.creationDate = { };
            
         var urlToGET = getDomain() + '/academi-co/resources/posts/'+ "{"+ $scope.id +"}";
            var req = {
                    method: 'POST',
                    url: urlToGET,
                    headers: {
                              'Content-Type': 'application/json',
                              'Accept': 'application/json'
                            },
                    data: {'id':$scope.id,'author':$scope.author,'content':$scope.content,'creationDate':$scope.creationDate}
                  };
        
        
         $http(req).then(
                    function(response){
                      $.growl.notice({ message: "Post perfectly created." });
                                     },
                    function(response){
                      $.growl.error({ message: "Post cannot be created." });
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
                         case 500:
                           window.location.replace("/academi-co/#!/internalServerError");
                           break;
                       }
                      }
               
                )
      }

});

/* Controller for profile page */
app.controller('profileController', function($scope, $rootScope, $http, $routeParams){
  // TODO:

  // conditional display: settings button displayed only for the corresponding user
  $scope.goSettings = function(){
    window.location.replace(getProtectedURL("settings"));
  }

  // get the ressource (the user n° id)
  // we construct the url that we want to get
  var urlToGET = getDomain() + '/academi-co/resources/users/' + $routeParams.id;

  // if it is the current user, we display the settings button, otherwise not
  if($routeParams.id == jwt_decode(getCookie('tokenJWT')).sub) {
    document.getElementById("settingsButton").style.display = "block";
  } else {
    document.getElementById("settingsButton").style.display = "none";
  }


  var urlToGET = getDomain() + '/academi-co/resources/users/' + $routeParams.id;

  // console.log(urlToGET);
  $http({
    method: 'GET',
    url: urlToGET,
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
  }).then(function(response) {
    // if we correctly have the result, we just take the data
     $scope.myProfileData = response.data;
     alert(myProfileData.bio);
  }, function(response) {
    switch(response.status){
      case 400:
        alert(response.data + " invalid!");
      case 403:
        window.location.replace("/academi-co/#!/forbidden");
        break;
      case 404:
        window.location.replace("/academi-co/#!/notFound");
        break;
      case 500:
        window.location.replace("/academi-co/#!/internalServerError");
        break;
    }
  });

});

/* Controller for result page */
app.controller('resultController', function($scope, $rootScope, $http, $routeParams){
  // TODO:
  // result of request
  // do the path param
  console.log("Bonjour " + $routeParams.searchParameters);
  $scope.message = $routeParams.searchParameters;


});


/* Controller for settings and preferences page */
app.controller('settingsPreferencesController', function($scope, $rootScope, $http){
  // TODO:
  // PUT / (Use JWT to be sure of the user)
   // PUT / (Use JWT to be sure of the user)

   $scope.settingsUpdate = function() {

    if($scope.change.password != document.getElementById("repeatPasswordSettings").value) {
      // password did not match
      $.growl.error({ message: "Those passwords didn't match. Try again." });

    } else {
      // TODO: check if there is update in bio/pp/password
      // if there is, change
      $scope.change = {}; // BIO PAssword OK
      $scope.change.username = $rootScope.user.username;
      // TODO need to change this to have correct thing
      $scope.change.profilePicture = $rootScope.user.profilePicture;
      $scope.change.type = "ADMINISTRATOR";
      $scope.change.canBeModerator = 1;



      // we need to PUT the change
     var urlToGET = getDomain() + '/academi-co/resources/users/'+ $rootScope.user.id;
       var req = {
                 method: 'PUT',
                 url: urlToGET,
                 headers: {
                           'Content-Type': 'application/json',
                           'Accept': 'application/json'
                         },
                 data: $scope.change
               };
   
   
      $http(req).then(
                function(response){
                  $.growl.notice({ message: "Your settings has been updated!" });
                                  },
                function(response){
                  $.growl.error({ message: "An error occured while updating your profile." + response.status});
                  // console.log(response.statusText);
                  // console.log(response.status);
                  // console.log("End error message");
                  // alert(response.data + " " + response.status + " "  + response.statusText + " invalid!");
                  //   switch(response.status){
                  //     case 400:
                  //       alert(response.data + " invalid!");
                  //     case 403:
                  //       window.location.replace("/academi-co/#!/forbidden");
                  //       break;
                  //     case 404:
                  //       window.location.replace("/academi-co/#!/notFound");
                  //       break;
                  //     case 500:
                  //       window.location.replace("/academi-co/#!/internalServerError");
                  //       break;
                  //   }
                  }
            
                )
      }
              
   }

});

/* Controller for signUp page */
app.controller('signUpController', function($scope, $rootScope, $http) {
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
           window.location.replace(getProtectedURL("login"));

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
              case 500:
                window.location.replace("/academi-co/#!/internalServerError");
                break;
             }
           }

         )

    }

  }

});

/* Controller for simple search */
app.controller('simplifiedSearchController', function($scope, $rootScope, $http){

$scope.search = {};

$scope.simplifiedSearch = function() {

  if(($scope.search.title == null) || ($scope.search.title == "")){

    // when the user has entered nothing, we don't search it
    // and ask him to fill out the field
    $.growl.warning({ message: "Please enter something in the field." });

  } else {

    // we redirect him to the correct result page
    searchUrl = getDomain() + '/academi-co/#!/result/title=' + $scope.search.title;
    // console.log(searchUrl);
    window.location.replace(searchUrl);

  }
};

  // the + Button redirect to newThread (does not work, because it is in the form)
  $scope.newThread = function() {

    window.location.replace(getProtectedURL("newThread"));

  }
});

/* Controller for thread page */
app.controller('threadController', function($scope, $rootScope, $http, $routeParams){
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
        case 500:
          window.location.replace("/academi-co/#!/internalServerError");
          break;
      }
  });

  // TODO: if user not connected: remove textArea
  // document.getElementById("textAreaThread").style.display = "none";

  // TODO: function onSubmit (PUT)

});

/* Controller for forbidden page */
app.controller('forbiddenController', function($scope, $rootScope, $http){

});

/* Controller for help page */
app.controller('helpController', function($scope, $rootScope, $http){

});

/* Controller for internal Server Error page */
app.controller('internalServerErrorController', function($scope, $rootScope, $http){


});

/* Controller for login page */
app.controller('loginController', function($scope, $rootScope, $http){

});

/* Controller for loginError page */
app.controller('loginErrorController', function($scope, $rootScope, $http){

});

// /* Controller for moderation page */
// app.controller('moderationController', function($scope, $rootScope, $http){
//   // TODO: no need to control the access because, web.xml does it well
//   // if modo -->
// });

/* Controller for notFound page */
app.controller('notFoundController', function($scope, $rootScope, $http){


});

/* Controller for preview page*/
app.controller('previewController', function($scope, $rootScope, $http){

});

/* Controller for logout page*/
app.controller('logoutController', function($scope, $rootScope, $http){
  
});

// TODO: one or two controller for search
//      if the user has opened advanced search, will he be able to write something in the normal search bar

// /* controller that allow only connected user to reply a thread */
// app.controller('threadTextAreaConnected', function($scope, $rootScope, $http) {

//   if(getCookie('username') == 'null'){
//     document.getElementById("textAreaThread").style.display = 'none';
//   } else {
//     document.getElementById("textAreaThread").style.display = 'block';

//   }

// });


// /* how to GET element */

// // moduleAngular
// route.controller('profilePageLoad', function($scope, $rootScope, $http) {

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
// .controller('profilePageLoad', function($scope, $rootScope, $http) {
// 	$http.get("http://localhost:8080/academi-co/resources/users/2")
// 	.then(function(response) {
// 		$scope.myProfileData = response.data;
// 	})
// });


// app
// .controller('threadLoad', function($scope, $rootScope, $http) {
// 	$http.get("http://localhost:8080/academi-co/resources/posts/2")
// 	.then(function(response) {
// 		$scope.thread = response.data;
// 	})
// });



// /* POST User (create an account) */

// app.controller('signUpController', function($scope, $rootScope, $http) {
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

// app.controller('isConnectHeader', function($scope, $rootScope, $http){
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

