// 내정보 드랍다운

/*document.addEventListener("DOMContentLoaded", function() {
    
    var memberName = document.getElementById("memberName");
    var logonDropdown = document.getElementById("logonDropdown");

    memberName.onmouseover = function() {
        logonDropdown.style.display = "block";
    }

    memberName.onmouseout = function() {
        setTimeout(function() {
            if (!logonDropdown.matches(':hover')) {
                logonDropdown.style.display = "none";
            }
        }, 250);
    }

    logonDropdown.onmouseover = function() {
        logonDropdown.style.display = "block";
    }

    logonDropdown.onmouseout = function() {
        logonDropdown.style.display = "none";
    }
});*/

document.addEventListener("DOMContentLoaded", function() {
    var memberName = document.getElementById("memberName");
    var logonDropdown = document.getElementById("logonDropdown");
	
	if(memberName == null){
		
	}
	
    if (memberName && logonDropdown) {
        memberName.onmouseover = function() {
            logonDropdown.style.display = "block";
        }

        memberName.onmouseout = function() {
            setTimeout(function() {
                if (!logonDropdown.matches(':hover')) {
                    logonDropdown.style.display = "none";
                }
            }, 250);
        }

        logonDropdown.onmouseover = function() {
            logonDropdown.style.display = "block";
        }

        logonDropdown.onmouseout = function() {
            logonDropdown.style.display = "none";
        }
    } 
});


