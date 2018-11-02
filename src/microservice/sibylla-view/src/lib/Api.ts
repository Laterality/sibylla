import { default as Axios, AxiosPromise } from "axios";

export default class Api {

    private static readonly BASE_URL = "http://altair.latera.kr/sb/api";

    static readonly retrieveArticles = (limit = 20): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/list?limit=${limit}`);
    }

    static readonly retrieveArticle = (id: number): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/by-id/${id}`);
    }

    static readonly retrieveRecommends = (auth: string): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/recommends`, {
            headers: {
                Authorization: `Bearer ${auth}`,
            },
        });
    }

    static readonly retrieveArticlesWithIds = (ids: Array<number>): AxiosPromise => {
        const param = ids.join(",");
        return Axios.get(`${Api.BASE_URL}/article/by-ids?ids=${param}`);
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

    static readonly read = (auth: string, articleId: number): AxiosPromise => {
        return Axios.get(`${Api.BASE_URL}/article/read?articleId=${articleId}`, {
            headers: {
                Authorization: `Bearer ${auth}`,
            }
        });
    }
}