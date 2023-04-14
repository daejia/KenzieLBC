import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ProductSearchClient from "../api/productSearchClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ProductSearchPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderItem'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        document.getElementById('create-form').addEventListener('submit', this.onCreate);

        this.dataStore.addChangeListener(this.renderItem)

        this.client = new ProductSearchClient();
        const item = await this.client.getCartItem(this.errorHandler);
        this.dataStore.set("item", item);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderItem() {
        let resultArea = document.getElementById("result-info");

        const item = this.dataStore.get("item");

        if (item) {
            resultArea.innerHTML = `
                <div>ID: ${item.id}</div>
              `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("item", null);


        let result = await this.client.getCartItem(id, this.errorHandler);

        this.dataStore.set("item", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("item", null);

        let name = document.getElementById("create-name-field").value;
        let store = document.getElementById("create-store-field").value;
        let brandType = document.getElementById("create-brand-type-field").value;
        let category = document.getElementById("create-category-field").value;
        let price = document.getElementById("create-price-field").value;
        let isInStock = document.getElementById("create-in-stock-field").value;

        const createdItem = await this.client.addNewItem(name, store, brandType, category, price, isInStock, this.errorHandler);
        this.dataStore.set("item", createdItem);

        if (createdItem) {
            this.showMessage(`Created ${createdItem.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const productSearchPage = new ProductSearchPage();
    await productSearchPage.mount();
};

window.addEventListener('DOMContentLoaded', main);