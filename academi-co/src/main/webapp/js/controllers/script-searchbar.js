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

//Create a new list item when clicking on the "Add" button
function newElement1() {
var li = document.createElement("li");
var inputValue = document.getElementById("primarytag").value; 
var t = document.createTextNode(inputValue);
li.appendChild(t);
if (inputValue === '0') {
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

function newElement2() {
	  var li = document.createElement("li");
	  var inputValue = document.getElementById("secondarytag").value;
	  var t = document.createTextNode(inputValue);
	  li.appendChild(t);
	  if (inputValue === '') {
	    alert("You must select something!");
	  } else {
	    document.getElementById("myUL2").appendChild(li);
	  }
	  document.getElementById("secondarytag").value = "";

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




