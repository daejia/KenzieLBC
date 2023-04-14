import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class CheckoutClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getCart', 'createExample'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    async getCart(id, errorCallback) {
        try {
            const response = await this.client.get(`/cart/${id}/items`);
            return response.data;
        } catch (error) {
            this.handleError("getCart", error, errorCallback)
        }
    }

    async createExample(name, errorCallback) {
        try {
            const response = await this.client.post(`example`, {
                name: name
            });
            return response.data;
        } catch (error) {
            this.handleError("createExample", error, errorCallback);
        }
    }

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
