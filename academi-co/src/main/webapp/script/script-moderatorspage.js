var app = angular.module('academi-co', [])


app
.controller('profilePageLoad', function($scope, $http) {


	$scope.myProfileData =       {
	        "author": "Brad Pitt",
	        "bio": "I'm an American actor and film producer. I has received multiple awards and nominations including an Academy Award as producer under my own company Plan B Entertainment.",
	        "posts":
	            [
	                {
	                 "author": "Brad Pitt",
	                 "title": "Is matlab usefull for partial derivations?",
	                 "content": "Hi, i'm actually working on a project group for maths stuffs, and i'd like to know if using matlab for partial derivations will be easy to use",
	                 "creationDate": "2018-02-04",
	                 "upvoters": "SoftAware",
	                 "downvoters": "SoftAware2",
	                 "score": "52",
	                 "isBanned": "false",
	                 "subject": "Mathematics",
	                 "language": "English",
	                 "topics": 
	                     [
	                         {
	                          "id": "42",
	                          "name": "Partial Derivative"
	                         },
	                         {
	                          "id": "43",
	                          "name": "Matlab"
	                         },
	                         {
	                          "id": "44",
	                          "name": "Integrals"
	                         }
	                      ]
	               },
	               { 
	                "author": "Brad Pitt", 
	                "content": "To answer your question: you need to go to preferences and uncheck the section apply this to all files",
	                "creationDate": "2018-11-22",
	                "upvoters": "SoftAware",
	                "downvoters": "SoftAware2",
	                "score": "34",
	                "isBanned": "false",
	                "language": "English"
	               },
	                { 
	                "author": "Brad Pitt", 
	                 "title": "Does anyone know how to do partial derivations in matlab?",
	                 "content": "I'm looking for a function that does partial derivations so I can use them for some integrals in matlab. Thanks for helping",
	                 "creationDate": "2018-07-12",
	                 "upvoters": "SoftAware",
	                 "downvoters": "SoftAware2",
	                 "score": "2",
	                 "isBanned": "false",
	                 "subject": "Mathematics",
	                 "language": "English",
	                 "topics": 
	                      [
	                     {
	                      "id": "42",
	                      "name": "Partial Derivative"
	                     },
	                     {
	                      "id": "43",
	                      "name": "Matlab"
	                     },
	                     {
	                      "id": "44",
	                      "name": "Integrals"
	                     }
	                   ]
	               }
	                 ]
	        };
	


});
