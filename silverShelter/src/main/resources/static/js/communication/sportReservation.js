document.addEventListener("DOMContentLoaded", function() {
    const wrappers = document.querySelectorAll(".image-wrapper");
    const selectedSportInput = document.getElementById("selected-sport");

    wrappers.forEach(wrapper => {
        wrapper.addEventListener("click", function() {
            wrappers.forEach(wrap => wrap.classList.remove("selected"));
            wrapper.classList.add("selected");
            selectedSportInput.value = wrapper.getAttribute("data-value");
        });
    });
});