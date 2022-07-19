window.onload = function () {

    $(".login-form").on('submit', async function () {
        console.log("skajdkwuqdias");
        let mail = document.getElementById("mail").value;

        try {
            const passHash = await dcodeIO.bcrypt.hash(document.getElementById("pass").value, 10);
            postData('loginServlet', {
                email: mail,
                passhash: passHash
            });
        }
        catch {
            console.log('something happened while hashing or sending the login data');
        }
    })
}
async function postData(url = '', data = {}){
    const response = await fetch(url, {
            method: 'post',
            body: JSON.stringify(data)
        });
    // return response.json();


}
