import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ProductSearchClient from "../api/productSearchClient";


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

        this.client = new ProductSearchClient();

        this.dataStore.addChangeListener(this.renderItem)
    }


    // Render Methods --------------------------------------------------------------------------------------------------

    async renderItem() {
        let resultArea = document.getElementById("result-info");

        const item = this.dataStore.get("item");

        if (item) {
            resultArea.innerHTML = `
                <div>ID: ${item.id}</div>
                <div>Name: ${item.name}</div>
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

        const createdExample = await this.client.createItem(name, this.errorHandler);
        this.dataStore.set("item", createdExample);

        if (createdExample) {
            this.showMessage(`Created ${createdExample.name}!`)
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