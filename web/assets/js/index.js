// Function to shuffle an array using Fisher-Yates algorithm
function shuffleArray(array) {
    let currentIndex = array.length, randomIndex;

    // While there remain elements to shuffle...
    while (currentIndex !== 0) {
        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;

        // And swap it with the current element
        [array[currentIndex], array[randomIndex]] = [array[randomIndex], array[currentIndex]];
    }

    return array;
}

// Function to get a random subset of products
function getRandomProducts(products, count) {
    // Shuffle the products array
    const shuffledProducts = shuffleArray([...products]);

    // Select the first 'count' products from the shuffled array
    return shuffledProducts.slice(0, count);
}

// Example usage
async function loadProductData() {
    try {
        const response = await fetch("LoadProducts");  // Replace with your actual API endpoint

        if (response.ok) {
            const json = await response.json();
            const container = document.getElementById("product-sections-container");
            const featuredContainer = document.getElementById("featured-products-container");

            // Loop through each category and create sections dynamically
            json.categoryList.forEach(category => {
                const products = json.productList.filter(product => product.model.category.id === category.id);

                if (products.length > 0) {
                    const section = document.createElement("section");
                    section.classList.add("my-5", "pb-5");

                    section.innerHTML = `
                        <div class="container text-center mt-5 py-3">
                            <h3>${category.name} Phones</h3>
                            <hr class="mx-auto">
                            <p>Explore our ${category.name} products at fair prices.</p>
                        </div>
                        <div class="row mx-auto container-fluid">
                            ${createProductCards(products)}
                        </div>
                    `;

                    container.appendChild(section);
                }
            });

            // Get 4 random products for featured section
            const featuredProducts = getRandomProducts(json.productList, 4);
            const featuredSection = document.createElement("section");
            featuredSection.classList.add("my-5", "pb-5");

            featuredSection.innerHTML = `
                <div class="container text-center mt-5 py-3">
                    <h3>Featured Products</h3>
                    <hr class="mx-auto">
                    <p>Check out our featured products.</p>
                </div>
                <div class="row mx-auto container-fluid">
                    ${createProductCards(featuredProducts)}
                </div>
            `;

            featuredContainer.appendChild(featuredSection);

        } else {
            Swal.fire({
                title: "Error Occurred",
                text: "Please try again later",
                icon: "error"
            });
        }
    } catch (error) {
        console.error("Network error:", error);

        Swal.fire({
            title: "Network Error",
            text: "Failed to load data. Please check your connection and try again.",
            icon: "error"
        });
    }
}

// Function to create product cards for each category (limit to 4)
function createProductCards(products) {
    const formatter = new Intl.NumberFormat('en-US', {
        minimumFractionDigits: 2
    });
    
    let productCards = "";

    products.slice(0, 4).forEach(product => {
        productCards += `
            <div class="product text-center col-lg-3 col-md-4 col-12">
                <a href="single-product.html?id=${product.id}">
                    <img class="img-fluid" src="product-images/${product.id}/image1.png" alt="${product.title}">
                </a>
                <div class="star">
                    <i class="fa fa-star" aria-hidden="true"></i>
                    <i class="fa fa-star" aria-hidden="true"></i>
                    <i class="fa fa-star" aria-hidden="true"></i>
                    <i class="fa fa-star" aria-hidden="true"></i>
                    <i class="fa fa-star" aria-hidden="true"></i>
                </div>
                <h5 class="p-name">${product.title}</h5>
                <h4 class="p-price">Rs ${formatter.format(product.price)}</h4>
                <a href="single-product.html?id=${product.id}">
                    <button class="buy-button">Buy now</button>
                </a>
            </div>
        `;
    });

    return productCards;
}

// Call the function when the page loads
window.onload = loadProductData;
