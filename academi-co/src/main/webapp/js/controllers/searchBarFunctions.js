/* Create a new list item when clicking on the "Add" button */
/* Function for primary tag in advanced search: when click in Add button */
function newElement1() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("primarytag").options[document.getElementById("primarytag").selectedIndex].text;
	var t = document.createTextNode(inputValue);
	li.appendChild(t);
	var containerPrimaryTag = document.getElementById("myUL1")
	var size1 = containerPrimaryTag.getElementsByTagName("li").length;

	if (inputValue === 'Select primarytag:') {
		$.growl.error({ message: "You must select something."});
	} 
	else if (size1 < 1) {		
		containerPrimaryTag.appendChild(li);
	} 
	else {
		$.growl.error({ message: "You can't add more than one primary tag." });
	}

	document.getElementById("primarytag").options[document.getElementById("primarytag").selectedIndex].text;

	var spanPrimaryTag = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	spanPrimaryTag.className = "close";
	spanPrimaryTag.appendChild(txt);
	li.appendChild(spanPrimaryTag);
	
	spanPrimaryTag.onclick = function() { 
	containerPrimaryTag.removeChild(li);

	}
}


/* Function for secondary tag in advanced search: when click in Add button */
function newElement2() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("secondarytag").text; 
	var t = document.createTextNode(inputValue);
	li.appendChild(t);
	var containerSecondaryTag = document.getElementById("myUL2")	
	var size2 = containerSecondaryTag.getElementsByTagName("li").length;
	
	if (inputValue === 'Select secondarytag:') {
		$.growl.error({ message: "You must select something." });
	} 
	else if (size2 < 3) {		
		containerSecondaryTag.appendChild(li);
	} 
	else {
		$.growl.error({ message: "You can't add more than three secondary tags." });
	}

	document.getElementById("secondarytag").text;

	var spanSecondaryTag = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	spanSecondaryTag.className = "close";
	spanSecondaryTag.appendChild(txt);
	li.appendChild(spanSecondaryTag);

	spanSecondaryTag.onclick = function() { 
	containerSecondaryTag.removeChild(li);

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


