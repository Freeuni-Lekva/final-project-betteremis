window.onload = function () {

    $(".reg-form").on('submit', async function () {
        try {
            const passHash = await dcodeIO.bcrypt.hash(document.getElementById("pass").value, 10);
            const gender = document.getElementsByName("Gender");
            const type = document.getElementsByName("type");
            let isMale = false;
            let isStudent = false;
            let school = "";
            for(let i = 0; i < gender.length; i++) {
                if(gender[i].checked){
                    if(gender[i].value === "Male")
                        isMale = true;
                    break;
                }
            }
            for(let i = 0; i < type.length; i++) {
                if(type[i].checked){
                    if(type[i].value === "student")
                        isStudent = true;
                    break;
                }
            }
            if(isStudent) {
                school = document.getElementById("school").value;
            }
            postData('registerServlet', {
                firstname: document.getElementById("firstname").value,
                lastname: document.getElementById("lastname").value,
                email: document.getElementById("email").value,
                passhash: passHash,
                male: isMale,
                profession: document.getElementById("profession").value,
                birthdate: document.getElementById("dateofbirth").value,
                address: document.getElementById("address").value,
                phone: document.getElementById("number").value,
                groupname: document.getElementById("groupname").value,
                type: isStudent,
                school: school
            });
        }
        catch {
            console.log('something happened while hashing or sending the login data');
        }
    });
}
async function postData(url = '', data = {}){
    const response = await fetch(url, {
        method: 'post',
        body: JSON.stringify(data)
    });
    // return response.json();


}