import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "../api/exampleClient";

class CheckoutPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('checkout').addEventListener('click', this.onGet);

        this.client = new CheckoutClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const cart = this.dataStore.get("cart");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div> //code that gets returned
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let amount = document.querySelector(".amount").value;
        this.dataStore.set("cart", null);

        let result = await this.client.getCart(id, this.errorHandler);
        this.dataStore.set("cart", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let name = document.getElementById("create-name-field").value;

        const createdExample = await this.client.createExample(name, this.errorHandler);
        this.dataStore.set("example", createdExample);

        if (createdExample) {
            this.showMessage(`Created ${createdExample.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

const main = async () => {
    const checkoutPage = new CheckoutPage();
    checkoutPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
