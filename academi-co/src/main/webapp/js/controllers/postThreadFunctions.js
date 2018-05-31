////Create a "close" button and append it to each list item
//var myNodelist = document.getElementsByTagName("LI");
//var i;
//for (i = 0; i < myNodelist.length; i++) {
//var span = document.createElement("SPAN");
//var txt = document.createTextNode("\u00D7");
//span.className = "close";
//span.appendChild(txt);
//myNodelist[i].appendChild(span);
//}
//
////Click on a close button to hide the current list item
//var close = document.getElementsByClassName("close");
//var i;
//for (i = 0; i < close.length; i++) {
//close[i].onclick = function() {
// var div = this.parentElement;
// div.style.display = "none";
//}
//}
//
////From there, to avoid conflict with search bar dropdown, we use this for post thread page (PTP)
////Create a new list item when clicking on the "Add" button
////Create a new list item when clicking on the "Add" button
//function newElementPTP1() {
//var li = document.createElement("li");
//var inputValue = document.getElementById("primarytagPTP").value; 
//var t = document.createTextNode(inputValue);
//li.appendChild(t);
//if (inputValue === '') {
//alert("You must select something!");
//} else {
//document.getElementById("myULPTP1").appendChild(li);
//}
//document.getElementById("primarytagPTP").value = "";
//
//var span = document.createElement("SPAN");
//var txt = document.createTextNode("\u00D7");
//span.className = "close";
//span.appendChild(txt);
//li.appendChild(span);
//
//for (i = 0; i < close.length; i++) {
//close[i].onclick = function() {
// var div = this.parentElement;
// div.style.display = "none";
//}
//}
//}
//
//function newElementPTP2() {
//	  var li = document.createElement("li");
//	  var inputValue = document.getElementById("secondarytagPTP").value; 
//	  var t = document.createTextNode(inputValue);
//	  li.appendChild(t);
//	  if (inputValue === '') {
//	    alert("You must select something!");
//	  } else {
//	    document.getElementById("myULPTP2").appendChild(li);
//	  }
//	  document.getElementById("secondarytagPTP").value = "";
//
//	  var span = document.createElement("SPAN");
//	  var txt = document.createTextNode("\u00D7");
//	  span.className = "close";
//	  span.appendChild(txt);
//	  li.appendChild(span);
//
//	  for (i = 0; i < close.length; i++) {
//	    close[i].onclick = function() {
//	      var div = this.parentElement;
//	      div.style.display = "none";
//	    }
//	  }
//	}


function newElementPTP1() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("primarytagPTP").options[document.getElementById("primarytagPTP").selectedIndex].text;
	temp = inputValue.split(" ");
	inputValue = temp[1];
	var t = document.createTextNode(inputValue);
	li.appendChild(t);
	var containerPrimaryTagPTP = document.getElementById("myULPTP1")
	var size3 = containerPrimaryTagPTP.getElementsByTagName("li").length;

	if (inputValue === 'Select primarytag:') {
		$.growl.error({ message: "You must select something." });
	} 
	else if (size3 < 1) {		
		containerPrimaryTagPTP.appendChild(li);
	} 
	else {
		$.growl.error({ message: "You can't add more than one primary tag." });
	}

	document.getElementById("primarytagPTP").options[document.getElementById("primarytagPTP").selectedIndex].text;

	var spanPrimaryTagPTP = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	spanPrimaryTagPTP.className = "close";
	spanPrimaryTagPTP.appendChild(txt);
	li.appendChild(spanPrimaryTagPTP);
	
	spanPrimaryTagPTP.onclick = function() { 
	containerPrimaryTagPTP.removeChild(li);

	}
}

function newElementPTP2() {
	var li = document.createElement("li");
	var inputValue = document.getElementById("secondarytagPTP").options[document.getElementById("secondarytagPTP").selectedIndex].text; 
	var t = document.createTextNode(inputValue);
	li.appendChild(t);
	var containerSecondaryTagPTP = document.getElementById("myULPTP2")
	var size4 = containerSecondaryTagPTP.getElementsByTagName("li").length;

	if (inputValue === 'Select secondarytag:') {
		$.growl.error({ message: "You must select something." });
	} 
	else if (size4 < 3) {		
		containerSecondaryTagPTP.appendChild(li);
	} 
	else {
		$.growl.error({ message: "You can't add more than three secondary tag." });
	}

	document.getElementById("secondarytagPTP").options[document.getElementById("secondarytagPTP").selectedIndex].text;

	var spanSecondaryTagPTP = document.createElement("SPAN");
	var txt = document.createTextNode("\u00D7");
	spanSecondaryTagPTP.className = "close";
	spanSecondaryTagPTP.appendChild(txt);
	li.appendChild(spanSecondaryTagPTP);
	
	spanSecondaryTagPTP.onclick = function() { 
	containerSecondaryTagPTP.removeChild(li);

	}
}

function getIDsPrimaryTag() {
	  var elements = document.getElementById("myUL1").getElementsByTagName("li");
	  for (i = 0; i < elements.length; i++) {
	    var elem = elements[i];
	    var id1 = elem.innerText;
	    id1 = id1.split(" ");
	    //alert(id1[0]);
	  }
	}