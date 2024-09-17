async function loadData() {
    const response = await fetch("LoadProfileData");
    if (response.ok) {
        const json = await response.json();
        if (json.success) {

            const cityList = json.cityList;
            loadSelect("citydata", cityList, "name");

            if (json.address) {

                const address = json.address;
                console.log(address);

                document.getElementById("userfirstname").innerHTML = address.user.first_name;
                document.getElementById("userlastname").innerHTML = address.user.last_name;
                document.getElementById("fname").value = address.user.first_name;
                document.getElementById("lname").value = address.user.last_name;
                document.getElementById("email").value = address.user.email;
                document.getElementById("line1").value = address.line1;
                document.getElementById("line2").value = address.line2;
                document.getElementById("citydata").value = address.city.id;
                document.getElementById("pcode").value = address.postal_code;
                document.getElementById("mobile").value = address.mobile;
                document.getElementById("password").value = address.user.password;
                
                document.getElementById("blah").src = "profile-images/" + address.user.id + "image.png";
                document.getElementById("blah").onerror = function() { this.src = 'assets/img/customer/customer5.jpg'; };


            } else {
                const user = json.user;

                document.getElementById("userfirstname").innerHTML = user.first_name;
                document.getElementById("userlastname").innerHTML = user.last_name;
                document.getElementById("fname").value = user.first_name;
                document.getElementById("lname").value = user.last_name;
                document.getElementById("email").value = user.email;
                document.getElementById("password").value = user.password;
                
                document.getElementById("blah").src = "profile-images/" + user.id + "image.png";
                document.getElementById("blah").onerror = function() { this.src = 'assets/img/customer/customer5.jpg'; };

            }





        } else {
            window.location.reload("1ndex.html");
        }


    } else {
        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });


    }

}

function loadSelect(selectTagId, list, property) {

    const selectTag = document.getElementById(selectTagId);
    list.forEach(item => {
        let optionTag = document.createElement("option");
        optionTag.value = item.id;
        optionTag.innerHTML = item[property];
        selectTag.appendChild(optionTag);

    });

}

async function  updateProfile() {

    var fname = document.getElementById("fname");
    var lname = document.getElementById("lname");
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var line1 = document.getElementById("line1");
    var line2 = document.getElementById("line2");
    var city = document.getElementById("citydata");
    var pcode = document.getElementById("pcode");
    var mobile = document.getElementById("mobile");


    var f = new FormData();

    f.append("fn", fname.value);
    f.append("ln", lname.value);
    f.append("m", mobile.value);
    f.append("l1", line1.value);
    f.append("l2", line2.value);
    f.append("p", email.value);
    f.append("d", password.value);
    f.append("c", city.value);
    f.append("pc", pcode.value);



    const response = await fetch("UpdateProfileData", {
        method: "POST",
        body: f

    });

    if (response.ok) {
        const json = await response.json();
        console.log(json);

        if (json.success) {

            Swal.fire({
                title: "Success",
                text: json.message,
                icon: "success"
            });

        } else {

            Swal.fire({
                title: "Success",
                text: json.message,
                icon: "error"
            });
        }

    } else {

        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });
    }

}


async function userpicturesupdate() {

    const image1Tag = document.getElementById("imgInp");

    const data = new FormData();

    data.append("image1", image1Tag.files[0]);

    const response = await fetch(
            "UserImageUpdate",
            {
                method: "POST",
                body: data
            }
    );


    if (response.ok) {
        const json = await response.json();

        if (json.success) {

            Swal.fire({
                title: "Success",
                text: json.content,
                icon: "success"
            });
        } else {
            Swal.fire({
                title: "Error Occured",
                text: json.content,
                icon: "error"
            });
        }
    } else {
        document.getElementById("message").innerHTML = "Please try again later!";
        }
}