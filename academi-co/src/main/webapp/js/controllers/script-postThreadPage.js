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

//From there, to avoid conflict with search bar dropdown, we use this for post thread page (PTP)
//Create a new list item when clicking on the "Add" button
//Create a new list item when clicking on the "Add" button
function newElementPTP1() {
var li = document.createElement("li");
var inputValue = document.getElementById("myInputPTP1").value;
var t = document.createTextNode(inputValue);
li.appendChild(t);
if (inputValue === '') {
alert("You must write something!");
} else {
document.getElementById("myULPTP1").appendChild(li);
}
document.getElementById("myInputPTP1").value = "";

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

function newElementPTP2() {
	  var li = document.createElement("li");
	  var inputValue = document.getElementById("myInputPTP2").value;
	  var t = document.createTextNode(inputValue);
	  li.appendChild(t);
	  if (inputValue === '') {
	    alert("You must write something!");
	  } else {
	    document.getElementById("myULPTP2").appendChild(li);
	  }
	  document.getElementById("myInputPTP2").value = "";

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