document.addEventListener('DOMContentLoaded', function() {
    
    document.querySelector('.hidden-bar').addEventListener('mouseenter', function() {

        	document.getElementById('sideNav').classList.toggle('active');
    });


       /* document.querySelector('.closebtn').addEventListener('click', function() {

        document.getElementById('sideNav').classList.remove('active');

    	});*/
    	
    	sideNav.addEventListener('mouseleave', function() {
        sideNav.classList.remove('active');
    });
});