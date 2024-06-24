document.addEventListener('DOMContentLoaded', function() {
    var hiddenBar = document.querySelector('.hidden-bar');
    var sideNav = document.getElementById('sideNav');

    if (hiddenBar && sideNav) {
        //console.log('hiddenBar and sideNav found');

        hiddenBar.addEventListener('click', function(event) {
            event.stopPropagation();
            sideNav.classList.toggle('active');
            //console.log('hiddenBar clicked');
        });

        document.addEventListener('click', function(event) {
            if (!sideNav.contains(event.target) && !hiddenBar.contains(event.target)) {
                sideNav.classList.remove('active');
                //console.log('Clicked outside sideNav and hiddenBar');
            }
        });

        sideNav.addEventListener('mouseleave', function() {
            sideNav.classList.remove('active');
            //console.log('sideNav mouseleave');
        });
    } else {
        //console.log('hiddenBar or sideNav not found');
    }
});

