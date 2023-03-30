import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ProductSearchClient from "../api/ProductSearchClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class ProductSearchPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'renderItem'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        this.client = new ProductSearchClient();

        this.dataStore.addChangeListener(this.renderItem)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderItem() {
        let resultArea = document.getElementById("result-info");

        const item = this.dataStore.get("item");
        item.inStock = undefined;
        item.price = undefined;
        item.category = undefined;
        item.brandType = undefined;
        item.store = undefined;

        if (item) {
            resultArea.innerHTML = `
                <div>ID: ${item.id}</div>
                <div>Store: ${item.store}</div>
                <div>BrandType: ${item.brandType}</div>
                <div>Name: ${item.name}</div>
                <div>Category: ${item.category}</div>
                <div>Price: ${item.price}</div>
                <div>InStock: ${item.inStock}</div>
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

        let result = await this.client.getItem(id, this.errorHandler);
        this.dataStore.set("item", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const productSearchPage = new ProductSearchPage();
    productSearchPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
