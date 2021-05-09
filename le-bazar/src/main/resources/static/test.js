localStorage.setItem('userInfo', JSON.stringify(userInfo));

document.getElementById("my-profile").addEventListener("click", getMe);

function getMe(e) {
    e.preventDefault();
    const token = JSON.parse(localStorage.getItem('token'));
    console.log(`Authorization=Bearer ${token}`)
    fetch('http://localhost/api/auth/login', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            // window.location.href = 'http://localhost:3000/dashboard';
        })
        .catch(err => { console.log(err) })
}