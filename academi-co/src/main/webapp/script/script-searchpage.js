// Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}

// Create a new list item when clicking on the "Add" button
function newElement1() {
  var li = document.createElement("li");
  var inputValue = document.getElementById("myInput1").value;
  var t = document.createTextNode(inputValue);
  li.appendChild(t);
  if (inputValue === '') {
    alert("You must write something!");
  } else {
    document.getElementById("myUL1").appendChild(li);
  }
  document.getElementById("myInput1").value = "";

  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  li.appendChild(span);

  for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
      var div = this.parentElement;
      div.style.display = "none";
    }
  }
}

function newElement2() {
	  var li = document.createElement("li");
	  var inputValue = document.getElementById("myInput2").value;
	  var t = document.createTextNode(inputValue);
	  li.appendChild(t);
	  if (inputValue === '') {
	    alert("You must write something!");
	  } else {
	    document.getElementById("myUL2").appendChild(li);
	  }
	  document.getElementById("myInput2").value = "";

	  var span = document.createElement("SPAN");
	  var txt = document.createTextNode("\u00D7");
	  span.className = "close";
	  span.appendChild(txt);
	  li.appendChild(span);

	  for (i = 0; i < close.length; i++) {
	    close[i].onclick = function() {
	      var div = this.parentElement;
	      div.style.display = "none";
	    }
	  }
	}

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
