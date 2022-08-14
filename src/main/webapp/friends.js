(function () {
    const dataPanel = document.querySelector('#data-panel')
    dataPanel.addEventListener('click', (event) => {
        if (event.target.matches('.show-photo')) {
            showPhoto(event.target.dataset)
        } else if (event.target.matches('.btn-add-friend')) {
            addFriend(event.target.dataset.id)
        }
    })

    function showPhoto(data) {
        const modalFirstName = document.getElementById('show-photo-firstname')
        const modalLastName = document.getElementById('show-photo-lastname')
        const modalEmail = document.getElementById('show-photo-email')
        const modalType = document.getElementById('show-photo-type')
        const modalImage = document.getElementById('show-photo-image')
        var id = data.id
        var email = data.email
        var type = data.type
        var name = data.firstname
        var lastname = data.lastname
        modalImage.innerHTML = `<img src="https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg" class="img-fluid" alt="Responsive image">`
        modalEmail.textContent = `Email:  ${email}`
        modalType.textContent = `${type}`
        modalFirstName.textContent = `First name:  ${name}`
        modalLastName.textContent = `Last Name:  ${lastname}`
    }


})()