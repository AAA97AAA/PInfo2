function readFile() {
	  
	  if (this.files && this.files[0]) {
	    
	    var FR= new FileReader();
	    
	    FR.addEventListener("load", function(e) {
	      document.getElementById("profile_pic").src       = e.target.result;
	      document.getElementById("b64").innerHTML = e.target.result;
	    }); 
	    
	    FR.readAsDataURL( this.files[0] );
	  }
	  
	}

	document.getElementById("profile_pic").addEventListener("change", readFile);