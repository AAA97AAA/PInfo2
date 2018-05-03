var app = angular.module('persons', ['ngResource', 'ngGrid', 'ui.bootstrap']);

// Create a controller with name personsListController to bind to the grid section.
app.controller('personsListController', function ($scope, $rootScope, personService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.persons = {currentPage: 1};

    $scope.gridOptions = {
        data: 'persons.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'Id' },
            { field: 'name', displayName: 'Name' },
            { field: 'description', displayName: 'Description' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('personSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listPersonsArgs = {
            page: $scope.persons.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        personService.get(listPersonsArgs, function (data) {
            $scope.persons = data;
        })
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deletePerson', row.entity.id);
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.persons = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

// Create a controller with name personsFormController to bind to the form section.
app.controller('personsFormController', function ($scope, $rootScope, personService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.person = null;
        // For some reason, I was unable to clear field values with type 'url' if the value is invalid.
        // This is a workaroud. Needs proper investigation.
        document.getElementById('imageUrl').value = null;
        // Resets the form validation state.
        $scope.personForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a person.
    $scope.updatePerson = function () {
        personService.save($scope.person).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('personSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('personSelected', function (event, id) {
        $scope.person = personService.get({id: id});
    });

    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deletePerson', function (event, id) {
        personService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('personDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
    
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('personSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('personDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides persons operations
app.factory('personService', function ($resource) {
    return $resource('resources/persons/:id');
    
    
});




var app = angular.module('myApp', []);

app.controller('AppCtrl', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {
  
  // Load the data
  $http.get('http://www.corsproxy.com/loripsum.net/api/plaintext').then(function (res) {
		$scope.loremIpsum = res.data;
    $timeout(expand, 0);
  });
  
  $scope.autoExpand = function(e) {
        var element = typeof e === 'object' ? e.target : document.getElementById(e);
    		var scrollHeight = element.scrollHeight -60; // replace 60 by the sum of padding-top and padding-bottom
        element.style.height =  scrollHeight + "px";    
    };
  
  function expand() {
    $scope.autoExpand('TextArea');
  }
}]);

var app = angular.module('app', [])
app.controller('Main', ['$scope', function($scope) {
  $scope.like = {};
  $scope.like.votes = 0;
  $scope.like.opendoor1 = false;
  $scope.like.opendoor2 = false;
  $scope.doLike = function() {
    if ($scope.like.userVotes == 1) {
      delete $scope.like.userVotes;
      $scope.like.votes--;
    } else {
      $scope.like.userVotes = 1;
      $scope.like.votes++;
    }
  }
  $scope.doDislike = function() {
    if ($scope.like.userVotes == 1) {
      delete $scope.like.userVotes;
      $scope.like.votes++;
    } else {
      $scope.like.userVotes = 1;
      $scope.like.votes--;
    }
  }
}]);

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

var app = angular.module('app', [])
.controller('Main', ['$scope', function($scope) {
  $scope.like = {};
  $scope.like.votes = 0;
  $scope.light1 = -1;
  $scope.light2 = -1;
  $scope.token = 1;
  $scope.Door1 = true;
  $scope.Door2 = true;
  $scope.doLike = function() {
    if ($scope.token == 0 && $scope.Door1 != true) { 
      $scope.like.votes--; //when button is pressed already, increment
      $scope.light1 *= (-1);
      $scope.token++;
      $scope.Door2 = false;
    }
    else if ($scope.token == 1) {
      $scope.light1 *= (-1);
      $scope.like.votes++; //when button isn't pressed, increment
      $scope.token--;
      $scope.Door1 = false;
      $scope.Door2 = true;
    }
  }
  $scope.doDislike = function() {
    if ($scope.token == 0  && $scope.Door2 != true) {
      $scope.like.votes++; //when button is pressed already, decrement
      $scope.light2 *= (-1);
      $scope.token++;
      $scope.Door1 = false;
    } 
    else if ($scope.token == 1){
      $scope.light2 *= (-1);
      $scope.like.votes--; //when button isn't pressed, decrement
      $scope.token--;
      $scope.Door2 = false;
      $scope.Door1 = true;
    }
  }
}]);


var app = angular.module('myApp2', []);
app.controller('myCtrl', function($scope, $http) {

$scope.intake = function othername() {
    var input = document.getElementById("userInput").value;
    output = "&quot"+ "<p>" + input + "</p>" + "&quot";
    document.getElementById('output').innerHTML = output;
}
});

var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
	
	
	$scope.intake = function othername() {
	    var input = document.getElementById("userInput").value;

	    
	    var BEGIN = '<div class=" w3-container w3-card w3-white w3-round w3-margin"><br><img src="images/fakeprofilepic1.jpg" alt="Avatar" class="w3-left w3-margin-right" style="width:40px"><span class="w3-right w3-opacity">1 min</span><h5>Brad Pitt<div class="content"><hr><div style="width:5%; float:right"><span style="color:gray"><i class="glyphicon glyphicon-upload"></i></span><br>0<br><span style="color:gray"><i class="glyphicon glyphicon-download"></i></span></div><div style="width:95%; float:left">';
	    
	    var END =   '<br></div></h5></div>'

	    
	    output = "<p>" + input + "</p>";
	    document.getElementById('output').innerHTML = BEGIN + output + END;
	

	    userInput.value = userInput.defaultValue;
	   

	}

      $scope.myWelcome = {
    		  "title": "Does anyone know how to do partial derivative in matlab ?",
    		  "answers": [
    		  {
    		  "id": "25",
    		  "author": "Angelina Jolie",
    		  "content": "Hi, try the documentation using help command of matlab, the function is called diff(f,x) as I remember",
    		  "creationDate": "2018-02-04",
    		  "upvoters": "SoftAware",
    		  "downvoters": "SoftAware2",
    		  "score": "52",
    		  "isBanned": "false"
    		  },
    		  {
    		  "id": "27",
    		  "author": "Jennifer Aniston",
    		  "content": "Which version of matlab are you using ? There's a new function for partial derivations that is faster than diff in the 2017 version of matlab",
    		  "creationDate": "2018-02-05",
    		  "upvoters": "SoftAware",
    		  "downvoters": "SoftAware2",
    		  "score": "10",
    		  "isBanned": "false"
    		  }
    		  ],
    		  "subject": "Mathematics",
    		  "language": "English",
    		  "topics": [
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
    		  ],
    		  "content": "I'm looking for a function that does partial derivations so I can use them for some integrals in matlab. Thanks for helping",
    		  "creationDate": "2018-02-01",
    		  "upvoters": "SoftAware3",
    		  "downvoters": "softAware4",
    		  "score": "-5",
    		  "isBanned": "false"
    		  }
});



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

/*
moduleAngular
.controller('profilePageLoad', function($scope, $http) {
	$http.get("academi-co.ch/profilePage")
	.then(function(response) {
		$scope.myProfileData = response.data;
	})
});

*/


