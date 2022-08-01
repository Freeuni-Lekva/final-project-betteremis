(function () {
    const BASE_URL = 'https://lighthouse-user-api.herokuapp.com/'
    const INDEX_URL = BASE_URL + 'api/v1/users'
    const data = []
    const dataPanel = document.querySelector('#data-panel')

    const searchBtn = document.getElementById('submit-search')
    const searchInput = document.getElementById('search')

    const pagination = document.getElementById('pagination')
    const ITEM_PER_PAGE = 16
    let paginationData = []

    const prev = document.querySelector('.prev')
    const next = document.querySelector('.next')

    axios
        .get(INDEX_URL)
        .then((response) => {
            data.push(...response.data.results)
            displayPeopleList(data)
            getTotalPages(data)
            getPageData(1, data)
        })
        .catch(error => console.log(error))

    dataPanel.addEventListener('click', (event) => {
        if (event.target.matches('.show-photo')) {
            showPhoto(event.target.dataset.id)
        } else if (event.target.matches('.btn-add-friend')) {
            addFriend(event.target.dataset.id)
        }
    })

    // listen to search btn click event
    searchBtn.addEventListener('click', event => {
        let results = []
        event.preventDefault()

        const regex = new RegExp(searchInput.value, 'i')
        results = data.filter(people => people.name.match(regex))
        console.log(results)
        getTotalPages(results)
        getPageData(1, results)
    })

    // listen to pagination click event
    pagination.addEventListener('click', event => {
        console.log(event.target.dataset.page)
        if (event.target.tagName === 'A') {
            getPageData(event.target.dataset.page)
        }
    })

    prev.addEventListener('click', event => {
        getPageData(1, data)
    })

    next.addEventListener('click', event => {
        getPageData(13, data)
    })


    function displayPeopleList(data) {
        let htmlContent = ''
        data.forEach(function (item, index) {
            htmlContent += `
      <div class="col-sm-3">
        <div class="card mb-2" id="card-list">
          <img class="card-img-top show-photo" data-toggle="modal" data-target="#show-photo-modal" data-id="${item.id}" src="${item.avatar}" title="點擊查看詳細資料" alt="Card image cap">

          <div class="card-body">
            <h6>${item.name}</h6>
            <button class="btn btn-info btn-add-friend" data-id="${item.id}">add</button>
          </div>
        </div>
      </div>
    `
        })
        dataPanel.innerHTML = htmlContent
    }

    function showPhoto(id) {
        const modalTitle = document.getElementById('show-photo-title')
        const modalImage = document.getElementById('show-photo-image')
        const modalEmail = document.getElementById('show-photo-email')
        const modalGender = document.getElementById('show-photo-gender')
        const modalAge = document.getElementById('show-photo-age')
        const modalRegion = document.getElementById('show-photo-region')
        const modalBirthday = document.getElementById('show-photo-birthday')

        const url = INDEX_URL + '/' + id
        console.log(url)

        axios
            .get(url)
            .then((response) => {
                const people = response.data
                console.log(people)

                modalTitle.textContent = people.name + ' ' + people.surname
                modalImage.innerHTML = `<img src="${people.avatar}" class="img-fluid" alt="Responsive image">`
                modalEmail.textContent = `Email : ${people.email}`
                modalGender.textContent = `Gender : ${people.gender}`
                modalAge.textContent = `Age : ${people.age}`
                modalRegion.textContent = `Region : ${people.region}`
                modalBirthday.textContent = `Birthday : ${people.birthday}`
            })
    }

    function addFriend(id) {
        const list = JSON.parse(localStorage.getItem('friendList')) || []
        const people = data.find(item => item.id === Number(id))

        if (list.some(item => item.id === Number(id))) {
            alert(`${people.name} is already in your friend list.`)
        } else {
            list.push(people)
            alert(`Added ${people.name} to your friend list!`)
        }
        localStorage.setItem('friendList', JSON.stringify(list))
    }

    function getTotalPages(data) {
        let totalPages = Math.ceil(data.length / ITEM_PER_PAGE) || 1
        let pageItemConetnt = ""

        for (let i = 0; i < totalPages; i++) {
            pageItemConetnt += `
      <li class="page-item">
        <a class="page-link" href="javascript:;" data-page="${i + 1}">${i + 1}</a>
      </li>
      `
        }
        pagination.innerHTML = pageItemConetnt
    }

    function getPageData(pageNum, data) {
        paginationData = data || paginationData
        let offset = (pageNum - 1) * ITEM_PER_PAGE
        let pageData = paginationData.slice(offset, offset + ITEM_PER_PAGE)
        displayPeopleList(pageData)
    }

})()