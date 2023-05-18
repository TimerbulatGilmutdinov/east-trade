const searchInput = document.getElementById("searchInput");
const searchResults = document.getElementById("searchResults");
const errorMsg = document.getElementById("errorMsg");

searchInput.addEventListener("input", function() {
    const searchTerm = this.value.trim();

    fetch(`/search/${searchTerm}`)
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                searchResults.innerHTML = "";
                errorMsg.innerText = "No results found.";
                return;
            }

            errorMsg.innerText = "";
            searchResults.innerHTML = "";
            data.forEach(user => {
                const li = document.createElement("li");
                li.innerText = `${user.name} ${user.surname}`;
                li.addEventListener("click", function() {
                    window.location.href = `/users/${user.id}`;
                });
                searchResults.appendChild(li);
            });
        })
        .catch(error => console.log(error));
});