const form = document.querySelector('form');
form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new URLSearchParams(new FormData(event.target));
    fetch('/login', {
        method: 'POST',
        body: formData,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin'
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/profile';
            } else {
                throw new Error('invalid credentials');
            }
        })
        .catch(error => {
            alert(error.message)
        });
});