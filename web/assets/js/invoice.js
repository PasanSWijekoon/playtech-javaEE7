async function loadData() {

    const parameters = new URLSearchParams(window.location.search);

    const orderId = parameters.get("id");

    const response = await fetch("LoadInvoiceData?orderid=" + orderId);

    if (response.ok) {
        const json = await response.json();

        console.log(json);

        const orderlist = json.Orderlist;


        let st_tbody = document.getElementById("st-tbody");
        let st_item_tr = document.getElementById("st-item-tr");
        st_tbody.innerHTML = "";
        let sub_total = 0;
        json.Orderlist.forEach(orders => {


            document.getElementById("pe-or-no").innerHTML = orders.order.id;
            document.getElementById("cus-name").innerHTML = orders.order.user.first_name + " " + orders.order.user.last_name;
            document.getElementById("cus-line-1").innerHTML = orders.order.address.line1;
            document.getElementById("cus-line-2").innerHTML = orders.order.address.line2;
            document.getElementById("cus-add-city").innerHTML = orders.order.address.city.name;
            document.getElementById("cus-add-postal").innerHTML = orders.order.address.postal_code;
            document.getElementById("cus-mobile-1").innerHTML = orders.order.address.mobile;
            document.getElementById("cus-order-no").innerHTML = orders.order.id;
            document.getElementById("cus-order-date").innerHTML = orders.order.date_time;

            let st_item_clone = st_item_tr.cloneNode(true);
            let itemSubtotail = orders.product.price * orders.qty;
            sub_total += itemSubtotail;

            st_item_clone.querySelector("#st-item-title").innerHTML = orders.product.title;

            st_item_clone.querySelector("#similar-product-image").src = "product-images/" + orders.product.id + "/image1.png";

            st_item_clone.querySelector("#st-item-qty").innerHTML = orders.qty;
            st_item_clone.querySelector("#st-item-total").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(itemSubtotail);

            st_item_clone.querySelector("#st-item-price").innerHTML = "Rs." + new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(orders.product.price);


            //st_item_clone.querySelector("#st-item-qty").innerHTML = orders.product.user.email;
            st_tbody.appendChild(st_item_clone);
        });
//

        document.getElementById("totao-supd").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(sub_total);

        let item_count = orderlist.length;
        

        let shipping_amount = 0;


        if (orderlist[0].order.address.city.name) {
            //Colombo
            shipping_amount = item_count * 1000;

        } else {
            //out of Colombo
            shipping_amount = item_count * 2500;
        }
        document.getElementById("tota-shipoing").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(shipping_amount);

         let total = sub_total + shipping_amount;
         
         
         document.getElementById("tota-amounto").innerHTML = new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(total);

         
       

    } else {


        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });
    }

}