import { default as Axios, AxiosPromise } from "axios";

export default class Api {

    private static readonly BASE_URL = "http://altair.latera.kr/sb/api";

    static readonly retrieveArticles = (limit = 20): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/list?limit=${limit}`);
    }

    static readonly retrieveArticle = (id: number): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/by-id/${id}`);
    }

    static readonly signIn = (email: string, password: string): AxiosPromise => {
        return Axios.post(`${Api.BASE_URL}/auth/login`, {
            email,
            password,
        });
    }
}