import * as React from "react";
import { AxiosResponse } from "axios";

import { default as Api } from "../lib/Api";

import { default as Article } from "../lib/Article";

import { default as Header } from "../component/Header";
import { default as Content } from "../component/Content";
import { withCookies, Cookies } from "react-cookie";

interface IHomePageComponentProps {
    location: Location;
    cookies: Cookies;
}

interface IHomePageComponentState {
    articles: Array<Article>;
    authToken: string;
}

class HomePageComponent extends React.Component<IHomePageComponentProps, IHomePageComponentState> {

    constructor(props: IHomePageComponentProps) {
        super(props);

        this.state = {
            articles: [],
            authToken: this.props.cookies.get("auth"),
        };
    }

    render() {

        return (
            <div>
                <Header />
                <Content
                    articles={this.state.articles}
                    authToken={this.state.authToken}/>
            </div>
        );
    }

    componentDidMount() {
        // Get article list
        Api.retrieveArticles(21)
        .then((res: AxiosResponse) => {
            const articles = [];

            for (let a of res.data["articles"]) {
                articles.push(new Article(
                    a["id"], 
                    a["title"],
                    a["content"],
                    a["sourceName"],
                    new Date(a["writtenDate"]),
                    a["url"],
                    a["images"]));
            }
            this.setState({articles});
        });
    }
}

export default withCookies(HomePageComponent);
