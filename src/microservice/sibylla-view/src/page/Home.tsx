import * as React from "react";
import { AxiosResponse } from "axios";

import { default as Api } from "../lib/Api";

import { default as Article } from "../lib/Article";

import { default as Header } from "../component/Header";
import { default as Content } from "../component/Content";

interface IHomePageComponentProps {
    location: Location;
}

interface IHomePageComponentState {
    articles: Array<Article>;
}

export default class HomePageComponent extends React.Component<IHomePageComponentProps, IHomePageComponentState> {

    constructor(props: IHomePageComponentProps) {
        super(props);

        this.state = {
            articles: [],
        };
    }

    render() {
        const style = {
            background: "#fafafa",
        };

        return (
            <div style={style}>
                <Header/>
                <Content articles={this.state.articles}/>
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