async function loadData() {
    const response = await fetch("LoadPurchaseData");
    if (response.ok) {
        const json = await response.json();

        console.log(json);

        let st_tbody = document.getElementById("st-tbody");
        let st_item_tr = document.getElementById("st-item-tr");
        st_tbody.innerHTML = "";

        json.OrderList.forEach(listoforders => {


            listoforders.OrderItems.forEach(orders => {
                let st_item_clone = st_item_tr.cloneNode(true);
                let itemSubtotail = orders.product.price * orders.qty;

                st_item_clone.querySelector("#st-item-title").innerHTML = orders.product.title;
                st_item_clone.querySelector("#similar-product-a1").href = "single-product.html?id=" + orders.product.id;
                st_item_clone.querySelector("#similar-product-image").src = "product-images/" + orders.product.id + "/image1.png";
                st_item_clone.querySelector("#st-item-seller").innerHTML = orders.product.user.email;
                st_item_clone.querySelector("#st-item-qty").innerHTML = orders.qty;
                st_item_clone.querySelector("#st-item-total").innerHTML = itemSubtotail;
                st_item_clone.querySelector("#st-item-date").innerHTML = orders.order.date_time;
                st_item_clone.querySelector("#st-item-invoicedata").href = "invoice.html?id=" + orders.order.id;


                //st_item_clone.querySelector("#st-item-qty").innerHTML = orders.product.user.email;
                st_tbody.appendChild(st_item_clone);

            });

        });



    } else {


        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });
    }

}