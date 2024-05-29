document.addEventListener('DOMContentLoaded', function() {
    
    document.querySelector('.hidden-bar').addEventListener('click', function() {

        	document.getElementById('sideNav').classList.toggle('active');
    });


        document.querySelector('.closebtn').addEventListener('click', function() {

        document.getElementById('sideNav').classList.remove('active');

    	});
});