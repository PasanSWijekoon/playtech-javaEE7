async function loadProduct() {

    const parameters = new URLSearchParams(window.location.search);

    if (parameters.has("id")) {

        const productId = parameters.get("id");

        const response = await fetch("LoadSingleProduct?id=" + productId);

        if (response.ok) {

            const json = await response.json();

            const id = json.product.id;
            document.getElementById("product-title").innerHTML = json.product.title;
            document.getElementById("MainImg").src = "product-images/" + id + "/image1.png";


            document.getElementById("image1-thumb").src = "product-images/" + id + "/image1.png";
            document.getElementById("image2-thumb").src = "product-images/" + id + "/image2.png";
            document.getElementById("image3-thumb").src = "product-images/" + id + "/image3.png";


            document.getElementById("product-published-on").innerHTML = json.product.date_time;

            document.getElementById("product-price").innerHTML = new Intl.NumberFormat(
                    "en-US",
                    {
                        minimumFractionDigits: 2
                    }
            ).format(json.product.price);

            document.getElementById("product-category").innerHTML = json.product.model.category.name;
            document.getElementById("product-model").innerHTML = json.product.model.name;
            document.getElementById("product-condition").innerHTML = json.product.product_condition.name;
            document.getElementById("product-quantity").innerHTML = json.product.qty;
            document.getElementById("product-color").innerHTML = json.product.color.name;
            document.getElementById("product-storage").innerHTML = json.product.storage.value;

            document.getElementById("product-description").innerHTML = json.product.description;

            document.getElementById("add-to-cart-main").addEventListener(
                    "click",
                    (e) => {
                addToCart(
                        json.product.id,
                        document.getElementById("add-to-cart-qty").value
                        );
                e.preventDefault();
            });

            let productHtml = document.getElementById("similar-product");
            document.getElementById("similar-product-main").innerHTML = "";

            json.productList.forEach(item => {

                let productCloneHtml = productHtml.cloneNode(true);

                productCloneHtml.querySelector("#similar-product-image").src = "product-images/" + item.id + "/image1.png";
                productCloneHtml.querySelector("#similar-product-a1").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similar-product-a2").href = "single-product.html?id=" + item.id;
                productCloneHtml.querySelector("#similar-product-title").innerHTML = item.title;

                productCloneHtml.querySelector("#similar-product-price").innerHTML = "Rs." + new Intl.NumberFormat(
                        "en-US",
                        {
                            minimumFractionDigits: 2
                        }
                ).format(item.price);

                document.getElementById("similar-product-main").appendChild(productCloneHtml);

            });



        } else {
            window.location = "index.html";
        }

    } else {
        window.location = "index.html";
    }
}

async function addToCart(id, qty) {

    const response = await fetch(
            "AddToCart?pid=" + id + "&qty=" + qty
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

        Swal.fire({
            title: "Error Occured",
            text: "Unable to process your request",
            icon: "error"
        });

    }

}