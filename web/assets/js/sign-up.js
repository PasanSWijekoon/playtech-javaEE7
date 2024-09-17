/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


async function signupprocess() {


    const user_dto = {
        first_name: document.getElementById("f").value,
        last_name: document.getElementById("l").value,
        email: document.getElementById("e").value,
        password: document.getElementById("p").value,
    };

    const response = await fetch(
            "SignUp",
            {

                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }

    );
    if (response.ok) {
        const json = await response.json();
       if(json.success){
           window.location ="verify-account.html";
       }else{
           document.getElementById("message").innerHTML =json.content;
       }
    } else {
       document.getElementById("message").innerHTML="Please try again later";
    }
}