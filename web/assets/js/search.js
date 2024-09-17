
async function loadData() {
    const response = await fetch("LoadData");
    if (response.ok) {
        const json = await response.json();
        console.log(json);

        loadOption("categorytag", json.categoryList, "name");
        loadOption("Conditiontag", json.conditionList, "name");
        loadOption("colortag", json.colorList, "name");
        loadOption("storagetag", json.storageList, "value");

        const parameters = new URLSearchParams(window.location.search);
        if (parameters.has("t")) {
            searchProducts(0);
        } else {
            updateProductView(json);
        }



    } else {


        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });
    }

}

function loadOption(selectTagId, list, property) {

    const selectTag = document.getElementById(selectTagId);
    list.forEach(item => {
        let optionTag = document.createElement("option");
        optionTag.value = item.id;
        optionTag.innerHTML = item[property];
        selectTag.appendChild(optionTag);

    });

}



async function searchProducts(firstResult) {

    const parameters = new URLSearchParams(window.location.search);


    // Define `searchText` based on the URL parameter or input field
    let searchText;

    if (parameters.has("t")) {
        // Get search text from URL parameter
        searchText = decodeURIComponent(parameters.get("t")).trim();
    } else {
        // Get search text from input field
        searchText = document.getElementById('searchText').value.trim();
    }



    const category_name = document.getElementById('categorytag').value;
    const condition = document.getElementById('Conditiontag').value;

    const color = document.getElementById('colortag').value;
    const storage = document.getElementById('storagetag').value;
    const price_range_start = document.getElementById('priceFrom').value;
    const price_range_end = document.getElementById('priceTo').value;
    const sort_text = document.getElementById('sort').value;


    const data = {

        firstResult: firstResult,
        searchText: searchText,
        category_name: category_name, // Category filter
        color: color, // Color filter
        condition: condition, // Condition filter
        storage: storage, // Storage filter
        price_range_start: price_range_start, // Price range start
        price_range_end: price_range_end, // Price range end
        sort_text: sort_text
    };

    console.log(data);

    const response = await fetch("SearchProducts", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (response.ok) {
        const json = await response.json();
        console.log(json);
        updateProductView(json);
        //currentPage = 0;
    } else {
        Swal.fire({
            title: "Error Occured",
            text: "Please try again later",
            icon: "error"
        });
    }
    clearUrlParameters();
}

var st_product = document.getElementById("st-product");
var st_pagination_button = document.getElementById("st-pagination-button");

var currentPage = 0;
async function updateProductView(json) {
    let st_product_container = document.getElementById("st-product-container");
    st_product_container.innerHTML = "";

    json.productList.forEach(product => {
        let st_product_clone = st_product.cloneNode(true);

        // Update product cards
        st_product_clone.querySelector("#st-product-a-1").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-img-1").src = "product-images/" + product.id + "/image1.png";
        st_product_clone.querySelector("#st-product-a-2").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-title-1").innerHTML = product.title;
        st_product_clone.querySelector("#st-product-price-1").innerHTML = new Intl.NumberFormat(
                "en-US",
                {
                    minimumFractionDigits: 2
                }
        ).format(product.price);

        st_product_container.appendChild(st_product_clone);
    });

    let st_pagination_container = document.getElementById("st-pagination-container");
    st_pagination_container.innerHTML = "";  // Clear the pagination container

    let product_count = json.allProductCount;
    const product_per_page = 6;
    let pages = Math.ceil(product_count / product_per_page);

    // Create a <nav> container for pagination
    let navElement = document.createElement("nav");
    navElement.setAttribute("aria-label", "Page navigation");
    let ulElement = document.createElement("ul");
    ulElement.className = "pagination mt-5"; // Bootstrap pagination classes
    navElement.appendChild(ulElement);

    // Add Previous button
    let prevLi = document.createElement("li");
    prevLi.className = `page-item ${currentPage === 0 ? 'disabled' : ''}`;  // Disable if on the first page

    let prevLink = document.createElement("a");
    prevLink.className = "page-link";
    prevLink.href = "#";
    prevLink.innerHTML = "Previous";
    prevLink.setAttribute("tabindex", currentPage === 0 ? "-1" : "0");
    prevLink.setAttribute("aria-disabled", currentPage === 0 ? "true" : "false");

    if (currentPage !== 0) {
        prevLink.addEventListener("click", e => {
            e.preventDefault(); // Prevent default link behavior
            currentPage--;
            searchProducts(currentPage * product_per_page);
        });
    }

    prevLi.appendChild(prevLink);
    ulElement.appendChild(prevLi);

    // Add Page number buttons
    for (let i = 0; i < pages; i++) {
        let pageLi = document.createElement("li");
        pageLi.className = `page-item ${i === currentPage ? 'active' : ''}`;
        if (i === currentPage) {
            pageLi.setAttribute("aria-current", "page");
        }

        let pageLink = document.createElement("a");
        pageLink.className = "page-link";
        pageLink.href = "#";
        pageLink.innerHTML = i + 1;

        pageLink.addEventListener("click", e => {
            e.preventDefault(); // Prevent default link behavior
            currentPage = i;
            searchProducts(i * product_per_page);
        });

        pageLi.appendChild(pageLink);
        ulElement.appendChild(pageLi);
    }

    // Add Next button
    let nextLi = document.createElement("li");
    nextLi.className = `page-item ${currentPage === (pages - 1) ? 'disabled' : ''}`;  // Disable if on the last page

    let nextLink = document.createElement("a");
    nextLink.className = "page-link";
    nextLink.href = "#";
    nextLink.innerHTML = "Next";
    nextLink.setAttribute("tabindex", currentPage === (pages - 1) ? "-1" : "0");
    nextLink.setAttribute("aria-disabled", currentPage === (pages - 1) ? "true" : "false");

    if (currentPage !== (pages - 1)) {
        nextLink.addEventListener("click", e => {
            e.preventDefault(); // Prevent default link behavior
            currentPage++;
            searchProducts(currentPage * product_per_page);
        });
    }

    nextLi.appendChild(nextLink);
    ulElement.appendChild(nextLi);

    // Finally, append the pagination <nav> to the container
    st_pagination_container.appendChild(navElement);
}


function clearUrlParameters() {
    // Clear the URL parameters without reloading the page
    history.replaceState(null, '', window.location.pathname);
}