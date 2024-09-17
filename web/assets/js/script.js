function signUp() {
  var f = document.getElementById("f");
  var l = document.getElementById("l");
  var e = document.getElementById("e");
  var p = document.getElementById("p");
  var mob = document.getElementById("mob");
  var g = document.getElementById("g");

  var form = new FormData();
  form.append("f", f.value);
  form.append("l", l.value);
  form.append("e", e.value);
  form.append("p", p.value);
  form.append("mob", mob.value);
  form.append("g", g.value);

  var request = new XMLHttpRequest();

  request.onreadystatechange = function () {
    if (request.readyState == 4) {
      var text = request.responseText;
      if (text == "success") {
        document.getElementById("msg").innerHTML = text;
        document.getElementById("msg").className = "bi bi-check2-circle fs-5";
        document.getElementById("msgdiv").className = "d-block";

        window.location.href = "Signin.php";
      } else {
        document.getElementById("msg").innerHTML = text;
        document.getElementById("msgdiv").className = "d-block";
      }
    }
  };

  request.open("POST", "signUpProcess.php", true);
  request.send(form);
}

function signIn() {
  var email = document.getElementById("email2");
  var password = document.getElementById("password2");
  var rememberme = document.getElementById("rememberme");

  var f = new FormData();
  f.append("e", email.value);
  f.append("p", password.value);
  f.append("r", rememberme.checked);

  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
    if (r.readyState == 4) {
      var t = r.responseText;
      if (t == "success") {
        window.location.href = "profile.php";
      } else {
        document.getElementById("msg").innerHTML = t;
        document.getElementById("msgdiv").className = "d-block";
      }
    }
  };

  r.open("POST", "signInProcess.php", true);
  r.send(f);
}


function payNoww(){
  alert("Connection Error");
}
var bm;
function forgotPassword() {

    var email = document.getElementById("email2");

    var r = new XMLHttpRequest();
    r.onreadystatechange = function () {
        if (r.readyState == 4) {
            var t = r.responseText;
            if (t == "Success") {
                alert("Verification code has to sent to your email.Please check your inbox");

                var m = document.getElementById("forgotPasswordModel");
                bm = new bootstrap.Modal(m);
                bm.show();


            } else {
                alert(t);
            }

        }
    }

    r.open("GET", "forgotPasswordProcess.php?e=" + email.value, true);
    r.send();


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

function resetpw() {



  var email = document.getElementById("email2");
  var np = document.getElementById("npi");
  var rnp = document.getElementById("rpi");
  var vcode = document.getElementById("vc");

  var f = new FormData();
  f.append("e", email.value);
  f.append("n", np.value);
  f.append("r", rnp.value);
  f.append("v", vcode.value);


  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
      if (r.readyState == 4) {
          var t = r.responseText;
          if (t == "success") {

              bm.hide();
              alert("Password reset success");
          } else {

              alert(t);
          }




      }
  }



  r.open("POST", "resetPassword.php", true);
  r.send(f);

}

function advancedSearch(x) {

  var txt = document.getElementById("t");
  var category = document.getElementById("c1");
  var brand = document.getElementById("b");
  var model = document.getElementById("m");
  var condition = document.getElementById("c2");
  var colour = document.getElementById("c3");
  var from = document.getElementById("pf");
  var to = document.getElementById("pt");


  var f = new FormData();
  f.append("t", txt.value);
  f.append("cat", category.value);
  f.append("b", brand.value);
  f.append("m", model.value);
  f.append("con", condition.value);
  f.append("col", colour.value);
  f.append("pf", from.value);
  f.append("to", to.value);

  f.append("page", x);


  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
      if (r.readyState == 4) {
          var t = r.responseText;
          document.getElementById("view_area").innerHTML = t;
      }
  }

  r.open("POST", "advancedSearchProcess.php", true);
  r.send(f);

}


function submitSearchForm() {
  const form = document.getElementById("searchForm");
  const formData = new FormData(form);
  const params = new URLSearchParams(formData).toString();
  window.location.href = "searchResults.php?" + params;
}


function checkValue() {
  var input = document.getElementById("myInput");
  var maxValueParagraph = document.getElementById("qtygana");

  // Retrieve the maximum value from the hidden paragraph
  var maxValue = parseInt(maxValueParagraph.textContent.trim());

  var value = parseInt(input.value); // Parse the input value as an integer

  // Check if the value is within the allowed range
  if (value < 1) {
      alert("Value is too low. Minimum value allowed is 1");
      input.value = 1; // Set the input value to the minimum allowed value
  } else if (value > maxValue) {
      alert("Value is too high. Maximum value allowed is " + maxValue);
      input.value = maxValue; // Set the input value to the maximum allowed value
  }
}


function removeFromWatchlist(id) {

  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {

      if (r.readyState == 4) {
          var t = r.responseText;
          if (t == "Success") {
            window.location.reload();
          } else {
              alert(t);
          }
      }
  }

  r.open("GET", "removeWatchlistProcess.php?id=" + id, true);
  r.send();
}

function addToWatchlist(id) {
  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
      if (r.readyState == 4) {
          var t = r.responseText;
          if (t == "removed") {
              document.getElementById("heart" + id).style.className = "text-dark";
              window.location.reload();
          } else if (t == "added") {
              document.getElementById("heart" + id).style.className = "text-danger";
              window.location.reload();
          }
          alert(t);
      }
  }

  r.open("GET", "addToWatchlistProcess.php?id=" + id, true);
  r.send();
}


function deleteFromCart(id) {

  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
      if (r.readyState == 4) {
          var t = r.responseText;
          if (t == "success") {
              alert("Product removed from cart");
              window.location.reload();
          } else {
              alert(t);
          }
      }
  }

  r.open("GET", "deleteFromCartProcess.php?id=" + id, true);
  r.send()
}

function addToCart(id) {

  var r = new XMLHttpRequest();

  r.onreadystatechange = function () {
      if (r.readyState == 4) {
          var t = r.responseText;
          alert(t);
      }
  }

  r.open("GET", "addToCartProcess.php?id=" + id, true);

  r.send();
}



function changeQTY(id) {
  var qty = document.getElementById("qty_num").value;

  var request = new XMLHttpRequest();

  request.onreadystatechange = function () {
      if (request.status == 200 & request.readyState == 4) {
          var response = request.responseText;
          if (response == "Updated") {
              window.location.reload();
          } else {
              alert(response);
          }
      }
  }

  request.open("GET", "cartQtyUpdateProcess.php?qty=" + qty + "&id=" + id, true);
  request.send();

}


function payNow(id) {

  var qty = document.getElementById("myInput").value;

  var request = new XMLHttpRequest();

  request.onreadystatechange = function () {
      if (request.status == 200 & request.readyState == 4) {
          var response = request.responseText;

          var obj = JSON.parse(response);

          var mail = obj["umail"];
          var amount = obj["amount"];

          if (response == 1) {
              alert("Please Login.");
              window.location = "Signin.php";
          } else if (response == 2) {
              alert("Please update your profile.");
              window.location = "profile.php";
          } else {

              // Payment completed. It can be a successful failure.
              payhere.onCompleted = function onCompleted(orderId) {
                  console.log("Payment completed. OrderID:" + orderId);

                  alert("Payment completed. OrderID:" + orderId);
                  saveInvoice(orderId, id, mail, amount, qty);

              };

              // Payment window closed
              payhere.onDismissed = function onDismissed() {
                  // Note: Prompt user to pay again or show an error page
                  console.log("Payment dismissed");
              };

              // Error occurred
              payhere.onError = function onError(error) {
                  // Note: show an error page
                  console.log("Error:" + error);
              };

              // Put the payment variables here
              var payment = {
                  "sandbox": true,
                  "merchant_id": obj["mid"],    // Replace your Merchant ID
                  "return_url": "http://localhost/eshop/singleProductView.php?id=" + id,     // Important
                  "cancel_url": "http://localhost/eshop/singleProductView.php?id=" + id,     // Important
                  "notify_url": "http://sample.com/notify",
                  "order_id": obj["id"],
                  "items": obj["item"],
                  "amount": amount + ".00",
                  "currency": "LKR",
                  "hash": obj["hash"], // *Replace with generated hash retrieved from backend
                  "first_name": obj["fname"],
                  "last_name": obj["lname"],
                  "email": mail,
                  "phone": obj["mobile"],
                  "address": obj["address"],
                  "city": obj["city"],
                  "country": "Sri Lanka",
                  "delivery_address": obj["address"],
                  "delivery_city": obj["city"],
                  "delivery_country": "Sri Lanka",
                  "custom_1": "",
                  "custom_2": ""
              };

              // Show the payhere.js popup, when "PayHere Pay" is clicked
              // document.getElementById('payhere-payment').onclick = function (e) {
              payhere.startPayment(payment);
              // };

          }

      }
  }

  request.open("GET", "buyNowProcess.php?id=" + id + "&qty=" + qty, true);
  request.send();
}


function saveInvoice(orderId, id, mail, amount, qty) {

  var form = new FormData();
  form.append("o", orderId);
  form.append("i", id);
  form.append("m", mail);
  form.append("a", amount);
  form.append("q", qty);

  var request = new XMLHttpRequest();

  request.onreadystatechange = function () {
      if (request.status == 200 & request.readyState == 4) {
          var response = request.responseText;

          if (response == "success") {
              window.location = "invoice.php?id=" + orderId;
          } else {
              alert(response);
          }
      }
  }

  request.open("POST", "saveInvoiceProcess.php", true);
  request.send(form);

}

function printInvoice() {
  var restorePage = document.body.innerHTML;
  var page = document.getElementById("page").innerHTML;
  document.body.innerHTML = page;
  window.print();
  document.body.innerHTML = restorePage;
}
