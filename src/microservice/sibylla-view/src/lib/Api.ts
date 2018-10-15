import { default as Axios, AxiosPromise } from "axios";

export default class Api {

    private static readonly BASE_URL = "http://localhost:8080/sibylla/api";

    static readonly retrieveArticles = (limit = 20): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/list?limit=${limit}`);
    }

    static readonly signIn = (email: string, password: string): AxiosPromise => {
        // return Axios.post(`${Api.BASE_URL}/auth/login`, {
        return Axios.post(`http://localhost:8080/login`, {
            email,
            password,
        });
    }
}