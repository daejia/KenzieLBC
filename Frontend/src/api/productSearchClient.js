import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the ItemService.
 *
 * This page will allow users to search for products by name or category and display real-time pricing and availability
 * information
 */
export default class ProductSearchClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getCartItem', 'createItem'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the item for the given ID.
     * @param id Unique identifier for an item
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The item
     */
    async getCartItem(id, errorCallback) {
        try {
            const response = await this.client.get(`/item/${id}`);
            return response.data;
        } catch (error) {
            this.handleError("getCartItem", error, errorCallback)
        }
    }
    async createItem(name, errorCallback) {
        try {
            const response = await this.client.post(`item`, {
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("createItem", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}