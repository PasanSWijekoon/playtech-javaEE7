

async function signinprocess() {

    rememberMe = document.getElementById("rememberMe").checked;
    const user_dto = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        rememberMe: rememberMe
    };

    console.log(user_dto);

    const response = await fetch(
            "SignIn",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    if (response.ok) {
        const json = await  response.json();
        if (json.success) {
            window.location.href = "index.html";
        } else {

            if (json.content === "Unverified") {
                window.location = "verify-account.html";
            } else {
                document.getElementById("message").innerHTML = json.content;
            }
        }
    } else {
        document.getElementById("message").innerHTML = "Please Try Again Later!!!!";
        }

}





async function forgotPassword() {

    const email = document.getElementById("email").value;


    const response = await fetch(
            "Forgetpassword?e=" + encodeURIComponent(email),
            {
                method: "GET"}
    );

    if (response.ok) {
        const json = await  response.json();
        if (json.success) {



            Swal.fire({
                title: "Success",
                text: "Verification code has to sent to your email.Please check your inbox",
                icon: "success"
            });

            const modalElement = document.getElementById("forgotPasswordModel");
            const modalInstance = new bootstrap.Modal(modalElement);
            modalInstance.show();

        } else {
            document.getElementById("message").innerHTML = json.content;

        }
    } else {
        document.getElementById("message").innerHTML = "Please Try Again Later!!!!";
        }

}



function showPassword() {

    var input = document.getElementById("npi");
    var i = document.getElementById("npb");

    if (input.type == "password") {
        input.type = "text";
        i.className = "bi bi-eye-fill";
    } else {
        input.type = "password";
        i.className = "bi bi-eye-slash-fill";
    }
}

function showPassword2() {
    var input = document.getElementById("rpi");
    var i = document.getElementById("rpb");

    if (input.type == "password") {
        input.type = "text";
        i.className = "bi bi-eye-fill";
    } else {
        input.type = "password";
        i.className = "bi bi-eye-slash-fill";
    }
}


async function  resetpw() {


    const data = {
        newpassword: document.getElementById("npi").value,
        confirmpassword: document.getElementById("rpi").value,
        newverificationcode: document.getElementById("vc").value
    };

    console.log(data);
    const response = await fetch('ResetPassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });


    if (response.ok) {
        const json = await  response.json();
        if (json.success) {
            window.location.href = "sign-in.html";
        } else {
            document.getElementById("message2").innerHTML = json.content;
        }
    } else {
        document.getElementById("message2").innerHTML = "Please Try Again Later!!!!";
        }

}



