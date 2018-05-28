//Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
var span = document.createElement("SPAN");
var txt = document.createTextNode("\u00D7");
span.className = "closePrimaryTag";
span.appendChild(txt);
myNodelist[i].appendChild(span);
}

//Click on a close button to hide the current list item
var closePrimaryTag = document.getElementsByClassName("close");
var i;
for (i = 0; i < closePrimaryTag.length; i++) {
 closePrimaryTag[i].onclick = function() {
 var div = this.parentElement;
 div.style.display = "none";
}
}

//Click on a close button to hide the current list item
var closeSecondaryTag = document.getElementsByClassName("close");
var j;
for (j = 0; j < closeSecondaryTag.length; j++) {
closeSecondaryTag[j].onclick = function() {
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
	var containerPrimaryTag = document.getElementById("myUL1")
	var size1 = containerPrimaryTag.getElementsByTagName("li").length;
	
	if (inputValue === 'Select primarytag:') {
		alert("You must select something!");
	} 
	else if (size1 < 1) {		
		containerPrimaryTag.appendChild(li);
	} 
	else {
		alert("You can't add more than 1 primary tag");
	}

	document.getElementById("primarytag").value;

	var span = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	span.className = "close";
	span.appendChild(txt);
	li.appendChild(span);
	
	for (i = 0; i < closePrimaryTag.length; i++) {
		closePrimaryTag[i].onclick = function() { 
			containerPrimaryTag.removeChild(containerPrimaryTag.childNodes[i]);
		}
	}
}


/* Function for secondary tag in advanced search: when click in Add button */
function newElement2() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("secondarytag").value; 
	var t = document.createTextNode(inputValue);
	li.appendChild(t);
	
	var size2 = document.getElementById("myUL2").getElementsByTagName("li").length;
	
	if (inputValue === 'Select secondarytag:') {
		alert("You must select something!");
	} 
	else if (size2 < 3) {		
		document.getElementById("myUL2").appendChild(li);
	} 
	else {
		alert("You can't add more than 3 secondary tag");
	}

	document.getElementById("secondarytag").value;

	var span = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	span.className = "close";
	span.appendChild(txt);
	li.appendChild(span);
	
	for (j = 0; j < closeSecondaryTag.length; j++) {
		closeSecondaryTag[j].onclick = function() { 
			var div = this.parentElement;
			document.getElementById("myUL2").removeChild(document.getElementById("myUL2").childNodes[j]);
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


