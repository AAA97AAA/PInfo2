function openNav() {
    document.getElementById("mySidenav").style.height = "800px";
    document.getElementById("main").style.marginTop = "800px";
    document.getElementById("mySidenav").style.display = "block";
    document.body.style.backgroundColor = "rgba(0,0,0,0.2)";
}

function closeNav() {
    document.getElementById("mySidenav").style.height = "0px";
    document.getElementById("main").style.marginTop= "0px";
    document.getElementById("mySidenav").style.display = "none";
    document.body.style.backgroundColor = "white";
}

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