import { default as Axios, AxiosPromise } from "axios";

export default class Api {

    private static readonly BASE_URL = "http://altair.latera.kr/sb/api";

    static readonly retrieveArticles = (limit = 20): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/list?limit=${limit}`);
    }

    static readonly retrieveArticle = (id: number): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/by-id/${id}`);
    }

    static readonly signUp = (email: string, pw: string) => {
        return Axios.post(`${Api.BASE_URL}`, {
            email,
            password: pw,
        });
    }

    static readonly signIn = (email: string, password: string): AxiosPromise => {
        return Axios.post(`${Api.BASE_URL}/auth/login`, {
            email,
            password,
        });
    }

    static readonly logout = (auth: string): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/auth/logout`, {
            headers: {
                Authorization: `Bearer ${auth}`,
            },
        });
    }

    static readonly read = (auth: string): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/read`, {
            headers: {
                Authorization: `Bearer ${auth}`,
            }
        });
    }
}