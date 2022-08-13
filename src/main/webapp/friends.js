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
        const modalImage = document.getElementById('show-photo-image')
        const modalEmail = document.getElementById('show-photo-email')
        const modalGender = document.getElementById('show-photo-gender')
        const modalAge = document.getElementById('show-photo-age')
        const modalRegion = document.getElementById('show-photo-region')
        const modalBirthday = document.getElementById('show-photo-birthday')
        var id = data.id
        var email = data.email
        var type = data.type
        modalImage.innerHTML = `<img src="https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg" class="img-fluid" alt="Responsive image">`
        modalEmail.textContent = `Email : ${email}`
        modalGender.textContent = `Gender : male`
        modalAge.textContent = `Type : ${type}`
        modalRegion.textContent = `Region : usa`
        modalBirthday.textContent = `Birthday : lol`
    }


})()