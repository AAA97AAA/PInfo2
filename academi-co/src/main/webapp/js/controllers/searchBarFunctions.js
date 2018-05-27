//Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
var span = document.createElement("SPAN");
var txt = document.createTextNode("\u00D7");
span.className = "close";
span.appendChild(txt);
myNodelist[i].appendChild(span);
}

//Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
close[i].onclick = function() {
 var div = this.parentElement;
 div.style.display = "none";
}
}

/* Create a new list item when clicking on the "Add" button */
/* Function for primary tag in advanced search: when click in Add button */
function newElement1() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("primarytag").value; 
	var t = document.createTextNode(inputValue);
	li.appendChild(t);

	if (inputValue === 'Select primarytag:') {
		alert("You must select something!");
	} else {
	document.getElementById("myUL1").appendChild(li);
	}

	document.getElementById("primarytag").value;

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

/* Function for secondary tag in advanced search: when click in Add button */
function newElement2() {
	  var li = document.createElement("li");
	  var inputValue = document.getElementById("secondarytag").value;
	  var t = document.createTextNode(inputValue);
	  li.appendChild(t);
	  if (inputValue === 'Select secondarytag:') {
	  	alert("You must select something!");
	  } else {
	    document.getElementById("myUL2").appendChild(li);
	  }
	  document.getElementById("secondarytag").value;

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

/* function that shows or hide the advanced research element */
function showAdvancedSearch() {
	// selecting the right component 
	var researchBar = document.getElementById("advancedResearchContent");
	// if not displayed, display it
	if(researchBar.style.display == "none") {
		researchBar.style.display = "block";		
	} else {
		// if displayed, remove it
		researchBar.style.display = "none";
	}
}


