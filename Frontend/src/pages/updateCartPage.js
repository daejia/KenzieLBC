import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import UpdateCart from "../api/updateCartClient";

/**
 * Logic needed for the update cart page of the website.
 */
class UpdateCartPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onUpdate', 'renderCart'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and initialize the data store and client.
     */
    async mount() {
        document.getElementById('update-form').addEventListener('submit', this.onUpdate);
        this.client = new UpdateCartClient();
        this.dataStore.set("cart", {});

        this.dataStore.addChangeListener(this.renderCart);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderCart() {
        let cartArea = document.getElementById("cart-info");

        const cart = this.dataStore.get("cart");

        if (Object.keys(cart).length !== 0) {
            let itemsHtml = "";
            for (const item in cart) {
                itemsHtml += `<li>${item}: ${cart[item]}</li>`;
            }
            cartArea.innerHTML = `
                <ul>${itemsHtml}</ul>
            `;
        } else {
            cartArea.innerHTML = "Cart is empty.";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onUpdate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        const itemName = document.getElementById("item-name-field").value;
        const quantity = parseInt(document.getElementById("quantity-field").value);
        this.dataStore.set("cart", {});

        const cart = await this.client.updateCart(itemName, quantity, this.errorHandler);
        this.dataStore.set("cart", cart);

        if (cart) {
            this.showMessage(`Updated cart with ${quantity} ${itemName}!`)
        } else {
            this.errorHandler("Error updating cart! Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updateCartPage = new UpdateCartPage();
    updateCartPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
