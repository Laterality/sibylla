import * as React from "react";
import { Location } from "history";

import { default as Article } from "../lib/Article";
import ArticleListComponent from "./ArticleList";


interface IArticlesComponentProps {
    location: Location;
    onArticleClick: (id: number) => void;
    articles: Array<Article>;
}

interface IArticlesComponentState {
    signedIn: boolean;
    articles: Array<Article>
}

export default class ArticlesComponent extends React.Component<IArticlesComponentProps, IArticlesComponentState> {

    constructor(p: IArticlesComponentProps) {
        super(p);

        this.state = {
            signedIn: false,
            articles: [],
        };
    }

    public render() {
        return (<div>
            <ArticleListComponent
                articles={this.props.articles}
                onArticleClick={this.props.onArticleClick}
            />
        </div>);
    }

}
