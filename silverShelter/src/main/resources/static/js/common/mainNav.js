/*document.addEventListener('DOMContentLoaded', function() {
    
    document.querySelector('.hidden-bar').addEventListener('mouseenter', function() {

        	document.getElementById('sideNav').classList.toggle('active');
    });


        document.querySelector('.closebtn').addEventListener('click', function() {

        document.getElementById('sideNav').classList.remove('active');

    	});
    	
    	sideNav.addEventListener('mouseleave', function() {
        sideNav.classList.remove('active');
    });
});*/

document.addEventListener('DOMContentLoaded', function() {
    var hiddenBar = document.querySelector('.hidden-bar');
    var sideNav = document.getElementById('sideNav');

    if (hiddenBar && sideNav) {
        hiddenBar.addEventListener('click', function(event) {
            event.stopPropagation();
            sideNav.classList.toggle('active');
        });

        document.addEventListener('click', function(event) {
            if (!sideNav.contains(event.target) && !hiddenBar.contains(event.target)) {
                sideNav.classList.remove('active');
            }
        });

        sideNav.addEventListener('mouseleave', function() {
            sideNav.classList.remove('active');
        });
    }
});